package com.durin.web;

import java.net.URI;
import java.util.Map;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;

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

import com.durin.domain.Label;
import com.durin.domain.Result;
import com.durin.domain.User;
import com.durin.dto.LabelsDto;
import com.durin.exception.UnAuthorizedException;
import com.durin.security.LoginUser;
import com.durin.service.LabelService;

@RestController
@RequestMapping("/api/labels")
public class ApiLabelController {

	@Resource(name = "labelService")
	private LabelService labelService;

	
	@GetMapping("")
	public ResponseEntity<LabelsDto> getLabels(@LoginUser User loginUser) {
		return new ResponseEntity<LabelsDto>(LabelsDto.of(labelService.getLabels(loginUser)),HttpStatus.OK);
	}
	
	
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
			result = Result.fail(e.getMessage(),Result.ERROR_ID);
		}
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

}
