package com.durin.domain.friend;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;


@Embeddable
public class Relations{
	
	@OneToMany(mappedBy = "owner")
	@OrderBy("createDate DESC")
	private List<Relation> relations = new ArrayList<>();

	public List<Relation> getRelations() {
		return relations;
	}
	
	public int AllCount() {
		return relations.size();
	}
	
}
