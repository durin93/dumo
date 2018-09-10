package com.durin.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.durin.domain.BookMark;
import com.durin.domain.User;


public interface BookMarkRepository extends JpaRepository<BookMark, Long>{

	List<BookMark> findByWriter(User loginUser);


	
}
