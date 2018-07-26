package com.durin.dto;


import com.durin.domain.User;

public class RelationDto {

	private String ownerId;
	
	private String ownerName;
	
	private String friendId;

	private String friendName;
	
	
	public RelationDto() {
	}
	
	public RelationDto(User owner, User friend) {
		this.ownerId = owner.getUserId();
		this.ownerName = owner.getName();
		this.friendId = friend.getUserId();
		this.friendName = friend.getName();
	}

	
	
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	
	
}
