package com.durin.domain.friend;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.durin.domain.User;

public interface RelationRepository extends JpaRepository<Relation, Long>{

	Optional<Relation> findByOwnerAndFriend(User owner, User friend);
	
	List<Relation> findByOwner(User loginUser);


}
