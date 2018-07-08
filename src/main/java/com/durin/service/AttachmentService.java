package com.durin.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.durin.domain.Attachment;
import com.durin.domain.AttachmentRepository;
import com.durin.domain.User;


@Service
@Transactional
public class AttachmentService {

	@Resource(name = "attachmentRepository")
	private AttachmentRepository attachmentRepository;

	
	@Value("${file.upload.path}")
	private String uploadPath;


	public void addAttachment(User loginUser, MultipartFile file)
			throws IllegalStateException, IOException {
		Attachment baseAttachment = attachmentRepository.findByWriter(loginUser).get();
		baseAttachment.update(file.getOriginalFilename());
		file.transferTo(new File(baseAttachment.getPath(), baseAttachment.getSaveFileName()));
	}

	public PathResource downloadAttachment(Long attachmentId) {
		Attachment attachment = attachmentRepository.findById(attachmentId).orElseThrow(NullPointerException::new);
		Path path = Paths.get(attachment.getPath() + attachment.getSaveFileName());
		PathResource resource = new PathResource(path);
		return resource;
	}

	
}
