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

import com.durin.domain.Link;
import com.durin.domain.LinkRepository;
import com.durin.domain.User;
import com.durin.domain.UserRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApiLinkAcceptanceTest {

	private static final Logger log = LoggerFactory.getLogger(ApiLinkAcceptanceTest.class);
	private static final String DEFAULT_LOGIN_USER = "lsc109";

	@Autowired
	private TestRestTemplate template;

	@Autowired
	private LinkRepository linkRepository;

	@Autowired
	private UserRepository userRepository;

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
		params.put("url", "https://www.google.com/");
		params.put("title", "구글");
		params.put("content", "구글페이지");

		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
		ResponseEntity<Link> response = basicAuthTemplate().postForEntity("/api/links", request, Link.class);

		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		assertThat(response.getBody().getTitle(), is("Google"));
	}

	@Test
	public void update() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> params = new HashMap<>();
		params.put("url", "https://www.google.com/");
		params.put("title", "구글");
		params.put("content", "구글페이지");

		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
		ResponseEntity<Link> response = basicAuthTemplate().postForEntity("/api/links", request, Link.class);
		
		params.clear();
		params.put("url", "https://www.naver.com/");
		params.put("title", "네이버");
		params.put("content", "네이버페이지");

		HttpEntity<Map<String, Object>> request2 = new HttpEntity<Map<String, Object>>(params, headers);
		basicAuthTemplate().exchange("/api/links/" + response.getBody().getId(), HttpMethod.PUT, request2,
				String.class);

		assertThat(linkRepository.findById(response.getBody().getId()).get().getContent(), is("네이버페이지"));
	}

	
	@Test
	public void delete() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> params = new HashMap<>();
		params.put("url", "https://www.google.com/");
		params.put("title", "구글");
		params.put("content", "구글페이지");

		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);

		ResponseEntity<Link> response = basicAuthTemplate().postForEntity("/api/links", request, Link.class);
		
		basicAuthTemplate().delete("/api/links/"+response.getBody().getId());
		
		assertThat(linkRepository.findById(response.getBody().getId()).isPresent(), is(false));
}
	



}
