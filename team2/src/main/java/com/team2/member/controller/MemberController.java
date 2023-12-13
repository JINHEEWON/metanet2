package com.team2.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {

   @GetMapping("/member")
   public @ResponseBody String index() {
      return "하이그대들fffff"; 
   }
}
