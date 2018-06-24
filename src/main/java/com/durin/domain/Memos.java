package com.durin.domain;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
@Embeddable
public class Memos {
	
	@OneToMany(mappedBy="writer")
	@OrderBy("createDate DESC")
	private List<Memo> memos = new ArrayList<>();

	public List<Memo> getMemos() {
		return memos;
	}
	
	public int AllCount() {
		return memos.size();
	}

	
	
}
