package com.durin.domain;

import java.util.ArrayList;
import java.util.Collections;
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
		return Collections.unmodifiableList(labels);
	}
	
	public int allCount() {
		return labels.size();
	}
	
	public int allMemoCount() {
		int count = 0;
		for (Label label : labels) {
			count += label.getMemos().size();
		}
		return count;
	}
	
	
}
