package com.durin.domain.friend;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.durin.domain.User;

public interface RelationRepository extends JpaRepository<Relation, Long>{

	List<Relation> findByOwnerOrFriend(User owner, User friend);

}
