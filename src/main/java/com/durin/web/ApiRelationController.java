package com.durin.web;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.durin.aspect.LogExecutionTime;
import com.durin.domain.User;
import com.durin.dto.FriendRequestDto;
import com.durin.dto.RelationDto;
import com.durin.security.ExistException;
import com.durin.security.LoginUser;
import com.durin.service.FriendRequestService;


@RestController
@RequestMapping("/api/relations")
public class ApiRelationController {

	@Resource(name = "friendRequestService")
	private FriendRequestService friendRequestService;
	
	
	@LogExecutionTime
	@PostMapping("request")
	public ResponseEntity<FriendRequestDto> sendFriendRequest(@LoginUser User loginUser, String receiverId) {
		FriendRequestDto result;
		try {
			result = friendRequestService.sendFriendRequest(loginUser, receiverId);
		} catch (ExistException e) {
			result = FriendRequestDto.ofFail();
		}
		return new ResponseEntity<FriendRequestDto>(result,	HttpStatus.CREATED);
	}

	@LogExecutionTime
	@PostMapping("accept")
	public ResponseEntity<RelationDto> acceptFriendRequest(@LoginUser User loginUser, String senderId) {
		RelationDto relationDto = friendRequestService.acceptFriendRequest(loginUser, senderId);
		return new ResponseEntity<RelationDto>(relationDto,HttpStatus.OK);
	}
	
	@LogExecutionTime
	@DeleteMapping("cancel/{id}")
	public ResponseEntity<Void> cancleFriendRequest(@LoginUser User loginUser, @PathVariable Long id) {
		friendRequestService.cancelFriendRequest(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
}
