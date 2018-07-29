package com.durin.web.oAuth;


import java.io.IOException;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.durin.domain.User;
import com.durin.dto.KakaoOauthDto;
import com.durin.security.HttpSessionUtils;
import com.durin.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/api/kakao")
public class KakaoController {
	
	@Resource(name = "userService")
	private UserService userService;
	
	private final String CLIENT_ID = "dd746ae4467778b3ffeb30138d366024";
	private static final Logger log = LoggerFactory.getLogger(KakaoController.class);
	private RestTemplate restTemplate = new RestTemplate();
	
	@GetMapping("/oauth")
	public String accessCode(String code, Model model, HttpSession session) throws Exception {
		System.out.println("code "+code);
		KakaoOauthDto response = getAccessToken(code);
		System.out.println("KakaoOauthDto "+response);
		
		JsonNode userInfo = getUserInfo(response.getAccess_token());
		
	      String oauthId = userInfo.path("id").asText();
	      JsonNode properties = userInfo.path("properties");
	      String profileImg = 	properties.path("profile_image").asText();
	      String nickname = 	properties.path("nickname").asText();
		
	      model.addAttribute("oauthId", oauthId);
	      model.addAttribute("name", nickname);
	      model.addAttribute("profileImg", profileImg);
	     
	      User user=null;
	      try {
	    	  user = userService.existOauth(oauthId);
		} catch (NullPointerException e) {
			System.out.println("가입안됨");
			return "/users/oAuthForm";
		}
			session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
			return "redirect:/memos";
	}

	
	private KakaoOauthDto getAccessToken(String code){
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML, MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", CLIENT_ID);
		params.add("redirect_uri", "http://localhost:8080/api/kakao/oauth");
		params.add("code", code);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
		KakaoOauthDto response  = restTemplate.postForObject("https://kauth.kakao.com/oauth/token",
				request, KakaoOauthDto.class);
		return response;
	}
	
	private JsonNode getUserInfo(String accessToken) throws IOException{
		log.debug("앗디벅");
		System.out.println("accessToken "+accessToken);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML, MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", "Bearer "+accessToken);
		
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
		System.out.println("request "+request);
		String response  = restTemplate.postForObject("https://kapi.kakao.com/v2/user/me",request,String.class);
		
	    JsonNode userInfo = null;
		ObjectMapper mapper = new ObjectMapper();
		userInfo = mapper.readTree(response);
		System.out.println("유저정보"+response);
		return userInfo;
	}
	
}
