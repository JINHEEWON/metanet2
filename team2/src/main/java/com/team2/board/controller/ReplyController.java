package com.team2.board.controller;

import java.security.Principal;

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
import com.team2.member.model.Member;
import com.team2.member.service.IMemberService;

@RestController
@RequestMapping("/reply")
public class ReplyController {
	
	@Autowired
	IBoardService boardService;
	@Autowired
	IMemberService memberService;
	
	// 댓글 작성 
	@PostMapping(value="/create/{teamId}") 
	public ReplyVO createReply(@RequestBody ReplyVO reply, 
			@PathVariable String teamId, Principal principal) {
		Member member = memberService.selectMember(principal.getName());
		if(!teamId.equals(Integer.toString(member.getTeamId()))) {
			return null; 
		}
		boardService.createReply(reply, boardService.maxReplyId());
		return reply;
	}
	
	// 댓글 삭제 
	@DeleteMapping("/delete/{teamId}/{replyId}")
	public String deleteReply(@PathVariable int replyId, 
			@PathVariable String teamId, Principal principal) {
		Member member = memberService.selectMember(principal.getName());
		if(!teamId.equals(Integer.toString(member.getTeamId()))) {
			return "fail"; 
		}
		try {
			boardService.deleteReply(replyId);
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}
	
	// 댓글 수정 
	@PutMapping("/update/{teamId}")
	public ReplyVO updateReply(@RequestBody ReplyVO reply,
			@PathVariable String teamId, Principal principal) {
		Member member = memberService.selectMember(principal.getName());
		if(!teamId.equals(Integer.toString(member.getTeamId()))) {
			return null; 
		}
		boardService.updateReply(reply);
		return reply;
	}
}
