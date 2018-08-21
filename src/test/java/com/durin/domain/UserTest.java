package com.durin.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.naming.AuthenticationException;

import org.junit.Before;
import org.junit.Test;

import com.durin.dto.UserDto;

public class UserTest {

	private User user;
	
	@Before
	public void setUp() {
		this.user = CommonTest.testUser();
	}
	
	@Test(expected=AuthenticationException.class)
	public void matchPasswordFail() throws AuthenticationException {
		user.matchPassword("12345", CommonTest.passwordEncoder);
	}

	@Test
	public void matchPasswordSuccess() throws AuthenticationException {
		user.matchPassword("1234", CommonTest.passwordEncoder);
	}

	@Test
	public void update() throws AuthenticationException {
		user.update(new UserDto("testId", "1234", "testName2"), CommonTest.passwordEncoder);
		assertThat(user.getName(),is("testName2"));
	}
	
	
}
