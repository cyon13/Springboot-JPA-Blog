package com.cyon13.web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cyon13.web.model.RoleType;
import com.cyon13.web.model.User;
import com.cyon13.web.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌.
@Service
public class Userservice {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public void join(User user) {
		String rawPassword = user.getPassword(); // 원문
		String encPassword = encoder.encode(rawPassword); // 해쉬
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);

		userRepository.save(user);
	}

	@Transactional
	public void update(User user) {
		// 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
		// select를 해서 User 오브젝트를 DB로부터 가져오는 이유는 영속화를 하기위해서!!
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려준다.
		User CurrentUser = userRepository.findById(user.getId())
				.orElseThrow(()-> {
					return new IllegalArgumentException("회원찾기실패");
				});
		String rawPassword = user.getPassword(); // 원문
		String encPassword = encoder.encode(rawPassword); // 해쉬
		CurrentUser.setPassword(encPassword);
		CurrentUser.setEmail(user.getEmail());
		// 회원수정 함수 종료시 = 서비스 종료 = 트랜잭션이 종료 = commit이 자동으로 된다.
		// 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려준다.
	}
	
	/*
	 * @Transactional(readOnly = true) // Select할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료
	 * //(readOnly = true 라고 하면 트랜잭션 도중에는 정합성을 유지시켜준다) public User login(User user)
	 * { return userRepository.findByUsernameAndPassword(user.getUsername(),
	 * user.getPassword()); }
	 */


}
