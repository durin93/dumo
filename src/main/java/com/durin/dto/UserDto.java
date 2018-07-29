package com.durin.dto;

import javax.validation.constraints.Size;


import com.durin.domain.User;


public class UserDto {
	
	private String oauthId;
	
    @Size(min = 3, max = 20)
    private String userId;

    @Size(min = 6, max = 20)
    private String password;
    
    private String newPassword;

    @Size(min = 3, max = 20)
    private String name;

    private String profileImg;
    
    
    
    public UserDto() {
    }

    public UserDto(String userId, String password, String name) {
        super();
        this.userId = userId;
        this.password = password;
        this.name = name;
    }
    

	public UserDto(String userId, String password, String name, String filePath) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.profileImg = filePath;
	}
	
	
	
	public String getOauthId() {
		return oauthId;
	}

	public void setOauthId(String oauthId) {
		this.oauthId = oauthId;
	}

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

 
	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public User toUser() {
        return new User(null, this.userId, this.password, this.name, "dumo");
    }

	public User oauthToUser() {
		return new User(this.oauthId, this.userId, this.password, this.name, "kakao");
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((newPassword == null) ? 0 : newPassword.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDto other = (UserDto) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (newPassword == null) {
			if (other.newPassword != null)
				return false;
		} else if (!newPassword.equals(other.newPassword))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserDto [oauthId=" + oauthId + ", userId=" + userId + ", password=" + password + ", newPassword="
				+ newPassword + ", name=" + name + ", profileImg=" + profileImg +  "]";
	}

	public boolean isOauthUser() {
		return oauthId!=null;
	}



	
    
    
}
