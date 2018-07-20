package com.durin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.durin.domain.User;
import com.durin.domain.UserRepository;
import com.durin.domain.friend.FriendRequest;
import com.durin.domain.friend.FriendRequestRepository;
import com.durin.dto.FriendRequestDto;

@Service
public class FriendRequestService {
	
	@Resource(name = "userRepository")
	private UserRepository userRepository;

	@Resource(name = "friendRequestRepository")
	private FriendRequestRepository friendRequestRepository;

	
	public FriendRequestDto sendFreindRequest(User loginUser, String receiverId) {
		FriendRequest request = new FriendRequest(loginUser, userRepository.findById(Long.parseLong(receiverId)).get());
		friendRequestRepository.save(request);
		return request.toFriendRequestDto();
	}


	public List<FriendRequest> findRequestByUser(User loginUser) {
		return friendRequestRepository.findBySender(loginUser);
	}


	public void cancelFriendRequest(User loginUser, Long id) {
		friendRequestRepository.delete(friendRequestRepository.findById(id).orElseThrow(NullPointerException::new));
	}
}
