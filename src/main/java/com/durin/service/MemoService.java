package com.durin.service;


import java.util.List;

import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.durin.domain.Label;
import com.durin.domain.Memo;
import com.durin.domain.Pagination;
import com.durin.domain.User;
import com.durin.dto.MemoDto;
import com.durin.dto.SearchDto;
import com.durin.repository.LabelRepository;
import com.durin.repository.MemoRepository;

@Service
public class MemoService {

	@Resource(name="memoRepository")
	private MemoRepository memoRepository;
	
	@Resource(name="labelRepository")
	private LabelRepository labelRepository;
	
	public Memo add(User loginUser , Long labelId, MemoDto memoDto) {
		Label label = findLabelById(labelId);
		return	memoRepository.save(memoDto.toMemo(loginUser, label));
	}

	public Memo update(User loginUser, Long id, MemoDto memoDto) throws AuthenticationException {
		Memo baseMemo = findMemoById(id);
		baseMemo.update(loginUser, memoDto);
		return memoRepository.save(baseMemo);
	}

	public void delete(User loginUser, Long id) throws AuthenticationException {
		Memo memo = findMemoById(id);
		memo.isOwner(loginUser);
		memoRepository.delete(memo);
	}
	
	public Memo findMemoById(Long memoId) {
		return memoRepository.findById(memoId).orElseThrow(NullPointerException::new);
	}
	
	public Label findLabelById(Long labelId) {
		return labelRepository.findById(labelId).orElseThrow(NullPointerException::new);
	}

	public int allMemoCount(User loginUser) {
		return 	memoRepository.findByWriter(loginUser).size();
	}

	public Page<Memo> findAll(Pagination pagination, User loginUser) {
		return 	memoRepository.findByLabelAndWriter(findLabelById(pagination.getLabelId()), loginUser, pagination.makePageReqeest());
	}

	public List<Memo> findAllByTitle(Label label, String title) {
		return 	memoRepository.findByLabelAndTitleLike(label,"%" + title + "%");
	}
	public List<Memo> findAllByContent(Label label, String content) {
		return 	memoRepository.findByLabelAndContentLike(label,"%" + content + "%");
	}
	public List<Memo> findAllByAll(Label label, String content) {
		return 	memoRepository.findByLabelAndContentLikeOrTitleLike(label,"%" + content + "%","%" + content + "%");
	}

	public List<Memo> findAllBySearch(SearchDto searchDto) {
		Label label = findLabelById(searchDto.getLabelId());
		String value = searchDto.getValue();
		if(searchDto.isTitleSearch()) {
			return findAllByTitle (label, value);
		}
		if(searchDto.isContentSearch()) {
		return findAllByContent(label, value);
		}
		return findAllByAll(label, value);

	}

}
