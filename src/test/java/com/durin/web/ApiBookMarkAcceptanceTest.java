package com.durin.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.durin.domain.BookMark;
import com.durin.dto.BookMarkDto;
import com.durin.repository.BookMarkRepository;

import support.test.HttpHeaderBuilder;


public class ApiBookMarkAcceptanceTest extends AcceptanceTest {

	private static final Logger log = LoggerFactory.getLogger(ApiBookMarkAcceptanceTest.class);


	@Autowired
	private BookMarkRepository bookMarkRepository;


	@Test
	public void create() {
		ResponseEntity<BookMark> response = basicAuthTemplate().postForEntity("/api/links", new BookMarkDto("https://www.google.com/", "구글", "구글페이지"), BookMark.class);
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		assertThat(response.getBody().getTitle(), is("Google"));
	}

	@Test
	public void update() {
		HttpHeaders headers = HttpHeaderBuilder.allJsonData();

		Map<String, Object> params = new HashMap<>();

		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
		ResponseEntity<BookMark> response = basicAuthTemplate().postForEntity("/api/links", new BookMarkDto("https://www.google.com/", "구글", "구글페이지"), BookMark.class);

		params.put("url", "https://www.naver.com/");
		params.put("title", "네이버");
		params.put("content", "네이버페이지");

		basicAuthTemplate().put("/api/links/" + response.getBody().getId(), request);

		assertThat(bookMarkRepository.findById(response.getBody().getId()).get().getContent(), is("네이버페이지"));
	}

	
	@Test
	public void delete() {
		ResponseEntity<BookMark> response = basicAuthTemplate().postForEntity("/api/links", new BookMarkDto("https://www.google.com/", "구글", "구글페이지"), BookMark.class);
		basicAuthTemplate().delete("/api/links/"+response.getBody().getId());
		assertThat(bookMarkRepository.findById(response.getBody().getId()).isPresent(), is(false));
}
	



}
