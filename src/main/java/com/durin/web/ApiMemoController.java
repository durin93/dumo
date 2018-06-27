package com.durin.web;

import java.net.URI;
import java.util.Map;

import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.durin.domain.Memo;
import com.durin.dto.MemosDto;
import com.durin.domain.Pagination;
import com.durin.domain.Result;
import com.durin.domain.User;
import com.durin.security.LoginUser;
import com.durin.service.MemoService;

@RestController
@RequestMapping("/api/memos")
public class ApiMemoController {

	@Resource(name = "memoService")
	private MemoService memoService;

	
	@GetMapping("")
	public ResponseEntity<MemosDto> defaultMainList(@LoginUser User loginUser) {
		Pagination pagination = Pagination.of();
		Page<Memo> postPage = memoService.findAll(pagination.getLabelId(), pagination.makePageReqeest());
		MemosDto memos = new MemosDto();
		memos.setMemos(postPage.getContent());
		return new ResponseEntity<MemosDto>(memos,HttpStatus.OK);
	}
	
	
	@PostMapping("")
	public ResponseEntity<Memo> create(@LoginUser User loginUser, @RequestBody Map<String, String> data) {
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/api/memos"));
		return new ResponseEntity<Memo>(memoService.add(loginUser, data.get("title"), data.get("content")),
				HttpStatus.CREATED);
	}
	

	@PutMapping("{id}")
	public ResponseEntity<Memo> update(@LoginUser User loginUser, @PathVariable Long id,
			@RequestBody Map<String, String> data) throws AuthenticationException {
		return new ResponseEntity<Memo>(memoService.update(loginUser, id, data.get("title"), data.get("content")),
				HttpStatus.CREATED);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Result> delete(@LoginUser User loginUser, @PathVariable Long id){
		Result result;
		try {
			memoService.delete(loginUser, id);
			result = Result.success();
		} catch (AuthenticationException e) {
			result = Result.failById(e.getMessage());
		}
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

}
