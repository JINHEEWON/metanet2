package com.team2.member.controller;

import java.security.Principal;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
import com.team2.common.filter.LoginInterceptor;
import com.team2.member.MemberValidator;
import com.team2.member.model.Email;
import com.team2.member.model.Member;
import com.team2.member.model.MemberId;
import com.team2.member.service.IMemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class MemberController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IMemberService memberService;

	@Autowired
	MemberValidator memberValidator;

	@Autowired
	private LoginInterceptor loginInterceptor;

	// 중복 이메일 확인
	@PostMapping("/checkemail")
	@ResponseBody
	public ResponseEntity<String> checkEmail2(@RequestBody Email email) {
		boolean emailAvailable = !memberService.checkEmail(email.getEmail());

		if (emailAvailable) {
			return ResponseEntity.ok("사용 가능한 이메일입니다.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("중복된 이메일입니다.");
		}
	}

	// 중복 아이디 확인
	@PostMapping("/checkmemberid")
	@ResponseBody
	public ResponseEntity<String> checkMemberId2(@RequestBody MemberId memberId) {
		boolean memberIdAvailable = !memberService.checkMemberId(memberId.getMemberId());

		if (memberIdAvailable) {
			return ResponseEntity.ok("사용 가능한 아이디입니다.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("중복된 아이디입니다.");
		}
	}

	// 회원가입
	@PostMapping("/signup")
	@ResponseBody
	public ResponseEntity<String> insertMember(@RequestBody Member member, HttpSession session) {

		// CSRF 토큰 생성 및 세션에 저장 (CSRF 공격 방어)
		String csrfToken = UUID.randomUUID().toString();
		session.setAttribute("csrfToken", csrfToken);

		// 중복 확인
		boolean emailAvailable = memberService.checkEmail(member.getEmail());
		boolean memberIdAvailable = memberService.checkMemberId(member.getMemberId());

		// 중복된 경우에 대한 에러 응답
		if (emailAvailable && memberIdAvailable) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("중복된 이메일입니다. 중복된 아이디입니다.");
		} else if (emailAvailable) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("중복된 이메일입니다.");
		} else if (memberIdAvailable) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("중복된 아이디입니다.");
		}

		// 이메일과 아이디가 모두 중복이 아닌 경우에만 회원 등록 수행
		// 비밀번호 암호화
		PasswordEncoder pwEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		String encodePw = pwEncoder.encode(member.getPassword());
		member.setPassword(encodePw);

		// 회원 등록
		memberService.insertMember(member);
		return ResponseEntity.ok("회원가입 성공");
	}
	
	@GetMapping("/success")
	@ResponseBody
	public ResponseEntity<String> loginSuccess() {
		return ResponseEntity.ok("로그인 성공");
	}


	// 회원정보 검색(마이페이지)
	@GetMapping(value = "/mypage/{memberId}")
	public Member selectMemberInfo(@PathVariable String memberId) {
		Member member = memberService.selectMember(memberId);
		return member;
	}

	// 회원정보 수정
	@PutMapping("/update")
	public Member updateMemberInfo(@RequestBody Member member) {
		if (!member.getPassword().equals(member.getPassword2())) {
			return null;
		}
		// password 암호화
		PasswordEncoder pwEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		String encodedPw = pwEncoder.encode(member.getPassword());
		member.setPassword(encodedPw);

		memberService.updateMember(member);
		Member resultMember = memberService.selectMember(member.getMemberId());
		return resultMember;
	}

	// 회원 탈퇴
	@DeleteMapping("/delete")
	public String deleteMemberInfo(@RequestBody MemberDelete memberDelete) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		String dbpw = memberService.getPassword(memberDelete.getMemberId());
		if (memberDelete.getPassword() != null && passwordEncoder.matches(memberDelete.getPassword(), dbpw)) {
			memberDelete.setPassword(dbpw);
			memberService.deleteMember(memberDelete);
			return memberDelete.getMemberId() + " 회원정보 삭제";
		} else {
			return "비밀번호를 다시 확인해주세요";
		}
	}

	// 아이디찾기
	@PostMapping("/find/username")
	public String findUsername(@RequestBody MemberFindInfo memberFindInfo) {
		String memberId = memberService.getId(memberFindInfo);
		;
		if (memberId == null) {
			memberId = "회원 정보가 존재하지 않습니다.";
		}
		return memberId;
	}

	// 비밀번호 찾기(새로운 비밀번호 발급 후 리턴)
	@PostMapping("/find/password")
	public String findPassword(@RequestBody MemberFindInfo memberFindInfo) {
		return memberService.updatePassword(memberFindInfo);
	}
	
	@GetMapping("/info")
	public String currentInfo(Principal principal) {
		String memberId = principal.getName();
		if (memberId==null) {
			return "로그인 안 된 상태입니다";
		}
		return "로그인 정보 \n" + memberService.selectMember(memberId);
	}
}
