package com.team2.board.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.team2.board.model.BoardVO;
import com.team2.board.model.ReplyVO;
import com.team2.board.service.IBoardService;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/b")
public class BoardController {

	@Autowired
	IBoardService boardService;
	
	@PostMapping(value="/create") 
	public BoardVO testing(@RequestBody BoardVO board) {
		System.out.println(board);
		boardService.createBoard(board,boardService.maxBoardId());
		return board;
	}

	@PostMapping(value="/createReply") 
	public ReplyVO test2(@RequestBody ReplyVO reply) {
		boardService.createReply(reply);
		return reply;
	}
	
	@DeleteMapping("/delete/{boardId}")
	public String deleteBoard(@PathVariable int boardId) {
		try {
			boardService.deleteBoard(boardId);
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}
	
	@DeleteMapping("/deleteReply/{replyId}")
	public String deleteReply(@PathVariable int replyId) {
		try {
			boardService.deleteReply(replyId);
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}

	@PutMapping("/update/{boardId}")
	public BoardVO putMethodName(@PathVariable int boardId, @RequestBody BoardVO board) {
		boardService.updateBoard(board);
		return board;
	}
	
	@PutMapping("/updateReply/{replyId}")
	public ReplyVO updateReply(@PathVariable int replyId, @RequestBody ReplyVO reply) {
		boardService.updateReply(reply);
		return reply;
	}
}
