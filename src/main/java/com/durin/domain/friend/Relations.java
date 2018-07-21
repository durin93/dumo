package com.durin.domain.friend;

import java.util.ArrayList;
import java.util.List;

public class Relations {

	private List<Relation> relations = new ArrayList<>();

	public Relations() {
	}

	public Relations(List<Relation> relations) {
		this.relations =relations;
	}

	
	public List<Relation> getRelations() {
		return relations;
	}

	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}
	
	public int getSize() {
		return relations.size();
	}
	
}
