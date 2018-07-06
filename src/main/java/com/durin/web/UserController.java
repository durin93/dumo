package com.durin.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.durin.domain.User;
import com.durin.security.LoginUser;

@Controller
@RequestMapping("/users")
public class UserController {

	@GetMapping("/join")
	public String joinForm() {
		return "/users/form";
	}

	@GetMapping("/update")
	public String updateForm(@LoginUser User loginUser, Model model) {
		model.addAttribute("loginUser", loginUser);
		return "/users/updateForm";
	}
	
}
