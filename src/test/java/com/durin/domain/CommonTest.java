package com.durin.domain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CommonTest {
	protected static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public static User testUser() {
		return new User("testId", passwordEncoder.encode("1234"), "testName");
	}
	
}
