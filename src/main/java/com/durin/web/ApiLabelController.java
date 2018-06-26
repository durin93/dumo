package com.durin.web;

import java.net.URI;
import java.util.Map;

import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.durin.UnAuthorizedException;
import com.durin.domain.Label;
import com.durin.domain.Link;
import com.durin.domain.Result;
import com.durin.domain.User;
import com.durin.security.LoginUser;
import com.durin.service.LabelService;
import com.durin.service.LinkService;

@RestController
@RequestMapping("/api/labels")
public class ApiLabelController {

	@Resource(name = "labelService")
	private LabelService labelService;

	@PostMapping("")
	public ResponseEntity<Label> create(@LoginUser User loginUser, @RequestBody Map<String, String> data) {
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/api/labels"));
		return new ResponseEntity<Label>(labelService.add(loginUser, data.get("title")),
				HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<Label> update(@LoginUser User loginUser, @PathVariable Long id,
			@RequestBody Map<String, String> data) throws AuthenticationException {
		return new ResponseEntity<Label>(labelService.update(loginUser, id, data.get("title")),
				HttpStatus.CREATED);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Result> delete(@LoginUser User loginUser, @PathVariable Long id) throws AuthenticationException{
		Result result;
		try {
			labelService.delete(loginUser, id);
			result = Result.success("/label/list");
		} catch (UnAuthorizedException e) {
			result = Result.failById(e.getMessage());
		}
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

}
