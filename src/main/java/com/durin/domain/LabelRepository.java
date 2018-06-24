package com.durin.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {

	Optional<Label> findByTitle(String title);

}
