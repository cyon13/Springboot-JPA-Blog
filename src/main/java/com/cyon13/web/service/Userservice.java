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
	
	/*
	 * @Transactional(readOnly = true) // Select할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료
	 * //(readOnly = true 라고 하면 트랜잭션 도중에는 정합성을 유지시켜준다) public User login(User user)
	 * { return userRepository.findByUsernameAndPassword(user.getUsername(),
	 * user.getPassword()); }
	 */


}
