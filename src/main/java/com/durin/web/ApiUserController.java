package com.durin.web;

import java.net.URI;
import java.util.Map;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.durin.domain.Result;
import com.durin.domain.User;
import com.durin.dto.UserDto;
import com.durin.security.HttpSessionUtils;
import com.durin.security.LoginUser;
import com.durin.service.UserService;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

	@Resource(name = "userService")
	private UserService userService;

	
	@GetMapping("")
	public ResponseEntity<Result> joinForm() {
		return new ResponseEntity<Result>(Result.successJoinForm(), HttpStatus.OK);
	}
	
	@PostMapping("login")
	public ResponseEntity<Result> login(HttpSession session, @RequestBody Map<String, String> data) {
		Result result;
		try {
			User loginUser = userService.login(data.get("userId"), data.get("password"));
			session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, loginUser);
			result = Result.success();
		} catch (NullPointerException e) {
			result = Result.failById(e.getMessage());
		} catch (AuthenticationException e) {
			result = Result.faildByPassword(e.getMessage());
		}
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}
	
	@GetMapping("logout")
	public ResponseEntity<Result> logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return new ResponseEntity<Result>(Result.success(), HttpStatus.OK);
	}


	@PostMapping("")
	public ResponseEntity<Result> create(@RequestBody UserDto userDto) {
		User loginUser;
		Result result;
		HttpHeaders headers = new HttpHeaders();
		try {
			loginUser = userService.add(userDto);
			headers.setLocation(URI.create(loginUser.generateUrl()));
			result = Result.success();
		} catch (Exception e) {
			result = Result.failById(e.getMessage());
		}
		return new ResponseEntity<Result>(result, headers, HttpStatus.CREATED);
	}
    
	
	
}
