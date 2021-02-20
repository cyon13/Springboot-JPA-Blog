package com.cyon13.web.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyon13.web.dto.ResponseDto;
import com.cyon13.web.model.RoleType;
import com.cyon13.web.model.User;
import com.cyon13.web.service.Userservice;

@RestController("apiUserController")
public class UserController {

	@Autowired
	private Userservice userService;
	

	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> join(@RequestBody User user) {
		userService.join(user);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); // 자바오브젝트를 JSON으로 변경(Jackson)
	}
	
	// security 이용한 로그인
	
	
	
	
	// 전통적인 로그인 방식
	/*
	 * @PostMapping("/api/user/login") public ResponseDto<Integer>
	 * login(@RequestBody User user, HttpSession session) { User principal = userService.login(user); //
	 * principal(접근주체) if(principal != null) { session.setAttribute("principal",
	 * principal); } return new ResponseDto<Integer>(HttpStatus.OK.value(),1); //
	 * 자바오브젝트를 JSON으로 변경(Jackson) }
	 */
	
	
}
