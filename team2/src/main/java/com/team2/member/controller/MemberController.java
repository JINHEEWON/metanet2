package com.team2.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.team2.member.service.IMemberService;

@RestController
@RequestMapping("/member")
public class MemberController {
   
   @Autowired
   IMemberService memberService;

   // insertMember
   // login
   // updateMember
   // deleteMember
}
