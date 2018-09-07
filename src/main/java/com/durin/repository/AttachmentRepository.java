package com.durin.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.durin.domain.Attachment;
import com.durin.domain.User;

public interface AttachmentRepository extends JpaRepository<Attachment, Long>{

	Attachment findByWriter(User user);


}
