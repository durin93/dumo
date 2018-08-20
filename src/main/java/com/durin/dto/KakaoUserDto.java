package com.durin.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class KakaoUserDto {
	private String oauthId;
	private String profileImg;
	private String nickname;
	
	public KakaoUserDto() {
	}

	public KakaoUserDto(JsonNode userInfo) {
		this.oauthId = userInfo.path("id").asText();
		JsonNode properties = userInfo.path("properties");
		this.profileImg = properties.path("profile_image").asText();
		this.nickname = properties.path("nickname").asText();
	}

	public String getOauthId() {
		return oauthId;
	}

	public void setOauthId(String oauthId) {
		this.oauthId = oauthId;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((oauthId == null) ? 0 : oauthId.hashCode());
		result = prime * result + ((profileImg == null) ? 0 : profileImg.hashCode());
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
		KakaoUserDto other = (KakaoUserDto) obj;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (oauthId == null) {
			if (other.oauthId != null)
				return false;
		} else if (!oauthId.equals(other.oauthId))
			return false;
		if (profileImg == null) {
			if (other.profileImg != null)
				return false;
		} else if (!profileImg.equals(other.profileImg))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "KakaoUserInfoDto [oauthId=" + oauthId + ", profileImg=" + profileImg + ", nickname=" + nickname + "]";
	}
	
	
	
	
}
