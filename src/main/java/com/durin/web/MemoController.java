package com.durin.web;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/{labelId}")
	public String defaultMainList(@LoginUser User loginUser, Model model, @PathVariable Long labelId) {
		model = makeModel(model, loginUser, Pagination.of(labelId));
		return "memo/list";
	}

	@GetMapping("/{labelId}/{page}")
	public String list(@LoginUser User loginUser, @PathVariable Long labelId, @PathVariable Integer page, Model model) {
		model = makeModel(model, loginUser, Pagination.of(page,labelId));
		return "memo/list";
	}

	public Model makeModel(Model model, User loginUser, Pagination pagination) {
		Page<Memo> postPage = memoService.findAll(pagination.getLabelId(), pagination.makePageReqeest());
		model.addAttribute("loginUser", userService.findByUser(loginUser));
		model.addAttribute("memos", postPage.getContent());
		model.addAttribute("click_memo", true);
		model.addAttribute("pagination", pagination.makePagination(postPage.getTotalPages()));
		return model;
	}

	@GetMapping("/{labelId}/{search}/{searchVal}")
	public String list(@LoginUser User loginUser, @PathVariable Long labelId, Model model, @PathVariable String search,
			@PathVariable String searchVal) {
		model.addAttribute("loginUser", userService.findByUser(loginUser));
		Pagination pagination = Pagination.of(1, labelId);

		Page<Memo> postPage;
		if (search.equals("title")) {
			postPage = memoService.findAllByTitle(labelId, pagination.makePageReqeest(), searchVal);
		}
		postPage = memoService.findAllByTitle(labelId, pagination.makePageReqeest(), searchVal);

		model.addAttribute("memos", postPage.getContent());
		model.addAttribute("click_memo", true);
		model.addAttribute("pagination", pagination.makePagination(postPage.getTotalPages()));

		return "memo/list";
	}

}
