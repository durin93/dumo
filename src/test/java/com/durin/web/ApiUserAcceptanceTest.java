package com.durin.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.durin.domain.Result;
import com.durin.dto.SearchUserDto;

import support.test.HttpHeaderBuilder;


public class ApiUserAcceptanceTest extends AcceptanceTest{
	private static final Logger log = LoggerFactory.getLogger(ApiUserAcceptanceTest.class);
	

	@Test
	public void create() {
		HttpHeaders headers = HttpHeaderBuilder.jsonAndFormData();

		MultiValueMap<String, String> queryString = new LinkedMultiValueMap<>();
		queryString.add("userId", "gram");
		queryString.add("password", "1234");
		queryString.add("name", "이그램");
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String,String>>(queryString,headers);
		ResponseEntity<Result> response = template.postForEntity("/api/users",
				request, Result.class);

		log.debug("create test : {}", response.getBody());
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		assertThat(response.getBody().isValid(), is(true));
		assertThat(response.getBody().getUrl(), is("/memos"));
	}

	@Test
	public void login() {
		HttpHeaders headers = HttpHeaderBuilder.allJsonData();

		Map<String, Object> params = new HashMap<>();
		params.put("userId", "lsc109");
		params.put("password", "1234");
		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
		ResponseEntity<Result> response = basicAuthTemplate().postForEntity("/api/users/login", request, Result.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody().isValid(), is(true));
	}

	@Test
	public void logout() {
		ResponseEntity<Result> response = basicAuthTemplate().getForEntity("/api/users/logout", Result.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody().isValid(), is(true));
	}

	@Test
	public void search() {
		ResponseEntity<SearchUserDto> response = basicAuthTemplate().getForEntity("/api/users/search?userId=lsc109",
				SearchUserDto.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody().getUserId(), is("lsc109"));
		assertThat(response.getBody().getName(), is("일승철"));
	}

}
