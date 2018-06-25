package com.durin.domain;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
@Embeddable
public class Labels {
	
	@OneToMany(mappedBy="writer")
	@OrderBy("createDate ASC")
	private List<Label> labels = new ArrayList<>();

	public List<Label> getLabels() {
		return labels;
	}
	
	public int AllCount() {
		return labels.size();
	}
	
	public int AllMemoCount() {
		int count = 0;
		for (Label label : labels) {
			count += label.getMemos().size();
		}
		return count;
	}
	
	
}
