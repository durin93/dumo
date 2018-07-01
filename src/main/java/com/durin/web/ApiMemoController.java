package com.durin.web;

import java.net.URI;
import java.util.Map;

import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
		Page<Memo> postPage = memoService.findAll(pagination , loginUser);
		return new ResponseEntity<MemosDto>(MemosDto.of(postPage,  pagination),HttpStatus.OK);
	}
	
	@GetMapping("/{labelId}")
	public ResponseEntity<MemosDto> labelIdList(@LoginUser User loginUser, @PathVariable Long labelId) {
		Pagination pagination = Pagination.of(labelId);
		Page<Memo> postPage = memoService.findAll(pagination, loginUser);
		return new ResponseEntity<MemosDto>(MemosDto.of(postPage,  pagination),HttpStatus.OK);
	}
	
	@GetMapping("/{labelId}/{page}")
	public ResponseEntity<MemosDto> labelIdPageList(@LoginUser User loginUser, @PathVariable Long labelId, @PathVariable Integer page) {
		Pagination pagination = Pagination.of(page,labelId);
		Page<Memo> postPage = memoService.findAll(pagination, loginUser);
		return new ResponseEntity<MemosDto>(MemosDto.of(postPage,  pagination),HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public  ResponseEntity<MemosDto> search(@LoginUser User loginUser, String labelId, String search, String value) {
		return new ResponseEntity<MemosDto>(MemosDto.of(memoService.findAllBySearch(Long.parseLong(labelId), search, value)),HttpStatus.OK);
	}
	

	
	@PostMapping("/{labelId}")
	public ResponseEntity<Memo> create(@LoginUser User loginUser,  @PathVariable Long labelId, @RequestBody Map<String, String> data) {
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/api/memos"));
		return new ResponseEntity<Memo>(memoService.add(loginUser, labelId, data.get("title"), data.get("content")),
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

	@GetMapping("/size")
	public ResponseEntity<Integer> userMemoSize(@LoginUser User loginUser) {
		return new ResponseEntity<Integer>(memoService.allMemoCount(loginUser), HttpStatus.OK);
	}

}
