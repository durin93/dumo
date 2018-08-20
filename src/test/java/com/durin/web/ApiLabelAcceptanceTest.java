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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.durin.domain.Label;
import com.durin.domain.LabelRepository;

import support.test.HttpHeaderBuilder;


public class ApiLabelAcceptanceTest extends AcceptanceTest {

	private static final Logger log = LoggerFactory.getLogger(ApiLabelAcceptanceTest.class);
	
	@Autowired
	private LabelRepository labelRepository;

	@Test
	public void create() {
		
		HttpHeaders headers = HttpHeaderBuilder.allJsonData();

		Map<String, Object> params = new HashMap<>();
		params.put("title", "라벨제목");
		
		HttpEntity<Map<String,Object>> request = new HttpEntity<Map<String,Object>>(params ,headers);
		ResponseEntity<Label> response = basicAuthTemplate().postForEntity("/api/labels",request,Label.class);
		
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
	}

	@Test
	public void update() {
		HttpHeaders headers = HttpHeaderBuilder.allJsonData();
		
		Map<String, Object> params = new HashMap<>();
		params.put("title", "라벨제목");
		
		HttpEntity<Map<String,Object>> request = new HttpEntity<Map<String,Object>>(params ,headers);
		ResponseEntity<Label> response = basicAuthTemplate().postForEntity("/api/labels",request,Label.class);
		params.clear();		
		
		params.put("title", "수정라벨제목");
		
		HttpEntity<Map<String, Object>> request2 = new HttpEntity<Map<String, Object>>(params, headers);
		ResponseEntity<Label> response2 = basicAuthTemplate().exchange("/api/labels/"+response.getBody().getId(),HttpMethod.PUT, request2, Label.class);
		
		assertThat(response2.getStatusCode(), is(HttpStatus.CREATED));
		assertThat(labelRepository.findById(response.getBody().getId()).get().getTitle(), is("수정라벨제목"));

	}

}
