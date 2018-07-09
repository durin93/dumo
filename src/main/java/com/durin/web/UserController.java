package com.durin.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.durin.domain.User;
import com.durin.security.LoginUser;
import com.durin.service.AttachmentService;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Resource(name="attachmentService")
	private AttachmentService attachmentService;

	@GetMapping("/join")
	public String joinForm() {
		return "/users/form";
	}

	@GetMapping("/update")
	public String updateForm(@LoginUser User loginUser, Model model) {
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("profile", attachmentService.userProfile(loginUser));
		return "/users/updateForm";
	}
	@GetMapping("/friend")
	public String friendForm(@LoginUser User loginUser, Model model) {
		model.addAttribute("loginUser", loginUser);
		return "/users/friend";
	}
	
}
