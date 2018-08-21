package com.durin.dto;

public class SearchDto {

	private Long labelId;
	private String search;
	private String value;
	
	public SearchDto() {
	}

	public Long getLabelId() {
		return labelId;
	}

	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isTitleSearch() {
		return search.equals("title");
	}
	public boolean isContentSearch() {
		return search.equals("content");
	}
	
}
