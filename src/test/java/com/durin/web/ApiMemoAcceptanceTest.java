package com.durin.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.security.sasl.AuthenticationException;

import org.aspectj.lang.annotation.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import com.durin.domain.Memo;
import com.durin.domain.MemoRepository;
import com.durin.domain.Pagination;
import com.durin.domain.Result;
import com.durin.domain.User;
import com.durin.domain.UserRepository;
import com.durin.dto.MemoDto;
import com.durin.dto.MemosDto;
import com.durin.security.LoginUser;


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

		Map<String, Object> params = new HashMap<>();
		params.put("title", "제목1");
		params.put("content", "내용1");

		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
		ResponseEntity<Memo> response = basicAuthTemplate().postForEntity("/api/memos/1", request, Memo.class);

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
		params.put("title", "새로운제목");
		params.put("content", "새로운내용");

		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);

		ResponseEntity<Memo> response = basicAuthTemplate().postForEntity("/api/memos/1", request, Memo.class);
		params.clear();
		params.put("title", "수정제목");
		params.put("content", "수정내용");

		HttpEntity<Map<String, Object>> request2 = new HttpEntity<Map<String, Object>>(params, headers);
		basicAuthTemplate().exchange("/api/memos/" + response.getBody().getId(), HttpMethod.PUT, request2,
				String.class);

		assertThat(memoRepository.findById(response.getBody().getId()).get().getContent(), is("수정내용"));
	}

	
	@Test
	public void delete() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> params = new HashMap<>();
		params.put("title", "새로운제목");
		params.put("content", "새로운내용");

		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);

		ResponseEntity<Memo> response = basicAuthTemplate().postForEntity("/api/memos/1", request, Memo.class);
		
		basicAuthTemplate().delete("/api/memos/"+response.getBody().getId());
		
		assertThat(memoRepository.findById(response.getBody().getId()).isPresent(), is(false));
}
	
	//이건 지금실패중
	@Test
	public void search() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("labelId", "1").addParameter("search", "1").addParameter("value", "1") .build();
		
		ResponseEntity<MemosDto> response = basicAuthTemplate().getForEntity("/api/memos/search", MemosDto.class, request);

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}



}
