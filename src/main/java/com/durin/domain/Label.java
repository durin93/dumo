package com.durin.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.security.sasl.AuthenticationException;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Label extends AbstractEntity {

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_label_writer"))
	@JsonIgnore
	private User writer;

	@OneToMany(mappedBy="label")
	@OrderBy("createDate DESC")
	private List<Memo> memos;
	
	private String title;

	public Label() {
	}
	public Label(User loginUser, String title) {
        super(0L);
		this.writer = loginUser;
		this.title=title;
	}

	public String getTitle() {
		return title;
	}

	public User getWriter() {
		return writer;
	}

	public List<Memo> getMemos() {
		return memos;
	}
	
	
	public void isOwner(User loginUser) throws AuthenticationException {
		if (!writer.equals(loginUser)) {
			throw new AuthenticationException("본인만");
		}
	}

	public void update(User loginUser , String title) throws AuthenticationException {
		isOwner(loginUser);
		this.title = title;
	}
	
}
