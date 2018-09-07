package com.durin.web;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.durin.domain.User;
import com.durin.repository.UserRepository;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {
	private static final String DEFAULT_LOGIN_USER = "lsc109";

	
	@Autowired
	protected WebTestClient webTestClient;
	
	@Autowired
	protected TestRestTemplate template;

	@Autowired
	private UserRepository userRepository;

	public TestRestTemplate basicAuthTemplate() {
		return basicAuthTemplate(findByUserId(DEFAULT_LOGIN_USER));
	}

	public TestRestTemplate basicAuthTemplate(User loginUser) {
		return template.withBasicAuth(loginUser.getUserId(), "1234");
	}

	protected User findByUserId(String userId) {
		return userRepository.findByUserId(userId).get();
	}
	
	protected  User findByDefaultUser() {
		return userRepository.findByUserId(DEFAULT_LOGIN_USER).get();
	}
}