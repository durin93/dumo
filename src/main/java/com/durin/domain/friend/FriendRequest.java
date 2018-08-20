package com.durin.domain.friend;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


import com.durin.domain.AbstractEntity;
import com.durin.domain.User;
import com.durin.dto.FriendRequestDto;

@Entity
public class FriendRequest extends AbstractEntity {

	@ManyToOne
	private User sender;

	@ManyToOne
	private User receiver;

	public FriendRequest() {
	}

	public FriendRequest(User loginUser, User receiver) {
		super(0L);
		this.sender = loginUser;
		this.receiver = receiver;
	}

	public User getSender() {
		return sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public FriendRequestDto toFriendRequestDto() {
		return FriendRequestDto.of(receiver, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((receiver == null) ? 0 : receiver.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FriendRequest other = (FriendRequest) obj;
		if (receiver == null) {
			if (other.receiver != null)
				return false;
		} else if (!receiver.equals(other.receiver))
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FriendRequest [sender=" + sender + ", receiver=" + receiver + "]";
	}

}
