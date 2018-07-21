package com.durin.domain.friend;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.durin.domain.User;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long>{

	List<FriendRequest> findBySender(User loginUser);

	List<FriendRequest> findByReceiver(User loginUser);

}
