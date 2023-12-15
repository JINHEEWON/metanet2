package com.team2.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.board.model.ReplyVO;
import com.team2.board.service.IBoardService;

@RestController
@RequestMapping("/reply")
public class ReplyController {
	
	@Autowired
	IBoardService boardService;
	
	// 댓글 작성 
	@PostMapping(value="/create") 
	public ReplyVO createReply(@RequestBody ReplyVO reply) {
		boardService.createReply(reply, boardService.maxReplyId());
		return reply;
	}
	
	// 댓글 삭제 
	@DeleteMapping("/delete/{replyId}")
	public String deleteReply(@PathVariable int replyId) {
		try {
			boardService.deleteReply(replyId);
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}
	
	// 댓글 수정 
	@PutMapping("/update")
	public ReplyVO updateReply(@RequestBody ReplyVO reply) {
		boardService.updateReply(reply);
		return reply;
	}
}
