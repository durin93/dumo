package com.durin.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.durin.security.HttpSessionUtils;

@Controller
@RequestMapping("/users")
public class UserController {

	@GetMapping("")
	public String joinForm() {
		return "/users/form";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return "redirect:/";
	}

	
}
