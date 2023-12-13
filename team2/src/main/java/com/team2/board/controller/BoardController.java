package com.team2.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {

   @GetMapping("/")
   public @ResponseBody String index() {
      return "하이엉히"; 
   }
}