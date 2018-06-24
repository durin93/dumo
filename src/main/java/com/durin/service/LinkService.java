package com.durin.service;



import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;

import org.springframework.stereotype.Service;

import com.durin.domain.Link;
import com.durin.domain.LinkRepository;
import com.durin.domain.User;

@Service
public class LinkService {

	@Resource(name="linkRepository")
	private LinkRepository linkRepository;
	
	public Link add(User loginUser ,String title, String content, String url) {
		return	linkRepository.save(new Link(loginUser, title, content, url));
	}

	public Link update(User loginUser, Long id, String title, String content, String originalUrl) throws AuthenticationException {
		Link baseLink = linkRepository.findById(id).orElseThrow(NullPointerException::new);
		baseLink.update(loginUser, title, content, originalUrl);
		return linkRepository.save(baseLink);
	}

	public void delete(User loginUser, Long id) throws AuthenticationException {
		Link link = linkRepository.findById(id).orElseThrow(NullPointerException::new);
		link.isOwner(loginUser);
		linkRepository.delete(link);
	}
	
}
