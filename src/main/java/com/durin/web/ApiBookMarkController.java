package com.durin.web;

import java.net.URI;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.durin.domain.BookMark;
import com.durin.domain.User;
import com.durin.dto.BookMarkDto;
import com.durin.security.LoginUser;
import com.durin.service.BookMarkService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/links")
public class ApiBookMarkController {

	private BookMarkService bookMarkService;

	public ApiBookMarkController(BookMarkService bookMarkService) {
		this.bookMarkService = bookMarkService;
	}

	@PostMapping("")
	public ResponseEntity<BookMarkDto> create(@LoginUser User loginUser, @RequestBody BookMarkDto bookMarkDto) {
		HttpHeaders headers = new HttpHeaders();

		BookMarkDto bookMark  = bookMarkService.add(loginUser, bookMarkDto);
		bookMark.add(linkTo(ApiBookMarkController.class).slash(bookMark.getBookMarkId()).withSelfRel());
		headers.setLocation(linkTo(ApiBookMarkController.class).slash(bookMark.getBookMarkId()).toUri());

		return new ResponseEntity<>(bookMark, headers, HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<BookMarkDto> update(@LoginUser User loginUser, @PathVariable Long id,
                                           @RequestBody BookMarkDto bookMarkDto) throws AuthenticationException {

		HttpHeaders headers = new HttpHeaders();
		BookMarkDto bookMark  = bookMarkService.update(loginUser, id, bookMarkDto);
		bookMark.add(linkTo(ApiBookMarkController.class).slash(bookMark.getBookMarkId()).withSelfRel());
		headers.setLocation(linkTo(ApiBookMarkController.class).slash(bookMark.getBookMarkId()).toUri());

		return new ResponseEntity<>(bookMark,headers,HttpStatus.CREATED);
	}


	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@LoginUser User loginUser, @PathVariable Long id)
			throws AuthenticationException {
		bookMarkService.delete(loginUser, id);
		return new ResponseEntity<>(HttpStatus.OK);
	}


	@GetMapping("size")
	public ResponseEntity<Integer> userLinkSize(@LoginUser User loginUser) {
		return new ResponseEntity<>(bookMarkService.allLinkCount(loginUser), HttpStatus.OK);
	}

}
