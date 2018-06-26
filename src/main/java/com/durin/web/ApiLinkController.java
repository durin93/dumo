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
import org.springframework.web.client.RestTemplate;

import com.durin.domain.Link;
import com.durin.domain.Result;
import com.durin.domain.User;
import com.durin.security.LoginUser;
import com.durin.service.LinkService;

@RestController
@RequestMapping("/api/links")
public class ApiLinkController {
	
	private static final int TITLE_START_LENGTH = 7;

	@Resource(name = "linkService")
	private LinkService linkService;

	@PostMapping("")
	public ResponseEntity<Link> create(@LoginUser User loginUser, @RequestBody Map<String, String> data) {
		String title = getUrlTitle(data.get("url"), data.get("title"));
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/api/links"));
		return new ResponseEntity<Link>(linkService.add(loginUser, title, data.get("content"), data.get("url")),
				HttpStatus.CREATED);
	}

	
	
	@PutMapping("{id}")
	public ResponseEntity<Link> update(@LoginUser User loginUser, @PathVariable Long id,
			@RequestBody Map<String, String> data) throws AuthenticationException {
		String title = getUrlTitle(data.get("url"), data.get("title"));
		return new ResponseEntity<Link>(linkService.update(loginUser, id, title, data.get("content"),data.get("url")),
				HttpStatus.CREATED);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Result> delete(@LoginUser User loginUser, @PathVariable Long id){
		Result result;
		try {
			linkService.delete(loginUser, id);
			result = Result.success("/api/links");
		} catch (AuthenticationException e) {
			result = Result.failById(e.getMessage());
		}
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}
	
	public String getUrlTitle(String url, String title) {
		try {
			ResponseEntity<String> res = new RestTemplate().getForEntity(url, String.class);
			String urlBody = res.getBody().toString();
			title = urlBody.substring(urlBody.indexOf("<title>")+TITLE_START_LENGTH, urlBody.indexOf("</title>"));
		} catch (Exception e) {
			title = "unable to connect";
		}
		return title;
	}
	

}
