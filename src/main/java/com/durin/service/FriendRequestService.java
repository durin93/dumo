package com.durin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.durin.domain.User;
import com.durin.domain.UserRepository;
import com.durin.domain.friend.FriendRequest;
import com.durin.domain.friend.FriendRequestRepository;
import com.durin.domain.friend.Relation;
import com.durin.domain.friend.RelationRepository;
import com.durin.domain.friend.Relations;
import com.durin.dto.FriendRequestDto;
import com.durin.dto.RelationDto;

@Service
public class FriendRequestService {
	
	@Resource(name = "userRepository")
	private UserRepository userRepository;

	@Resource(name = "friendRequestRepository")
	private FriendRequestRepository friendRequestRepository;
	
	@Resource(name = "relationRepository")
	private RelationRepository relationRepository;
	
	public FriendRequestDto sendFreindRequest(User loginUser, String receiverId) {
		FriendRequest request = new FriendRequest(loginUser, userRepository.findById(Long.parseLong(receiverId)).get());
		friendRequestRepository.save(request);
		return request.toFriendRequestDto();
	}


	public List<FriendRequest> findRequestByUser(User loginUser) {
		return friendRequestRepository.findBySender(loginUser);
	}


	public void cancelFriendRequest(Long id) {
		friendRequestRepository.delete(friendRequestRepository.findById(id).orElseThrow(NullPointerException::new));
	}


	public List<FriendRequest> findRequestByOther(User loginUser) {
		return friendRequestRepository.findByReceiver(loginUser);

	}


	public RelationDto acceptFriendRequest(User loginUser, String requestId, String senederId) {
		Relation relation = new Relation(loginUser,userRepository.findById(Long.parseLong(senederId)).orElseThrow(NullPointerException::new));
	    relationRepository.save(relation);
	    cancelFriendRequest(Long.parseLong(requestId));
		return relation.toRelationDto();
	}


	public Relations findAllRelations(User loginUser) {
		return new Relations(relationRepository.findByOwnerOrFriend(loginUser, loginUser));
	}


	
}
