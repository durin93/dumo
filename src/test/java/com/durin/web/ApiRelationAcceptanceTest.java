package com.durin.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.durin.domain.Memo;
import com.durin.domain.MemoRepository;
import com.durin.domain.Pagination;
import com.durin.domain.Result;
import com.durin.domain.User;
import com.durin.domain.UserRepository;
import com.durin.domain.friend.FriendRequest;
import com.durin.domain.friend.FriendRequestRepository;
import com.durin.domain.friend.RelationRepository;
import com.durin.dto.FriendRequestDto;
import com.durin.dto.MemoDto;
import com.durin.dto.MemosDto;
import com.durin.dto.RelationDto;
import com.durin.dto.SearchUserDto;
import com.durin.dto.UserDto;
import com.durin.security.HttpSessionUtils;
import com.durin.security.LoginUser;
import com.durin.service.UserService;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApiRelationAcceptanceTest {

	private static final Logger log = LoggerFactory.getLogger(ApiRelationAcceptanceTest.class);
	private static final String DEFAULT_LOGIN_USER = "lsc109";

	@Autowired
	private TestRestTemplate template;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RelationRepository relationRepository;
	
	@Autowired
	private FriendRequestRepository friendRepository;

	public TestRestTemplate basicAuthTemplate() {
		return basicAuthTemplate(findByUserId(DEFAULT_LOGIN_USER));
	}

	public TestRestTemplate basicAuthTemplate(User loginUser) {
		return template.withBasicAuth(loginUser.getUserId(), loginUser.getPassword());
	}

	private User findByDefaultUser() {
		return userRepository.findByUserId(DEFAULT_LOGIN_USER).get();
	}

	private User findByUserId(String userId) {
		return userRepository.findByUserId(userId).get();
	}
	

	@Test
	public void request() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

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
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	    MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
	    params.add("requestId", "1");
	    params.add("senderId",  "2");
		
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String,Object>>(params,headers);
		ResponseEntity<RelationDto> response = basicAuthTemplate().postForEntity("/api/relations/accept", request, RelationDto.class);
		
		assertThat(response.getBody().getOwnerId(), is("lsc209"));
		
		assertThat(relationRepository.findByOwnerAndFriend(findByDefaultUser(),findByDefaultUser()).isPresent(),is(false));
	}

	
/*	@DeleteMapping("cancel/{id}")
	public ResponseEntity<Void> cancleFriendRequest(@LoginUser User loginUser, @PathVariable Long id) {
		friendRequestService.cancelFriendRequest(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	*/
	



}
