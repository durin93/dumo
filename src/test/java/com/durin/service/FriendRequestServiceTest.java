package com.durin.service;


import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.durin.domain.UserRepository;
import com.durin.domain.friend.FriendRequestRepository;


@RunWith(MockitoJUnitRunner.class)
public class FriendRequestServiceTest {
	
	@Mock
	private UserRepository userRepository;

	@Mock
	private FriendRequestRepository friendRequestRepository;

	@InjectMocks
	private FriendRequestService friendRequestService;
	
	

	
}
