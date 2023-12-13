package com.team2.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.member.model.Member;
import com.team2.member.service.IMemberService;

@RestController
@RequestMapping("/member")
public class MemberController {
   
   @Autowired
   IMemberService memberService;

   // insertMember
   @PostMapping("/signup")
	public Member insertEmp(@RequestBody Member member) {
		memberService.insertMember(member);
		return member;
   }
   
   // login
   @GetMapping(value="/login")
	public String login() {
		return "member/login";
	}
   
   // updateMember
   // deleteMember
   //sdfsdfsdfsdfsdf
}
