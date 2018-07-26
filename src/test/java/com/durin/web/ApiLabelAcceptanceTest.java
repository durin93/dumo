package com.durin.web;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.durin.domain.Label;
import com.durin.domain.LabelRepository;
import com.durin.domain.User;
import com.durin.domain.UserRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ApiLabelAcceptanceTest {

	private static final Logger log = LoggerFactory.getLogger(ApiLabelAcceptanceTest.class);
	private static final String DEFAULT_LOGIN_USER = "lsc109";
	
	@Autowired
	private TestRestTemplate template;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LabelRepository labelRepository;

	public TestRestTemplate basicAuthTemplate() {
		return basicAuthTemplate(findByUserId(DEFAULT_LOGIN_USER));
	}

	public TestRestTemplate basicAuthTemplate(User loginUser) {
		return template.withBasicAuth(loginUser.getUserId(), loginUser.getPassword());
	}

	private User findByUserId(String userId) {
		return userRepository.findByUserId(userId).get();
	}

	@Test
	public void create() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> params = new HashMap<>();
		params.put("title", "라벨제목");
		
		HttpEntity<Map<String,Object>> request = new HttpEntity<Map<String,Object>>(params ,headers);
		ResponseEntity<Label> response = basicAuthTemplate().postForEntity("/api/labels",request,Label.class);
		
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
	}

	@Test
	public void update() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		
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
