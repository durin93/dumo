package com.durin.domain;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemoRepository extends JpaRepository<Memo, Long>{

	List<Memo> findByLabel(Label label);

	Page<Memo> findByLabel(Label label, Pageable pageable);

	Page<Memo> findByLabelAndTitle(Label label, String title, Pageable pageable);

	Page<Memo> findByLabelAndTitleLike(Label label, String title, Pageable pageable);

	
}
