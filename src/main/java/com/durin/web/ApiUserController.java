package com.durin.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Map;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.durin.domain.Result;
import com.durin.domain.User;
import com.durin.dto.SearchUserDto;
import com.durin.dto.UserDto;
import com.durin.security.ExistException;
import com.durin.security.HttpSessionUtils;
import com.durin.service.AttachmentService;
import com.durin.service.UserService;
import com.durin.validate.ValidationException;
import com.durin.validate.Validator;



@RestController
@RequestMapping("/api/users")
public class ApiUserController {

	private static final Logger log = LoggerFactory.getLogger(ApiUserController.class);

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "attachmentService")
	private AttachmentService attachmentService;

	@GetMapping("")
	public ResponseEntity<Result> joinForm() {
		return new ResponseEntity<Result>(Result.success(Result.JOIN_PAGE), HttpStatus.OK);
	}

	@PostMapping("login")
	public ResponseEntity<Result> login(HttpSession session, @RequestBody Map<String, String> data) {
		Result result;
		try {
			User loginUser = userService.login(data.get("userId"), data.get("password"));
			
			session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, loginUser);
			result = Result.success(Result.MAIN_PAGE);
		} catch (NullPointerException e) {
			result = Result.fail(e.getMessage(),Result.ERROR_ID);
		} catch (AuthenticationException e) {
			result = Result.fail(e.getMessage(),Result.ERROR_PASSWORD);
		}
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

	@GetMapping("logout")
	public ResponseEntity<Result> logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return new ResponseEntity<Result>(Result.success(Result.MAIN_PAGE), HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<Result> create(@Valid UserDto userDto, BindingResult bindingResult) {
	
		log.debug("create user {}",userDto.toString());
		
		Result result;
		HttpHeaders headers = new HttpHeaders();
		try {
			checkValidator(bindingResult);
			User loginUser = userService.add(userDto);
			headers.setLocation(URI.create(loginUser.generateUrl()));
			result = Result.success(Result.MAIN_PAGE);
		} catch (ExistException e) {
			log.debug("user create error {} ", e.getMessage());
			result = Result.fail(e.getMessage(),Result.ERROR_ID);
		} catch (ValidationException e) {
			log.debug("user create validate error {} ", e.getMessage());
			result = Result.fail(Validator.of(bindingResult));
		}
		return new ResponseEntity<Result>(result, headers, HttpStatus.CREATED);
	}

	@PostMapping("/kakao")
	public ResponseEntity<Result> createOauth(HttpSession session, UserDto userDto) throws UnsupportedEncodingException{
		User loginUser = null;
		Result result;
		loginUser = userService.addOauth(userDto);
		
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, loginUser);
		result = Result.success(Result.MAIN_PAGE);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

	@PutMapping("")
	// @RequestMapping(value = "", headers = ("content-type=multipart/*" ), method =
	// RequestMethod.PUT )
	public ResponseEntity<Result> update(@RequestPart("file") MultipartFile file, UserDto userDto)
			throws IllegalStateException, IOException {

		User loginUser;
		Result result;
		HttpHeaders headers = new HttpHeaders();
		try {
			loginUser = userService.update(userDto, file);
			headers.setLocation(URI.create(loginUser.generateUrl()));
			result = Result.success(Result.MAIN_PAGE);
		} catch (AuthenticationException e) {
			result = Result.fail(e.getMessage(),Result.ERROR_PASSWORD);
		}
		return new ResponseEntity<Result>(result, headers, HttpStatus.CREATED);
	}

	@GetMapping("{id}/info")
	public ResponseEntity<User> info(@PathVariable Long id) {
		return new ResponseEntity<User>(userService.findByUserId(id), HttpStatus.OK);
	}

	@GetMapping("search")
	public ResponseEntity<SearchUserDto> search(String userId) {
		log.debug("user search : {}", userId);
		try {
			SearchUserDto user = userService.searchUser(userId);
			return new ResponseEntity<SearchUserDto>(user, HttpStatus.OK);
		} catch (NullPointerException e) {
			log.debug("user search fail: {}", userId);
			return new ResponseEntity<SearchUserDto>(SearchUserDto.noData(), HttpStatus.OK);
		}
	}
	
	public void checkValidator(BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException();
		}
	}

}
