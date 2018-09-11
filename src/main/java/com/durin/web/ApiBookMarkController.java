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

@RestController
@RequestMapping("/api/links")
public class ApiBookMarkController {

	@Resource(name = "bookMarkService")
	private BookMarkService bookMarkService;

	@PostMapping("")
	public ResponseEntity<BookMark> create(@LoginUser User loginUser, @RequestBody BookMarkDto linkDto) {
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<BookMark>(bookMarkService.add(loginUser, linkDto), HttpStatus.CREATED);
	}

    /*@PostMapping("{labelId}")
    public ResponseEntity<MemoDto> create(@LoginUser User loginUser, @PathVariable Long labelId, @RequestBody MemoDto memoDto) {

        MemoDto memo = memoService.add(loginUser, labelId, memoDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(ApiMemoController.class).slash(memo.getMemoId()).toUri());
        memo.add(linkTo(ApiMemoController.class).slash(memo.getMemoId()).withSelfRel());
        return new ResponseEntity<>(memo,headers,HttpStatus.CREATED);
    }*/

	@PutMapping("{id}")
	public ResponseEntity<BookMark> update(@LoginUser User loginUser, @PathVariable Long id,
                                           @RequestBody BookMarkDto linkDto) throws AuthenticationException {
		return new ResponseEntity<BookMark>(bookMarkService.update(loginUser, id, linkDto),
				HttpStatus.CREATED);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@LoginUser User loginUser, @PathVariable Long id)
			throws AuthenticationException {
		bookMarkService.delete(loginUser, id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}


	@GetMapping("size")
	public ResponseEntity<Integer> userLinkSize(@LoginUser User loginUser) {
		return new ResponseEntity<Integer>(bookMarkService.allLinkCount(loginUser), HttpStatus.OK);
	}

}
