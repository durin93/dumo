package com.durin.domain.friend;



import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.durin.domain.AbstractEntity;
import com.durin.domain.User;

@Entity
public class Relation extends AbstractEntity{

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_relation_owner"))
	private User owner;
	
	private String freindId;
	
	private String name;

	public Relation() {
	}
	
	public User getOwner() {
		return owner;
	}

	public String getFreindId() {
		return freindId;
	}

	public String getName() {
		return name;
	}
	
	
}
