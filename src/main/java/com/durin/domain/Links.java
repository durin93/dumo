package com.durin.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
@Embeddable
public class Links {
	
	@OneToMany(mappedBy="writer")
	@OrderBy("createDate DESC")
	private List<Link> links = new ArrayList<>();

	public List<Link> getLinks() {
		return Collections.unmodifiableList(links);
	}
	
	public int allCount() {
		return links.size();
	}
	
}
