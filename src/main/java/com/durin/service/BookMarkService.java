package com.durin.service;

import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;

import org.springframework.stereotype.Service;

import com.durin.domain.BookMark;
import com.durin.domain.User;
import com.durin.dto.BookMarkDto;
import com.durin.repository.BookMarkRepository;

@Service
public class BookMarkService {

	@Resource(name = "bookMarkRepository")
	private BookMarkRepository bookMarkRepository;

	public BookMarkDto add(User loginUser, BookMarkDto bookMarkDto) {
		return bookMarkRepository.save(bookMarkDto.toBookMark(loginUser)).toBookMarkDto();
	}

	public BookMarkDto update(User loginUser, Long id, BookMarkDto bookMarkDto)
			throws AuthenticationException {
		BookMark baseBookMark = bookMarkRepository.findById(id).orElseThrow(NullPointerException::new);
		baseBookMark.update(bookMarkDto.toBookMark(loginUser));
		return bookMarkRepository.save(baseBookMark).toBookMarkDto();
	}

	public void delete(User loginUser, Long id) throws AuthenticationException {
		BookMark bookMark = bookMarkRepository.findById(id).orElseThrow(NullPointerException::new);
		bookMark.isOwner(loginUser);
		bookMarkRepository.delete(bookMark);
	}

	public int allLinkCount(User loginUser) {
		return bookMarkRepository.findByWriter(loginUser).size();
	}

}
