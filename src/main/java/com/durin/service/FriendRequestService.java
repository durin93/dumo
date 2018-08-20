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
import com.durin.security.ExistException;

@Service
public class FriendRequestService {

	@Resource(name = "userRepository")
	private UserRepository userRepository;

	@Resource(name = "friendRequestRepository")
	private FriendRequestRepository friendRequestRepository;

	@Resource(name = "relationRepository")
	private RelationRepository relationRepository;

	public User findUserById(String userId) {
		return userRepository.findById(Long.parseLong(userId)).orElseThrow(NullPointerException::new);
	}
	public User findUserByUserId(String userId) {
		return userRepository.findByUserId(userId).orElseThrow(NullPointerException::new);
	}

	public FriendRequestDto sendFriendRequest(User loginUser, String receiverId) {
		if (friendRequestRepository.findByReceiverAndSender(findUserById(receiverId), loginUser).isPresent()) {
			throw new ExistException("이미 보낸 요청입니다.");
		}
		if (relationRepository.findByOwnerAndFriend(loginUser, findUserById(receiverId)).isPresent()) {
			throw new ExistException("이미 친구입니다.");
		}

		FriendRequest request = new FriendRequest(loginUser, findUserById(receiverId));
		friendRequestRepository.save(request);
		return request.toFriendRequestDto();
	}

	public List<FriendRequest> findRequestByUser(User loginUser) {
		return friendRequestRepository.findAllBySender(loginUser);
	}

	public void cancelFriendRequest(User loginUser) {
		friendRequestRepository.delete(friendRequestRepository.findByReceiver(loginUser).orElseThrow(NullPointerException::new));
	}

	public void cancelFriendRequest(Long id) {
		friendRequestRepository.delete(friendRequestRepository.findById(id).orElseThrow(NullPointerException::new));
	}

	public List<FriendRequest> findRequestByOther(User loginUser) {
		return friendRequestRepository.findAllByReceiver(loginUser);

	}

	public RelationDto acceptFriendRequest(User loginUser, String senderId) {
		Relation relation = new Relation(loginUser, findUserById(senderId));
		relationRepository.save(relation);
		relation = new Relation(findUserById(senderId), loginUser);
		relationRepository.save(relation);
		cancelFriendRequest(loginUser);
		return relation.toRelationDto();
	}

	public Relations findAllRelations(User loginUser) {
		return new Relations(relationRepository.findByOwner(loginUser));
	}

}
