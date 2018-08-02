package com.durin.domain;

import java.util.List;

import javax.naming.AuthenticationException;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import com.durin.domain.friend.Relation;
import com.durin.domain.friend.Relations;
import com.durin.dto.SearchUserDto;
import com.durin.dto.UserDto;
import com.durin.security.Encrpytion;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class User extends AbstractEntity {
    public static final GuestUser GUEST_USER = new GuestUser();

    private String division;
    
    private String oauthId;
    
	private String userId;

	private String password;

	private String name;
	
	@Embedded
	private Labels labels;
	
	@Embedded
	private Links links;
	
	@Embedded
	private Relations relations;
	
	public User() {
	}

    public User(long id, String userId, String password, String name) {
        super(id);
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public User(long id, String userId, String password, String name, String division) {
    	super(id);
    	this.userId = userId;
    	this.password = password;
    	this.name = name;
    	this.division = division;
    }

    public User(long id, String oauthId, String userId, String password, String name, String division) {
    	super(id);
    	this.oauthId = oauthId;
    	this.userId = userId;
    	this.password = password;
    	this.name = name;
    	this.division = division;
    }
    

	public User(String oauthId, String userId, String password,String name, String division) {
		this(0L, oauthId, userId, password, name,division);
	}

	
	
	public String getOauthId() {
		return oauthId;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
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
	
	public List<Link> getLinks(){
		return links.getLinks();
	}

	public List<Label> getLabels(){
		return labels.getLabels();
	}

	public List<Relation> getRelations(){
		return relations.getRelations();
	}

	public void matchPassword(String password) throws AuthenticationException{
		if (!this.password.equals(password)) {
			throw new AuthenticationException("비밀번호가 일치하지 않습니다.");
		}
	}

	public UserDto toUserDto() {
		return new UserDto(this.userId, this.password, this.name);
	}
	
	public UserDto toUserDto(String filePath) {
		return new UserDto(this.userId, this.password, this.name, filePath);
	}

	
	public SearchUserDto toSearchUserDto(String saveFileName) {
		return new SearchUserDto(userId, name, saveFileName, getId());
	}
	
	
	public int getMemoCount() {
		return labels.AllMemoCount();
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
	@JsonIgnore
	public boolean isKakaoUser() {
		return division.equals("kakao");
	}

	private static class GuestUser extends User {
		@Override
		public boolean isGuestUser() {
			return true;
		}
	}

	
	public void update(UserDto user) throws AuthenticationException {
		matchPassword(user.getPassword());
		this.name = user.getName();
		this.password = user.getNewPassword();
	}

	@Override
	public String toString() {
		return "User [division=" + division + ", oauthId=" + oauthId + ", userId=" + userId + ", password=" + password
				+ ", name=" + name + ", labels=" + labels + ", links=" + links + ", relations=" + relations + "]";
	}
	
	
}
