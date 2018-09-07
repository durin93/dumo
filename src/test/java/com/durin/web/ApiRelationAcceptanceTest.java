package com.durin.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.durin.domain.User;
import com.durin.domain.friend.FriendRequest;
import com.durin.domain.friend.FriendRequestRepository;
import com.durin.domain.friend.RelationRepository;
import com.durin.dto.FriendRequestDto;
import com.durin.dto.RelationDto;
import com.durin.repository.UserRepository;

import support.test.HttpHeaderBuilder;


public class ApiRelationAcceptanceTest extends AcceptanceTest {

	private static final Logger log = LoggerFactory.getLogger(ApiRelationAcceptanceTest.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FriendRequestRepository friendRepository;
	
	@Autowired
	private RelationRepository relationRepository;
	
	
	
	@Test
	public void request() {
		HttpHeaders headers = HttpHeaderBuilder.jsonAndFormData();

		User receiver = userRepository.findByUserId("lsc209").get();
	
	    MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
	    params.add("receiverId", String.valueOf(receiver.getId()));

		
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params, headers);
		ResponseEntity<FriendRequestDto> response = basicAuthTemplate().postForEntity("/api/relations/request", request, FriendRequestDto.class);

		FriendRequest friendRequest = friendRepository.findByReceiverAndSender(receiver,findByDefaultUser()).get();
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		assertThat(friendRequest.toFriendRequestDto(), is(response.getBody()));
		
		ResponseEntity<FriendRequestDto> response2 = basicAuthTemplate().postForEntity("/api/relations/request", request, FriendRequestDto.class);
		assertThat(response2.getBody().getStatus(), is(false));
	}
	
	@Test
	public void accept() {
		HttpHeaders headers = HttpHeaderBuilder.jsonAndFormData();

		User receiver = userRepository.findByUserId("lsc309").get();
		
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
	    params.add("receiverId", String.valueOf(receiver.getId()));

		
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params, headers);
		basicAuthTemplate().postForEntity("/api/relations/request", request, FriendRequestDto.class);
		
		params.clear();
		params.add("senderId", String.valueOf(findByDefaultUser().getId()));

		

		ResponseEntity<RelationDto> response2 = basicAuthTemplate(receiver).postForEntity("/api/relations/accept", request, RelationDto.class);
		
		assertThat(response2.getBody().getOwnerId(), is("lsc109"));
		
		assertThat(relationRepository.findByOwnerAndFriend(findByDefaultUser(),receiver).isPresent(),is(true));
	}



}
