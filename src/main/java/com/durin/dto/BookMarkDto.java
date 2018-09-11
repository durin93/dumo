package com.durin.dto;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.durin.domain.BookMark;
import com.durin.domain.User;

import java.util.ArrayList;
import java.util.List;

public class BookMarkDto extends ResourceSupport {
	private static final int TITLE_START_LENGTH = 7;
	protected static final String THUM_IO_URL = "https://image.thum.io/get/width/270/crop/800/";

	private Long bookMarkId;

	private String title;

	private String url;

//	private String thumnailUrl;

	private String content;

	private String modifiedDate;

	private List<Link> linkz = new ArrayList<>();


	public BookMarkDto() {
	}

	public BookMarkDto(String url, String title, String content) {
		this.url = url;
		this.title = title;
		this.content = content;
	}

	public BookMarkDto(Long bookMarkId, String url, String title, String content, String modifiedDate) {
		this(url, title, content);
		this. modifiedDate = modifiedDate;
		this.bookMarkId = bookMarkId;
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

	public Long getBookMarkId() {
		return bookMarkId;
	}

	public void setBookMarkId(Long bookMarkId) {
		this.bookMarkId = bookMarkId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumnailUrl() {
		return THUM_IO_URL+url;
	}

//	public void setThumnailUrl(String thumnailUrl) {
//		this.thumnailUrl = thumnailUrl;
//	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<Link> getLinkz() {
		return linkz;
	}

	public void add(Link link) {
		this.linkz.add(link);
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

	@Override
	public String toString() {
		return "BookMarkDto{" +
				"bookMarkId=" + bookMarkId +
				", title='" + title + '\'' +
				", url='" + url + '\'' +
				", content='" + content + '\'' +
				", modifiedDate='" + modifiedDate + '\'' +
				", linkz=" + linkz +
				'}';
	}
}
