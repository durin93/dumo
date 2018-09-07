package com.durin.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.durin.domain.Link;
import com.durin.domain.User;


public interface LinkRepository extends JpaRepository<Link, Long>{

	List<Link> findByWriter(User loginUser);


	
}
