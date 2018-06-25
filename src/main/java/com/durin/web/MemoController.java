package com.durin.web;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.durin.domain.Memo;
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
	
	@Resource(name="memoService")
	private MemoService memoService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@GetMapping("/list")
	public String list(@LoginUser User loginUser,  Model model) {

		model.addAttribute("loginUser", userService.findByUser(loginUser));
		Page<Memo> postPage = memoService.findAll(1L, PageRequest.of(0, 9, Sort.Direction.DESC, "createDate"));
		model.addAttribute("memos", postPage.getContent());
		model.addAttribute("click_memo",true);
		return "memo/list";
	}

	@GetMapping("/list/{labelId}/{page}")
	public String list(@LoginUser User loginUser, @PathVariable Long labelId, @PathVariable int page, Model model) {
		model.addAttribute("loginUser", userService.findByUser(loginUser));
		Page<Memo> postPage = memoService.findAll(labelId, PageRequest.of(page, 9, Sort.Direction.DESC, "createDate"));
		model.addAttribute("memos", postPage.getContent());
		model.addAttribute("click_memo",true);
		return "memo/list";
	}
}
