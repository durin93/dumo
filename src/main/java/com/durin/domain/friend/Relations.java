package com.durin.domain.friend;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class Relations {

	@OneToMany(mappedBy="owner")
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
