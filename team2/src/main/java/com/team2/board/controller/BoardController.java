package com.team2.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.team2.board.model.BoardVO;
import com.team2.board.model.ReplyVO;
import com.team2.board.service.IBoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/b")
public class BoardController {

	@Autowired
	IBoardService boardService;
	
	@PostMapping(value="/create") 
	public @ResponseBody String testing(@RequestBody BoardVO board) {
		System.out.println(board);
		boardService.createBoard(board);
		return "test";
	}

	@PostMapping(value="/createReply") 
	public @ResponseBody String test2(@RequestBody ReplyVO reply) {
		boardService.createReply(reply);
		return "test";
	}

}