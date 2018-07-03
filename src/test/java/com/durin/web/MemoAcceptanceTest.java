package com.durin.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.durin.domain.Memo;
import com.durin.domain.MemoRepository;
import com.durin.domain.User;
import com.durin.domain.UserRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MemoAcceptanceTest {

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
		
		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params,
				headers);
		ResponseEntity<Memo> response = basicAuthTemplate().postForEntity("/api/memos/1", request, Memo.class);
		
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		assertThat(response.getHeaders().getLocation().getPath(), is("/api/memos/13"));
	}

	@Test
	public void update() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> params = new HashMap<>();
		params.put("title", "제목");
		params.put("content", "내용");
		
		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params,
				headers);
		
		
		ResponseEntity<Memo> response = basicAuthTemplate().postForEntity("/api/memos/1", request, Memo.class);
		Long id = response.getBody().getId();

		
		Map<String, Object> params2 = new HashMap<>();
		params.put("title", "제목1");
		params.put("content", "수정");
		
		HttpEntity<Map<String, Object>> request2 = new HttpEntity<Map<String, Object>>( params2,headers);
		

		basicAuthTemplate().put("/api/memos/"+id,  request2);

		assertThat(memoRepository.findById(id).get().getContent() , is("수정"));
	}
  
	


}
