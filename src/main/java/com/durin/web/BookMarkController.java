package com.durin.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.durin.domain.User;
import com.durin.security.LoginUser;
import com.durin.service.UserService;

@Controller
@RequestMapping("/links")
public class BookMarkController {
	

	@Resource(name="userService")
	private UserService userService;
	
	@GetMapping("")
	public String list(@LoginUser User loginUser, Model model) {
		model.addAttribute("loginUser",  userService.pullUserInfo(loginUser));
		return "link/list";
	}
}
