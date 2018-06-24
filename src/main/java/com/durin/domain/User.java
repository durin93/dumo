package com.durin.domain;

import java.util.List;

import javax.naming.AuthenticationException;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.durin.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
public class User extends AbstractEntity {
    public static final GuestUser GUEST_USER = new GuestUser();

	private String userId;

	private String password;

	private String name;
	
	@Embedded
	private Labels labels;
	
	@Embedded
	private Memos memos;
	
	@Embedded
	private Links links;

	
	
	public User() {
	}

    public User(long id, String userId, String password, String name) {
        super(id);
        this.userId = userId;
        this.password = password;
        this.name = name;
    }
	public User(String userId, String password,String name) {
		this(0L, userId, password, name);
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}
	

	public List<Memo> getMemos() {
		return memos.getMemos();
	}
	
	public List<Link> getLinks(){
		return links.getLinks();
	}

	public List<Label> getLabels(){
		return labels.getLabels();
	}

	public void matchPassword(String password) throws AuthenticationException{
		if (!this.password.equals(password)) {
			throw new AuthenticationException("비밀번호 틀림");
		}
	}

	public UserDto toUserDto() {
		return new UserDto(this.userId, this.password, this.name);
	}
	
	public int getMemoCount() {
		return memos.AllCount();
	}
	
	public int getLinkCount() {
		return links.AllCount();
	}
	
	public String generateUrl() {
		return String.format("/api/users/%d", getId());
	}
	@JsonIgnore
	public boolean isGuestUser() {
		return false;
	}

	private static class GuestUser extends User {
		@Override
		public boolean isGuestUser() {
			return true;
		}
	}

}
