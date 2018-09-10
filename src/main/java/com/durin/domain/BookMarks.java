package com.durin.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
@Embeddable
public class BookMarks {
	
	@OneToMany(mappedBy="writer")
	@OrderBy("createDate DESC")
	private List<BookMark> bookMarks = new ArrayList<>();

	public List<BookMark> getBookMarks() {
		return Collections.unmodifiableList(bookMarks);
	}
	
	public int allCount() {
		return bookMarks.size();
	}
	
}
