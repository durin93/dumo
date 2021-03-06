package com.durin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.security.sasl.AuthenticationException;

import com.durin.dto.BookMarkDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class BookMark extends AbstractEntity{

	protected static final String THUM_IO_URL = "https://image.thum.io/get/width/270/crop/800/";
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_link_writer"))
	@JsonIgnore
	private User writer;
	
	@Column(nullable=false)
	private String title;
	
	@Lob
	private String content;
	
	@Column(nullable=false)
	private String originalUrl;

	@Column(nullable=false)
	private String thumnailUrl;
	
	
	public BookMark() {
	}

	public BookMark(User writer, String title, String content, String originalUrl) {
		super(0L);
		this.writer = writer;
		this.title = title.trim();
		this.content = content;
		this.originalUrl = originalUrl;
		this.thumnailUrl = THUM_IO_URL+originalUrl;
	}
	

	public String getTitle() {
		return title;
	}

	public User getWriter() {
		return writer;
	}

	public String getContent() {
		return content;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public String getThumnailUrl() {
		return thumnailUrl;
	}
	public String getModifiedDate() {
		return getFormattedModifiedDate();
	}

	public void isOwner(User loginUser) throws AuthenticationException {
		if (!writer.equals(loginUser)) {
			throw new AuthenticationException("본인만");
		}
	}
	public void update(BookMark link) throws AuthenticationException {
		isOwner(link.writer);
		this.title = link.title.trim();
		this.content = link.content;
		this.originalUrl = link.originalUrl;
		this.thumnailUrl = THUM_IO_URL+link.originalUrl;
	}

	public BookMarkDto toBookMarkDto() {
		return new BookMarkDto(getId(), originalUrl, title , content, getModifiedDate());
	}

}
