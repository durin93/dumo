package com.durin.dto;

import com.durin.domain.Label;
import com.durin.domain.Memo;
import com.durin.domain.User;

public class MemoDto{

	private User writer;

	private String title;

	private String content;

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	


}
