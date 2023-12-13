package com.team2.reply.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReplyController { 

   @GetMapping("/reply")
   public @ResponseBody String index() {
      return "하이그대들ssss"; 
   }
}