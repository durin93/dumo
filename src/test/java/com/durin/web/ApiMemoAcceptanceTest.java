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

import com.durin.domain.Memo;
import com.durin.domain.MemoRepository;
import com.durin.dto.MemoDto;
import com.durin.dto.MemosDto;

import support.test.HttpHeaderBuilder;


public class ApiMemoAcceptanceTest extends AcceptanceTest {

	private static final Logger log = LoggerFactory.getLogger(ApiMemoAcceptanceTest.class);

	@Autowired
	private MemoRepository memoRepository;

	@Test
	public void create() {
		ResponseEntity<Memo> response = basicAuthTemplate().postForEntity("/api/memos/1", new MemoDto("제목1","내용1"), Memo.class);

		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		assertThat(response.getHeaders().getLocation().getPath(), is("/api/memos/13"));
	}

	@Test
	public void update() {
		HttpHeaders headers = HttpHeaderBuilder.allJsonData();

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
