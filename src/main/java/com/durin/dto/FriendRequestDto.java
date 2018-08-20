package com.durin.dto;

import com.durin.domain.User;

public class FriendRequestDto {
	
	
	private String receiverId;
	
	private String receiverName;
	
	private String createDate;
	
	private Boolean status=true;

	public FriendRequestDto() {
	}
	
	
	public FriendRequestDto(Boolean status) {
		this.status = status;
	}
	
	public FriendRequestDto(String receiverId, String receiverName, String createDate) {
		this.receiverId = receiverId;
		this.receiverName = receiverName;
		this.createDate = createDate ;
	}


	public static FriendRequestDto of(User receiver, String createDate) {
		return new FriendRequestDto(receiver.getUserId(), receiver.getName(), createDate);
	}
	
	public static FriendRequestDto ofFail() {
		Boolean status = false;
		return new FriendRequestDto(status);
	}
	

	public Boolean getStatus() {
		return status;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((receiverId == null) ? 0 : receiverId.hashCode());
		result = prime * result + ((receiverName == null) ? 0 : receiverName.hashCode());
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
		FriendRequestDto other = (FriendRequestDto) obj;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (receiverId == null) {
			if (other.receiverId != null)
				return false;
		} else if (!receiverId.equals(other.receiverId))
			return false;
		if (receiverName == null) {
			if (other.receiverName != null)
				return false;
		} else if (!receiverName.equals(other.receiverName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FriendRequestDto [receiverId=" + receiverId + ", receiverName=" + receiverName + ", createDate="
				+ createDate + "]";
	}
	
	
	
}
