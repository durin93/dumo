package com.durin.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.durin.domain.User;
import com.durin.security.LoginUser;
import com.durin.service.LabelService;
import com.durin.service.MemoService;
import com.durin.service.UserService;

@Controller
@RequestMapping("/memo")
public class MemoController {
	
	
	@Resource(name="labelService")
	private LabelService labelService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@GetMapping("/list")
	public String list(@LoginUser User loginUser,  Model model) {
		model.addAttribute("loginUser", userService.findByUser(loginUser));
		model.addAttribute("label", labelService.getLabel(1L));
		model.addAttribute("click_memo",true);
		return "memo/list";
	}

	@GetMapping("/list/{labelId}")
	public String list(@LoginUser User loginUser, @PathVariable Long labelId, Model model) {
		model.addAttribute("loginUser", userService.findByUser(loginUser));
		model.addAttribute("label", labelService.getLabel(labelId));
		model.addAttribute("click_memo",true);
		return "memo/list";
	}
}
