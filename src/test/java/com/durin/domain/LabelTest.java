package com.durin.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.naming.AuthenticationException;

import org.junit.Before;
import org.junit.Test;

public class LabelTest  {

	private Label label;
	private User user = CommonTest.testUser();
	
	@Before
	public void setUp() {
		this.label = new Label(user, "라벨1");
	}
	
	@Test(expected=AuthenticationException.class)
	public void isOwner() throws AuthenticationException {
		label.isOwner(new User("test2", "124124", "테스트2"));
	}

	@Test
	public void update() throws AuthenticationException {
		label.update(user, "라벨2");
		assertThat(label.getTitle(), is("라벨2"));
	}
	

	
}
