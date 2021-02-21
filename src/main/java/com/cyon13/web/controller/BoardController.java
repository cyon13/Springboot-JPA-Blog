package com.cyon13.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cyon13.web.config.auth.PrincipalDetail;

@Controller
public class BoardController {
	

	
	@GetMapping("/")
	public String index() { // 컨트롤러에서 세션을 어떻게 찾는지?

		return "index";
	}
	
	//USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		
		return "board/saveForm";
	}
}
