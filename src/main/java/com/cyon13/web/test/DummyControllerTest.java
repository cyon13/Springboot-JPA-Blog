package com.cyon13.web.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyon13.web.model.RoleType;
import com.cyon13.web.model.User;
import com.cyon13.web.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired // 의존성 주입(DI)
	private UserRepository userRepository;
	
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("username:"+user.getUsername());
		System.out.println("username:"+user.getPassword());
		System.out.println("username:"+user.getEmail());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다";
	}
		
}
