package com.durin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.durin.domain.Label;
import com.durin.domain.User;

public interface LabelRepository extends JpaRepository<Label, Long> {

	Optional<Label> findByTitle(String title);

	List<Label> findByWriter(User loginUser);

}
