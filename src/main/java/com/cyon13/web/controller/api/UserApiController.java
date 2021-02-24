package com.cyon13.web.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyon13.web.config.auth.PrincipalDetail;
import com.cyon13.web.dto.ResponseDto;
import com.cyon13.web.model.RoleType;
import com.cyon13.web.model.User;
import com.cyon13.web.service.Userservice;

@RestController
public class UserApiController {

	@Autowired
	private Userservice userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> join(@RequestBody User user) {
		userService.join(user);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); // 자바오브젝트를 JSON으로 변경(Jackson)
	}
	
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user) {
		
		userService.update(user);
		// 여기서는 트랜잭션이 종료되기 때문에 DB 값은 변경이 됐음.
		// 하지만 세션값은 변경되지 않은 상태이기 때문에 우리가 직접 세션값을 변경해줄 것임

		// 세션 등록
		Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())); // authenticationManager에 username, password를 담은 AuthenticationToken을 넘겨주면 인증해서 Authentication 객체를 만들어준다. 
		SecurityContextHolder.getContext().setAuthentication(authentication); // 만들어진 Authentication 객체를 SecurityContext에 저장시킨다.
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
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
