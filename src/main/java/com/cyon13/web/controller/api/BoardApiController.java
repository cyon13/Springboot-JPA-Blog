package com.cyon13.web.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyon13.web.config.auth.PrincipalDetail;
import com.cyon13.web.dto.ResponseDto;
import com.cyon13.web.model.Board;
import com.cyon13.web.model.RoleType;
import com.cyon13.web.model.User;
import com.cyon13.web.service.Boardservice;
import com.cyon13.web.service.Userservice;

@RestController
public class BoardApiController {

	@Autowired
	private Boardservice boardService;
	

	
	@PostMapping("/api/board")
	public ResponseDto<Integer> join(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		boardService.write(board,principal.getUser());
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); // 자바오브젝트를 JSON으로 변경(Jackson)
	}	
	
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id) {
		boardService.del(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); // 자바오브젝트를 JSON으로 변경(Jackson)
	}	
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> UpdateById(@PathVariable int id, @RequestBody Board board) {
		boardService.update(id,board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); // 자바오브젝트를 JSON으로 변경(Jackson)
	}	
	
}
