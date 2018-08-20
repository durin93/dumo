package com.durin.service;

import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;

import org.springframework.stereotype.Service;

import com.durin.domain.Link;
import com.durin.domain.LinkRepository;
import com.durin.domain.User;
import com.durin.dto.LinkDto;

@Service
public class LinkService {

	@Resource(name = "linkRepository")
	private LinkRepository linkRepository;

	public Link add(User loginUser, LinkDto linkDto) {
		return linkRepository.save(linkDto.toLink(loginUser));
	}

	public Link update(User loginUser, Long id, LinkDto linkDto)
			throws AuthenticationException {
		Link baseLink = linkRepository.findById(id).orElseThrow(NullPointerException::new);
		baseLink.update(linkDto.toLink(loginUser));
		return linkRepository.save(baseLink);
	}

	public void delete(User loginUser, Long id) throws AuthenticationException {
		Link link = linkRepository.findById(id).orElseThrow(NullPointerException::new);
		link.isOwner(loginUser);
		linkRepository.delete(link);
	}

	public int allLinkCount(User loginUser) {
		return linkRepository.findByWriter(loginUser).size();
	}

}
