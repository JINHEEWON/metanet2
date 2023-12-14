package com.team2.member.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.team2.member.model.Member;
import com.team2.member.service.IMemberService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	IMemberService memberService;

//	@Autowired
//	MemberValidator memberValidator;

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
}
