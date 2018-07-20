package com.durin.dto;

import com.durin.domain.User;

public class FriendRequestDto {
	
	
	private String receiverId;
	
	private String receiverName;
	
	private String createDate;

	public FriendRequestDto() {
	}
	
	public FriendRequestDto(String receiverId, String receiverName, String createDate) {
		this.receiverId = receiverId;
		this.receiverName = receiverName;
		this.createDate = createDate ;
	}
	
	
	public static FriendRequestDto of(User receiver, String createDate) {
		return new FriendRequestDto(receiver.getUserId(), receiver.getName(), createDate);
	}
	

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
	
}
