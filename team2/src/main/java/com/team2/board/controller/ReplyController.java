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

import com.team2.board.model.BoardVO;
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
	public ReplyVO createReply(
		@RequestBody ReplyVO reply,
		@PathVariable int teamId,
		Principal principal
	) {
		String memberId = principal.getName();
		if (teamId!=1) {
			if (memberId==null) {
				return null;
			}
			Member member = memberService.selectMember(memberId);
			if (member.getTeamId()!=teamId) {
				return null;
			}
		}

		boardService.createReply(reply, boardService.maxReplyId());
		return reply;
	}
	
	// 댓글 삭제 
	@DeleteMapping("/delete/{teamId}/{replyId}")
	public String deleteReply(
			@PathVariable int replyId,
			@PathVariable int teamId,
			Principal principal
	) {
		String memberId = principal.getName();
		if (memberId==null) {
			return "사용하려면 로그인을 해주세요.";
		}
		Member member = memberService.selectMember(memberId);
		ReplyVO reply = boardService.getReplyInfo(replyId);
		if (member.getTeamId()!=teamId) {
			return "자신이 속한 게시판만 이용하실 수 있습니다.";
		}
		if (memberId!=reply.getMemberId())
		if (reply.getMemberId()!= memberId) {
			return "작성자가 아니여서 댓글을 삭제하실 수 없습니다.";
		}
		try {
			boardService.deleteReply(replyId);
		} catch (Exception e) {
			e.printStackTrace();
			return "댓글 삭제에 실패하였습니다. 잠시후에 다시 시도해 주세요.";
		}
		return "댓글 삭제에 성공했습니다.";
	}
	
	// 댓글 수정 
	@PutMapping("/update/{teamId}")
	public ReplyVO updateReply(
			@RequestBody ReplyVO reply,
			@PathVariable int teamId,
			Principal principal
	) {
		String memberId = principal.getName();
		if (memberId==null) {
			return null;
		}
		Member member = memberService.selectMember(memberId);
		if (member.getTeamId()!=teamId) {
			return null;
		}
		if (memberId!=reply.getMemberId())
		if (reply.getMemberId()!= memberId) {
			return null;
		}
		boardService.updateReply(reply);
		return reply;
	}
}
