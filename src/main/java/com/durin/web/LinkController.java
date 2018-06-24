package com.durin.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.durin.domain.User;
import com.durin.security.LoginUser;
import com.durin.service.MemoService;
import com.durin.service.UserService;

@Controller
@RequestMapping("/link")
public class LinkController {
	

	@Resource(name="userService")
	private UserService userService;
	
	@GetMapping("/list")
	public String list(@LoginUser User loginUser, Model model) {
		model.addAttribute("loginUser", userService.findByUser(loginUser));
		model.addAttribute("click_link",true);
		return "link/list";
	}
}
