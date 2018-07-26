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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.durin.domain.Memo;
import com.durin.domain.MemoRepository;
import com.durin.domain.User;
import com.durin.domain.UserRepository;
import com.durin.dto.MemoDto;
import com.durin.dto.MemosDto;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApiMemoAcceptanceTest {

	private static final Logger log = LoggerFactory.getLogger(ApiMemoAcceptanceTest.class);
	private static final String DEFAULT_LOGIN_USER = "lsc109";

	@Autowired
	private TestRestTemplate template;

	@Autowired
	private MemoRepository memoRepository;

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
		ResponseEntity<Memo> response = basicAuthTemplate().postForEntity("/api/memos/1", new MemoDto("제목1","내용1"), Memo.class);

		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		assertThat(response.getHeaders().getLocation().getPath(), is("/api/memos/13"));
	}

	@Rollback
	@Test
	public void update() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> params = new HashMap<>();

		ResponseEntity<Memo> response = basicAuthTemplate().postForEntity("/api/memos/1", new MemoDto("제목1","내용1"), Memo.class);
		params.put("title", "수정제목");
		params.put("content", "수정내용");

		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
		basicAuthTemplate().put("/api/memos/" + response.getBody().getId(), request);

		assertThat(memoRepository.findById(response.getBody().getId()).get().getContent(), is("수정내용"));
	}

	
	@Test
	public void delete() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		ResponseEntity<Memo> response = basicAuthTemplate().postForEntity("/api/memos/1", new MemoDto("제목1","내용1"), Memo.class);
		basicAuthTemplate().delete("/api/memos/"+response.getBody().getId());
		assertThat(memoRepository.findById(response.getBody().getId()).isPresent(), is(false));
}
	
	@Test
	public void search() {
		ResponseEntity<MemosDto> response = basicAuthTemplate().getForEntity("/api/memos/search?labelId=1&search=1&value=1", MemosDto.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}



}
