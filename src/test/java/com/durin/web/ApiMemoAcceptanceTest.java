package com.durin.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;

import com.durin.domain.Memo;
import com.durin.dto.MemoDto;
import com.durin.dto.MemosDto;
import com.durin.repository.MemoRepository;


public class ApiMemoAcceptanceTest extends AcceptanceTest {

    private static final Logger log = LoggerFactory.getLogger(ApiMemoAcceptanceTest.class);

    @Autowired
    private MemoRepository memoRepository;

    @Test
    public void create() throws Exception {
        MemoDto response =
                basicAuthDefaultClient()
                        .post().uri("/api/memos/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(new MemoDto("제목1", "내용1")))
                        .exchange()
                        .expectStatus().isCreated()
                        .expectHeader().exists("Location")
                        .expectBody(MemoDto.class)
                        .consumeWith(result -> assertThat(result.getResponseBody().getTitle(), is("제목1")))
                        .consumeWith(result -> assertThat(result.getResponseBody().getContent(), is("내용1")))
                        .consumeWith(result -> assertFalse(result.getResponseBody().getLinkz().isEmpty()))
                        .returnResult().getResponseBody();
        log.debug(response.getLinkz().toString());
    }

    @Test
    public void update() {

        MemoDto response =
                basicAuthDefaultClient()
                        .put().uri("/api/memos/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(new MemoDto("수정제목", "수정내용")))
                        .exchange()
                        .expectStatus().isCreated()
                        .expectHeader().exists("Location")
                        .expectBody(MemoDto.class)
                        .consumeWith(result -> assertThat(result.getResponseBody().getTitle(), is("수정제목")))
                        .consumeWith(result -> assertThat(result.getResponseBody().getContent(), is("수정내용")))
                        .consumeWith(result -> assertFalse(result.getResponseBody().getLinkz().isEmpty()))
                        .returnResult().getResponseBody();
        log.debug(response.getLinkz().toString());


//		HttpHeaders headers = HttpHeaderBuilder.allJsonData();
//
//		Map<String, Object> params = new HashMap<>();
//
//		ResponseEntity<Memo> response = basicAuthTemplate().postForEntity("/api/memos/1", new MemoDto("제목1","내용1"), Memo.class);
//		params.put("title", "수정제목");
//		params.put("content", "수정내용");
//
//		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
//		basicAuthTemplate().put("/api/memos/" + response.getBody().getId(), request);
//
//		assertThat(memoRepository.findById(response.getBody().getId()).get().getContent(), is("수정내용"));
    }


    @Test
    public void delete() {
        ResponseEntity<MemoDto> response = basicAuthTemplate().postForEntity("/api/memos/1", new MemoDto("제목1", "내용1"), MemoDto.class);
        basicAuthTemplate().delete("/api/memos/" + response.getBody().getId());
        assertThat(memoRepository.findById(response.getBody().getMemoId()).isPresent(), is(false));
    }

    @Test
    public void search() {
        ResponseEntity<MemosDto> response = basicAuthTemplate().getForEntity("/api/memos/search?labelId=1&search=1&value=1", MemosDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }


}
