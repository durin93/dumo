package com.durin.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import com.durin.domain.BookMark;
import com.durin.dto.BookMarkDto;
import com.durin.repository.BookMarkRepository;

import org.springframework.web.reactive.function.BodyInserters;
import support.test.HttpHeaderBuilder;


public class ApiBookMarkAcceptanceTest extends AcceptanceTest {

    private static final Logger log = LoggerFactory.getLogger(ApiBookMarkAcceptanceTest.class);


    @Autowired
    private BookMarkRepository bookMarkRepository;


    @Test
    public void create() {
        BookMarkDto createBookMark =
                basicAuthDefaultClient().post()
                        .uri("/api/links")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(new BookMarkDto("https://www.google.com/", "구글", "구글페이지")))
                        .exchange()
                        .expectStatus().isCreated()
                        .expectHeader().exists("Location")
                        .expectBody(BookMarkDto.class)
                        .consumeWith(result -> assertThat(result.getResponseBody().getTitle(), is("Google")))
                        .returnResult().getResponseBody();
        log.debug(createBookMark.toString());
    }

    @Test
    public void update() {

        BookMarkDto createBookMark =
                basicAuthDefaultClient().post()
                        .uri("/api/links")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(new BookMarkDto("https://www.google.com/", "구글", "구글페이지")))
                        .exchange()
                        .expectBody(BookMarkDto.class)
                        .returnResult().getResponseBody();


        BookMarkDto updateBookMark =
                basicAuthDefaultClient().put()
                        .uri("/api/links/" + createBookMark.getBookMarkId())
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(new BookMarkDto("https://www.naver.com/", "네이버", "네이버페이지")))
                        .exchange()
                        .expectBody(BookMarkDto.class)
                        .consumeWith(result -> assertThat(result.getResponseBody().getTitle(), is("NAVER")))
                        .returnResult().getResponseBody();
        log.debug(updateBookMark.toString());
    }


    @Test
    public void delete() {
        BookMarkDto createBookMark =
                basicAuthDefaultClient().post()
                        .uri("/api/links")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(new BookMarkDto("https://www.google.com/", "구글", "구글페이지")))
                        .exchange()
                        .expectBody(BookMarkDto.class)
                        .returnResult().getResponseBody();

        basicAuthDefaultClient()
                .delete().uri("/api/links/" + createBookMark.getBookMarkId())
                .exchange()
                .expectStatus().isOk();
        assertThat(bookMarkRepository.findById(createBookMark.getBookMarkId()).isPresent(), is(false));
    }


}
