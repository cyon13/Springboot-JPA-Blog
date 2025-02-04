package com.cyon13.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cyon13.web.model.Board;
import com.cyon13.web.service.Boardservice;

@Controller
public class BoardController {
	
	@Autowired
	private Boardservice boardService;
	
	@GetMapping("/")
	public String index(Model model,@PageableDefault(size=3,sort="id",direction = Sort.Direction.DESC) Pageable pageable) { // 컨트롤러에서 세션을 어떻게 찾는지?
		
		Page<Board> boards = boardService.getList(pageable);
		System.out.println(boards);
		model.addAttribute("boards",boards);
		
		return "index";
	}
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		Board board = boardService.detail(id);
		model.addAttribute("board",board);
		return "board/detail";
	}
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		Board board = boardService.detail(id);
		model.addAttribute("board",board);
		return "board/updateForm";
	}
	
	//USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		
		return "board/saveForm";
	}
	
	
}
