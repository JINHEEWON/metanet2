package com.team2.board.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.team2.board.model.BoardVO;
import com.team2.board.model.ReplyVO;
import com.team2.board.service.IBoardService;

import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/b")
public class BoardController {

	@Autowired
	IBoardService boardService;
	
	@PostMapping(value="/create") 
	public BoardVO testing(@RequestBody BoardVO board) {
		System.out.println(board);
		boardService.createBoard(board);
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
		System.out.println(replyId);
		try {
			boardService.deleteReply(replyId);
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}

}
