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

import com.durin.domain.Link;
import com.durin.domain.User;
import com.durin.dto.LinkDto;
import com.durin.security.LoginUser;
import com.durin.service.LinkService;

@RestController
@RequestMapping("/api/links")
public class ApiLinkController {

	@Resource(name = "linkService")
	private LinkService linkService;

	@PostMapping("")
	public ResponseEntity<Link> create(@LoginUser User loginUser, @RequestBody LinkDto linkDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/api/links"));
		return new ResponseEntity<Link>(linkService.add(loginUser, linkDto), HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<Link> update(@LoginUser User loginUser, @PathVariable Long id,
			@RequestBody LinkDto linkDto) throws AuthenticationException {
		return new ResponseEntity<Link>(linkService.update(loginUser, id, linkDto),
				HttpStatus.CREATED);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@LoginUser User loginUser, @PathVariable Long id)
			throws AuthenticationException {
		linkService.delete(loginUser, id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}


	@GetMapping("size")
	public ResponseEntity<Integer> userLinkSize(@LoginUser User loginUser) {
		return new ResponseEntity<Integer>(linkService.allLinkCount(loginUser), HttpStatus.OK);
	}

}
