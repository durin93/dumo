package com.durin.web;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.durin.domain.User;
import com.durin.dto.FriendRequestDto;
import com.durin.dto.RelationDto;
import com.durin.security.LoginUser;
import com.durin.service.FriendRequestService;

@RestController
@RequestMapping("/api/relations")
public class ApiRelationController {

	@Resource(name = "friendRequestService")
	private FriendRequestService friendRequestService;
	
	@PostMapping("request")
	public ResponseEntity<FriendRequestDto> sendFreindRequest(@LoginUser User loginUser, String receiverId) {
		return new ResponseEntity<FriendRequestDto>(friendRequestService.sendFreindRequest(loginUser, receiverId),
				HttpStatus.CREATED);
	}

	
	
	@PostMapping("accept")
	public ResponseEntity<RelationDto> acceptFriendRequest(@LoginUser User loginUser,  String requestId, String senderId) {
		RelationDto relationDto = friendRequestService.acceptFriendRequest(loginUser, requestId,senderId);
		return new ResponseEntity<RelationDto>(relationDto,HttpStatus.OK);
	}
	
	@DeleteMapping("cancel/{id}")
	public ResponseEntity<Void> cancleFriendRequest(@LoginUser User loginUser, @PathVariable Long id) {
		friendRequestService.cancelFriendRequest(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
}
