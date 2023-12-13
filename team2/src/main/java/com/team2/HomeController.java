package com.team2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@GetMapping("/")
	public @ResponseBody String home(Model model) {
		model.addAttribute("message", "Welcome");
		return "home";
	}

}
