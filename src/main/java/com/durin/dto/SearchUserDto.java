package com.durin.dto;

import javax.validation.constraints.Size;


public class SearchUserDto {

	private Long id;
	@Size(min = 3, max = 20)
	private String userId;

	@Size(min = 3, max = 20)
	private String name;

	private String saveFileName;


	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	public SearchUserDto() {
	}

	public SearchUserDto(String userId, String name, String saveFileName, Long id) {
		this.userId = userId;
		this.name = name;
		this.saveFileName = saveFileName;
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static SearchUserDto noData() {
		return new SearchUserDto("x", "x", "x", 0L);
	}

	public Long getId() {
		return id;
	}

}
