package com.durin.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long>{

	Optional<Attachment> findByWriter(User loginUser);


}
