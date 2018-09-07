package com.durin.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.durin.domain.Label;
import com.durin.domain.Memo;
import com.durin.domain.User;


public interface MemoRepository extends JpaRepository<Memo, Long>{

	Page<Memo> findByLabelAndWriter(Label label, User loginUser, Pageable pageable);

	List<Memo> findByLabelAndTitleLike(Label label, String title);

	List<Memo> findByLabelAndContentLike(Label label, String content);

	List<Memo> findByLabelAndContentLikeOrTitleLike(Label label, String content, String title);

	List<Memo> findByWriter(User loginUser);

	
}
