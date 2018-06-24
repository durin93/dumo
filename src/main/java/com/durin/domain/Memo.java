package com.durin.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.security.sasl.AuthenticationException;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Memo extends AbstractEntity {

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_memo_writer"))
	@JsonIgnore
	private User writer;

	private String title;

	@Lob
	private String content;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_memo_label"))
	private Label label;
	
	private boolean deleted = false;

	public Memo() { 
		
	}
	
	public Memo(User writer, String title, String content, Label defaultLabel) {
		super(0L);
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.label = defaultLabel;
	}


	public User getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getModifiedDate() {
		return getFormattedModifiedDate();
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public String generateUrl() {
		return String.format("/api/memo/%d", getId());
	}
	
	public void isOwner(User loginUser) throws AuthenticationException {
		if (!writer.equals(loginUser)) {
			throw new AuthenticationException("본인만");
		}
	}

	public void update(User loginUser , String title, String content) throws AuthenticationException {
		isOwner(loginUser);
		this.title = title;
		this.content = content;
	}

}
