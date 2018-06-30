package com.durin.domain;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class Pagination {
	
	private static final Long DEFAULT_LABEL_ID = 1L;
	private static final int DEFAULT_PAGE = 1;
	private static final int SHOW_PAGE_RANGE = 5;
	private static final int SHOW_MEMO_RANGE = 2;
	private Long labelId;
	private int nowPage;
	
	public Pagination() {
		
	}
	
	public Pagination(int nowPage, Long labelId) {
		this.nowPage = nowPage;
		this.labelId = labelId;
	}
	
	public static Pagination of() {
		return new Pagination(DEFAULT_PAGE, DEFAULT_LABEL_ID);
	}

	public static Pagination of(Long labelId) {
		return new Pagination(DEFAULT_PAGE, labelId);
	}

	public static Pagination of(int nowPage, Long labelId) {
		return new Pagination(nowPage, labelId);
	}
	
	public PageRequest makePageReqeest() {
		return PageRequest.of(nowPage-1, SHOW_MEMO_RANGE, Sort.Direction.DESC, "createDate");
	}
	
	
	public  String leftButton() {
		if(1 < nowPage) {
			return "<li><a href=/api/memos/"+labelId+"/"+(nowPage-1)+"><</a></li>";
		}
		return "";
	}
	public  String rightButton(int totalPage) {
		if(nowPage < totalPage) {
			return "<li><a href=/api/memos/"+labelId+"/"+(nowPage+1)+">></a></li>";
		}
		return "";
	}

	public  String paginationLeftButton(int totalPage) {
		if(nowPage > SHOW_PAGE_RANGE ) {
			return "<li><a href=/api/memos/"+labelId+"/"+(nowPage-SHOW_PAGE_RANGE)+"><<</a></li>";
		}
		return "";
	}
	
	public  String paginationRightButton(int totalPage) {
		if(nowPage < totalPage) {
			return "<li><a href=/api/memos/"+labelId+"/"+calcPaginationRight(totalPage)+">>></a></li>";
		}
		return "";
	}
	
	public int calcPaginationRight(int totalPage) {
		int range = (nowPage+SHOW_PAGE_RANGE);
		if(range >= totalPage) {
			return totalPage;
		}
		return range;
	}
	
	
	public String pagination(int totalPage) {
		String pagination ="";
		for(int i = calcStartPage(); i < calcLastPage(totalPage); i++) {
			pagination += "<li><a href=/api/memos/"+labelId+"/"+(i+1)+">"+(i+1)+"</a></li>";
		}
		return pagination;
	}
	
	public int calcLastPage(int totalPage) {
		int last_page = calcStartPage() + SHOW_PAGE_RANGE;
		if (last_page > totalPage) {
			return totalPage;
		}
		return last_page;
	}
	
	public int calcStartPage() {
		if(nowPage <= SHOW_PAGE_RANGE) {
			return 0;
		}
		return (nowPage / SHOW_PAGE_RANGE) * SHOW_PAGE_RANGE;
	}

	
	public  String makePagination(int totalPage) {
		return  paginationLeftButton(totalPage) + pagination(totalPage) + paginationRightButton(totalPage);
//		return  paginationLeftButton(totalPage) + leftButton() + pagination(totalPage) + rightButton(totalPage) + paginationRightButton(totalPage);
	}

	public Long getLabelId() {
		return labelId;
	}

	
	
}
