package com.durin.dto;


import com.durin.domain.Label;
import com.durin.domain.Memo;
import com.durin.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;


public class MemoDto extends ResourceSupport {

	private Long memoId;

	private String title;

	private String content;

	private String modifiedDate;

	private List<Link> linkz = new ArrayList<>();


	public MemoDto() {
	}
	
	public MemoDto(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public MemoDto(Long id,String title, String content, String modifiedDate) {
		this(title,content);
		this.content = content;
		this.modifiedDate = modifiedDate;
		this.memoId = id;
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

	public void add(Link link) {
		this.linkz.add(link);
	}

	public List<Link> getLinkz() {
		return linkz;
	}

	public void setLinkz(List<Link> linkz) {
		this.linkz = linkz;
	}

	public Long getMemoId() {
		return memoId;
	}

	public void setMemoId(Long memoId) {
		this.memoId = memoId;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}



	public Memo toMemo(User writer, Label label) {
		return new Memo(writer, title, content, label);
	}


	@Override
	public String toString() {
		return "MemoDto{" +
				"memoId=" + memoId +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", modifiedDate='" + modifiedDate + '\'' +
				", linkz='" + linkz + '\'' +
				'}';
	}
}
