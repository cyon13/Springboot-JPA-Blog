package com.cyon13.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	
	@PostMapping("/api/user")
	public ResponseDto<Integer> join(@RequestBody User user) {
		user.setRole(RoleType.USER);
		userService.join(user);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); // 자바오브젝트를 JSON으로 변경(Jackson)
	}
	
}
