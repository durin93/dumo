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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.durin.domain.Result;
import com.durin.domain.User;
import com.durin.domain.UserRepository;
import com.durin.dto.SearchUserDto;
import com.durin.dto.UserDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApiUserAcceptanceTest {

	private static final Logger log = LoggerFactory.getLogger(ApiUserAcceptanceTest.class);
	private static final String DEFAULT_LOGIN_USER = "lsc109";

	@Autowired
	private TestRestTemplate template;

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

		ResponseEntity<Result> response = basicAuthTemplate().postForEntity("/api/users",
				new UserDto("gram", "1234", "그램"), Result.class);

		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		assertThat(response.getBody().isValid(), is(true));
		assertThat(response.getBody().getUrl(), is("/memos"));
	}

	@Test
	public void login() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

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
