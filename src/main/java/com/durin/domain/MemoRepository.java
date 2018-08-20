package com.durin.domain;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemoRepository extends JpaRepository<Memo, Long>{

	List<Memo> findByLabel(Label label);

	Page<Memo> findByLabel(Label label, Pageable pageable);

	Page<Memo> findByLabelAndTitle(Label label, String title, Pageable pageable);
	
	Page<Memo> findByLabelAndWriter(Label findLabelById, User loginUser, Pageable pageable);


	List<Memo> findByLabelAndTitleLike(Label label, String title);

	List<Memo> findByLabelAndContentLike(Label label, String content);

	List<Memo> findByLabelAndContentLikeOrTitleLike(Label label, String content, String title);

	List<Memo> findByWriter(User loginUser);


	
}
