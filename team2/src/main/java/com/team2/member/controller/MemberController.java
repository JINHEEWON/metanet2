package com.team2.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.member.model.Member;
import com.team2.member.model.MemberDelete;
import com.team2.member.model.MemberFindInfo;
import com.team2.member.service.IMemberService;

@RestController
@RequestMapping("/member")
public class MemberController {
   
   @Autowired
   IMemberService memberService;

   // insertMember 회원가입
   @PostMapping("/signup")
	public Member insertMember(@RequestBody Member member) {
		memberService.insertMember(member);
		return member;
   }
   
   // selectMember 회원정보 검색(마이페이지)
   @GetMapping(value="/mypage/{memberId}")
   public Member selectMemberInfo(@PathVariable String memberId) {
	   Member member =  memberService.selectMember(memberId);
	   return member;
   }
	
   // updateMember
	@PutMapping("/update/{memberId}")
	public Member updateMemberInfo(@RequestBody Member member) {
		memberService.updateMember(member);
		Member resultMember = memberService.selectMember(member.getMemberId());
		return resultMember;
	}
	
   // deleteMember
	@DeleteMapping("/delete")
	public String deleteMemberInfo(@RequestBody MemberDelete memberDelete) {
		int result = memberService.deleteMember(memberDelete);
		String message = "";
		if(result != 1) {
			message = "비밀번호를 다시 확인해주세요";
		} else {
			message = memberDelete.getMemberId()+" 회원정보 삭제";
		}
		return message;
	}
	
   // selectid(아이디찾기)
	@PostMapping("/find/username")
	public String findUsername(@RequestBody MemberFindInfo memberFindInfo) {
		String memberId = memberService.getId(memberFindInfo);;
		if(memberId == null) {
			memberId = "회원 정보가 존재하지 않습니다.";
		}
		return memberId;
	}
	
	// selectpassword(비밀번호 찾기 - 새로운 비밀번호 발급 후 리턴)
	@PostMapping("/find/password")
	public String findPassword(@RequestBody MemberFindInfo memberFindInfo) {
		return memberService.updatePassword(memberFindInfo);
	}
}
