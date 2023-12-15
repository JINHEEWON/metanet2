package com.team2.member.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.team2.member.model.Member;
import com.team2.member.model.MemberDelete;
import com.team2.member.model.MemberFindInfo;
import com.team2.member.model.Email;
import com.team2.member.model.MemberId;
import com.team2.member.service.IMemberService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	IMemberService memberService;
   
	// checkEmail 회원가입 시 이미 등록된 이메일이 있는지 확인하기 위함
	@PostMapping("/checkEmail")
	@ResponseBody
	public ResponseEntity<String> postEmail(@RequestBody Email email) {
		boolean answer = memberService.checkEmail(email);
		if (!answer) {
			return ResponseEntity.ok("사용 가능한 이메일입니다.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 사용중인 이메일입니다.");
		}
	}

	// checkMemberId 회원가입 시 이미 등록된 아이디가 있는지 확인하기 위함
	@PostMapping("/checkMemberId")
	@ResponseBody
	public ResponseEntity<String> postMemberId(@RequestBody MemberId memberId) {
		boolean answer = memberService.checkMemberId(memberId);
		if (!answer) {
			return ResponseEntity.ok("사용 가능한 아이디입니다.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 사용중인 아이디입니다.");
		}
	}

	// insertMember
	@PostMapping("/signup")
	@ResponseBody // postman으로 응답을 확인하기 위함
	public Member insertEmp(@RequestBody Member member, HttpSession session) {
		// CSRF 토큰 생성 및 세션에 저장
		String csrfToken = UUID.randomUUID().toString();
		session.setAttribute("csrfToken", csrfToken);

		// 이미 등록된 ID가 있는지 확인

		// 이미 등록된 email이 있는지 확인

		memberService.insertMember(member);
		return member;
	}

//   @GetMapping(value = "/member/insert")
//	public String insertMember(HttpSession session, Model model) {
//		String csrfToken = UUID.randomUUID().toString();
//		session.setAttribute("csrfToken", csrfToken);
//		logger.info("/member/insert, GET", csrfToken);
//		model.addAttribute("member", new Member()); // 폼 입력값 검증에 사용
//		return "member/form";
//	}

	// login
	@GetMapping(value = "/login")
	public String login() {
		return "member/login";
	}

   // selectMember 회원정보 검색(마이페이지)
   @GetMapping(value="/mypage/{memberId}")
   public Member selectMemberInfo(@PathVariable String memberId) {
	   Member member =  memberService.selectMember(memberId);
	   return member;
   }
		
   // updateMember
	@PutMapping("/update")
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
