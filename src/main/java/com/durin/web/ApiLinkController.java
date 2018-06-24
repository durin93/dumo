package com.durin.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Map;

import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.durin.domain.Link;
import com.durin.domain.Result;
import com.durin.domain.User;
import com.durin.security.LoginUser;
import com.durin.service.LinkService;

@RestController
@RequestMapping("/api/link")
public class ApiLinkController {

	@Resource(name = "linkService")
	private LinkService linkService;

	@PostMapping("")
	public ResponseEntity<Link> create(@LoginUser User loginUser, @RequestBody Map<String, String> data) {
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/api/link"));
		return new ResponseEntity<Link>(linkService.add(loginUser, data.get("title"), data.get("content"), data.get("url")),
				HttpStatus.CREATED);
	}

	
	
	@PutMapping("{id}")
	public ResponseEntity<Link> update(@LoginUser User loginUser, @PathVariable Long id,
			@RequestBody Map<String, String> data) throws AuthenticationException {
		return new ResponseEntity<Link>(linkService.update(loginUser, id, data.get("title"), data.get("content"),data.get("url")),
				HttpStatus.CREATED);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Result> delete(@LoginUser User loginUser, @PathVariable Long id){
		Result result;
		try {
			linkService.delete(loginUser, id);
			result = Result.success("/memo/list");
		} catch (AuthenticationException e) {
			result = Result.fail_match();
		}
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

}
