package com.durin.dto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.durin.domain.BookMark;
import com.durin.domain.User;

public class BookMarkDto {
	private static final int TITLE_START_LENGTH = 7;

	private String title;

	private String url;

	private String content;

	public BookMarkDto() {
	}

	public BookMarkDto(String url, String title, String content) {
		this.url = url;
		this.title = title;
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public String getTitle() {
		return title;
	}

	public void makeUrlTitle() {
		try {
			ResponseEntity<String> res = new RestTemplate().getForEntity(url, String.class);
			String urlBody = res.getBody().toString();
			title = urlBody.substring(urlBody.indexOf("<title>")+TITLE_START_LENGTH, urlBody.indexOf("</title>"));
		} catch (Exception e) {
			title = "unable to connect";
		}
	}

	public BookMark toBookMark(User writer) {
		makeUrlTitle();
		return new BookMark(writer, title, content, url);
	}


}
