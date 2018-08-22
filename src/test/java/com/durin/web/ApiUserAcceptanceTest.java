package com.durin.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import com.durin.domain.Result;
import com.durin.dto.SearchUserDto;
import com.durin.service.UserService;


public class ApiUserAcceptanceTest extends AcceptanceTest{
	private static final Logger log = LoggerFactory.getLogger(ApiUserAcceptanceTest.class);
	
	@Test
	public void create() {
		MultiValueMap<String, String> queryString = new LinkedMultiValueMap<>();
		queryString.add("userId", "gram");
		queryString.add("password", "1234");
		queryString.add("name", "이그램");
		
		webTestClient.post().uri("/api/users")
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)
		.body(BodyInserters.fromFormData(queryString))
		.exchange()
		.expectStatus().isCreated()
		.expectBody(Result.class).isEqualTo(new Result(true,null,"/memos",null));
		
/*		HttpHeaders headers = HttpHeaderBuilder.jsonAndFormData();
		MultiValueMap<String, String> queryString = new LinkedMultiValueMap<>();
		queryString.add("userId", "gram");
		queryString.add("password", "1234");
		queryString.add("name", "이그램");
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String,String>>(queryString,headers);
		ResponseEntity<Result> response = template.postForEntity("/api/users",
				request, Result.class);
		log.debug("create test : {}", response.getBody());
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		assertThat(response.getBody().isValid(), is(true));
		assertThat(response.getBody().getUrl(), is("/memos"));*/
	}

	@Test
	public void login() {
//		Map<String, Object> params = new HashMap<>();
//		params.put("userId", "lsc109");
//		params.put("password", "1234");
		
		String params = "{\"userId\":\"lsc109\",\"password\":\"1234\"}";
		webTestClient.post().uri("/api/users/login")
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)
		.body(BodyInserters.fromObject(params))
		.exchange()
		.expectStatus().isOk()
		.expectBody(Result.class).isEqualTo(new Result(true,null,"/memos",null));
		
/*		HttpHeaders headers = HttpHeaderBuilder.allJsonData();
		Map<String, Object> params = new HashMap<>();
		params.put("userId", "lsc109");
		params.put("password", "1234");
		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
		ResponseEntity<Result> response = basicAuthTemplate().postForEntity("/api/users/login", request, Result.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody().isValid(), is(true));*/
	}

	@Test
	public void logout() {
		webTestClient.mutate().filter(ExchangeFilterFunctions.basicAuthentication("lsc109","1234")).build()
		.get().uri("/api/users/logout")
		.exchange()
		.expectBody(Result.class).isEqualTo(new Result(true,null,"/memos",null));
		
/*		ResponseEntity<Result> response = basicAuthTemplate().getForEntity("/api/users/logout", Result.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody().isValid(), is(true));*/
	}

	@Test
	public void search() {
		SearchUserDto result =
		webTestClient.mutate().filter(ExchangeFilterFunctions.basicAuthentication("lsc109","1234")).build()
		.get().uri("/api/users/search?userId=lsc109")
		.exchange()
		.expectStatus().isOk()
		.expectBody(SearchUserDto.class)
		.returnResult().getResponseBody();
		assertThat(result.getUserId(), is("lsc109"));
		assertThat(result.getName(), is("일승철"));
		
		
/*		ResponseEntity<SearchUserDto> response = basicAuthTemplate().getForEntity("/api/users/search?userId=lsc109",
				SearchUserDto.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody().getUserId(), is("lsc109"));
		assertThat(response.getBody().getName(), is("일승철"));*/
	}

}