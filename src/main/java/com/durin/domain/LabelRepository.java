package com.durin.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {

	Optional<Label> findByTitle(String title);

	List<Label> findByWriter(User loginUser);

}
