package com.durin.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;


public class PaginationTest {

	private Pagination pagination;
	

	@Test
	public void makeLeftButton() {
		pagination = Pagination.of(0, 1L);
		assertThat(pagination.leftButton(), is(""));
		pagination = Pagination.of(4, 1L);
		assertThat(pagination.leftButton(), is("<li><a href=/api/memos/"+1L+"/"+3+"><</a></li>"));
	}

	@Test
	public void makeRightButton() {
		pagination = Pagination.of(11, 1L);
		assertThat(pagination.rightButton(10), is(""));
		pagination = Pagination.of(0, 1L);
		assertThat(pagination.rightButton(10), is("<li><a href=/api/memos/"+1L+"/"+(1)+">></a></li>"));
		pagination = Pagination.of(5, 1L);
		assertThat(pagination.rightButton(10), is("<li><a href=/api/memos/"+1L+"/"+(6)+">></a></li>"));
		pagination = Pagination.of(9, 1L);
		assertThat(pagination.rightButton(10), is("<li><a href=/api/memos/"+1L+"/"+(10)+">></a></li>"));
	}

	@Test
	public void makePagination() {
		pagination = Pagination.of(0, 1L);
		String pagination2 = "<li><a href=/api/memos/"+1L+"/"+1+">"+1+"</a></li>"+"<li><a href=/api/memos/"+1L+"/"+2+">"+2+"</a></li>"+"<li><a href=/api/memos/"+1L+"/"+3+">"+3+"</a></li>"+"<li><a href=/api/memos/"+1L+"/"+4+">"+4+"</a></li>"+"<li><a href=/api/memos/"+1L+"/"+5+">"+5+"</a></li>";
		assertThat(pagination.pagination(6), is(pagination2));
	}

	
}
