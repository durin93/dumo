package com.durin.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import com.durin.domain.Result;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import com.durin.dto.MemoDto;
import com.durin.dto.MemosDto;
import com.durin.repository.MemoRepository;


public class ApiMemoAcceptanceTest extends AcceptanceTest {

    private static final Logger log = LoggerFactory.getLogger(ApiMemoAcceptanceTest.class);

    @Autowired
    private MemoRepository memoRepository;

    @Test
    public void create() {
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

        MemoDto createMemo =
                basicAuthDefaultClient()
                        .post().uri("/api/memos/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(new MemoDto("제목1", "내용1")))
                        .exchange().expectBody(MemoDto.class).returnResult().getResponseBody();

        MemoDto response =
                basicAuthDefaultClient()
                        .put().uri("/api/memos/" + createMemo.getMemoId())
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

    }


    @Test
    public void delete() {

        MemoDto createMemo =
                basicAuthDefaultClient()
                        .post().uri("/api/memos/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(new MemoDto("제목1", "내용1")))
                        .exchange().expectBody(MemoDto.class).returnResult().getResponseBody();

        basicAuthDefaultClient()
                .delete().uri("/api/memos/" + createMemo.getMemoId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Result.class)
                .consumeWith(result -> assertThat(result.getResponseBody().isValid(), is(true)));
        assertThat(memoRepository.findById(createMemo.getMemoId()).isPresent(), is(false));

    }

    @Test
    public void search() {
        MemosDto response =
        basicAuthDefaultClient()
                .get().uri("/api/memos/search?labelId=1&search=title&value=제목")
                .exchange()
                .expectStatus().isOk()
                .expectBody(MemosDto.class)
                .consumeWith(result -> assertNotNull(result.getResponseBody().getMemos()))
                .returnResult().getResponseBody();
        log.debug(response.getMemos().toString());
    }

    @Test
    public void defaultMainList(){
        MemosDto response =
                basicAuthDefaultClient()
                        .get().uri("/api/memos")
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(MemosDto.class)
                        .consumeWith(result -> assertNotNull(result.getResponseBody().getMemos()))
                        .returnResult().getResponseBody();
        log.debug(response.getMemos().toString());
    }

}
