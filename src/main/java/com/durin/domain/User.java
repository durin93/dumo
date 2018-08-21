package com.durin.domain;

import java.util.List;

import javax.naming.AuthenticationException;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.durin.domain.friend.Relation;
import com.durin.domain.friend.Relations;
import com.durin.dto.SearchUserDto;
import com.durin.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class User extends AbstractEntity {
    public static final GuestUser GUEST_USER = new GuestUser();

	@Column(nullable=false)
    private String division;
    
    private String oauthId;

	@Column(nullable=false)
	private String userId;

	@Column(nullable=false)
	private String password;

	@Column(nullable=false)
	private String name;
	
	@Embedded
	private Labels labels;
	
	@Embedded
	private Links links;
	
	@Embedded
	private Relations relations;
	
	public User() {
	}

    public User(String userId, String password, String name) {
        super(0L);
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public User(String oauthId, String userId, String password, String name, String division) {
    	this(userId,password,name);
    	this.oauthId = oauthId;
    	this.division = division;
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

	public void matchPassword(String password, PasswordEncoder passwordEncoder) throws AuthenticationException{
		if (!passwordEncoder.matches(password, this.password)) {
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
		return labels.allMemoCount();
	}
	
	public int getLinkCount() {
		return links.allCount();
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

	
	public void update(UserDto user, PasswordEncoder passwordEncoder) throws AuthenticationException {
		matchPassword(user.getPassword(),passwordEncoder);
		this.name = user.getName();
		this.password = passwordEncoder.encode(user.getNewPassword());
	}

	@Override
	public String toString() {
		return "User [division=" + division + ", oauthId=" + oauthId + ", userId=" + userId + ", password=" + password
				+ ", name=" + name + ", labels=" + labels + ", links=" + links + ", relations=" + relations + "]";
	}
	
	
}
