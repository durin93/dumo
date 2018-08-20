package com.durin.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.security.sasl.AuthenticationException;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Link extends AbstractEntity{

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_link_writer"))
	@JsonIgnore
	private User writer;
	
	private String title;
	
	@Lob
	private String content;
	
	private String originalUrl;

	private String thumnailUrl;
	
	
	public Link() {
	}

	public Link (User writer, String title, String content, String originalUrl) {
		super(0L);
		this.writer = writer;
		this.title = title.trim();
		this.content = content;
		this.originalUrl = originalUrl;
		this.thumnailUrl = "https://image.thum.io/get/width/270/crop/800/"+originalUrl;
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
	
	public String generateUrl() {
		return String.format("/api/link/%d", getId());
	}
	
	public void isOwner(User loginUser) throws AuthenticationException {
		if (!writer.equals(loginUser)) {
			throw new AuthenticationException("본인만");
		}
	}
	public void update(Link link) throws AuthenticationException {
		isOwner(link.writer);
		this.title = link.title.trim();
		this.content = link.content;
		this.originalUrl = link.originalUrl;
		this.thumnailUrl = "https://image.thum.io/get/width/270/crop/800/"+link.originalUrl;
	}
	
}
