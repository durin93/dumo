package com.durin.domain;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class Pagination {
	
	private PageRequest pageRequest;
	
	public Pagination() {
		
	}
	
	public Pagination(PageRequest pageRequest) {
		this.pageRequest = pageRequest;
	}
	
	public static Pagination of(int nowPage, int showMemoRange) {
		return new Pagination(PageRequest.of(nowPage, showMemoRange, Sort.Direction.DESC, "createDate"));
	}
	
	
	
	public static String leftButton(Long labelId, int nowPage) {
		if(1 < nowPage) {
			return "<li><a href=/memo/list/"+labelId+"/"+(nowPage-1)+"><<</a></li>";
		}
		
		return "";
	}
	public static String rightButton(Long labelId, int nowPage, int totalPage) {
		if(nowPage < totalPage) {
			return "<li><a href=/memo/list/"+labelId+"/"+(nowPage+1)+">>></a></li>";
		}
		return "";
	}
	
	public static String makePagination(Long labelId, int nowPage, int totalPage) {
		return leftButton(labelId, nowPage) + rightButton(labelId, nowPage, totalPage);
	}

}
