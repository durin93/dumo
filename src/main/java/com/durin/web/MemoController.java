package com.durin.web;



import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.durin.domain.Memo;
import com.durin.domain.Pagination;
import com.durin.domain.User;
import com.durin.security.LoginUser;
import com.durin.service.MemoService;
import com.durin.service.UserService;

@Controller
@RequestMapping("/memos")
public class MemoController {

	@Resource(name = "memoService")
	private MemoService memoService;

	@Resource(name = "userService")
	private UserService userService;
	@GetMapping("")
	public String defaultMainList(@LoginUser User loginUser, Model model) {
		model = makeModel(model, loginUser, Pagination.of());
		return "memo/list";
	}

	public Model makeModel(Model model, User loginUser, Pagination pagination) {
		Page<Memo> postPage = memoService.findAll(pagination, loginUser);
		model.addAttribute("loginUser",  userService.pullUserInfo(loginUser));
		model.addAttribute("pagination", pagination.makePagination(postPage.getTotalPages()));
		return model;
	}
}
