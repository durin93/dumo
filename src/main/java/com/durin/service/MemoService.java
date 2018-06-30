package com.durin.service;



import java.util.List;

import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.durin.domain.Label;
import com.durin.domain.LabelRepository;
import com.durin.domain.Labels;
import com.durin.domain.Memo;
import com.durin.domain.MemoRepository;
import com.durin.domain.Pagination;
import com.durin.domain.User;

@Service
public class MemoService {

	@Resource(name="memoRepository")
	private MemoRepository memoRepository;
	
	@Resource(name="labelRepository")
	private LabelRepository labelRepository;
	
	
	public Memo add(User loginUser ,String title, String content) {
		Label defaultLabel = findLabelById(1L);
		return	memoRepository.save(new Memo(loginUser, title, content, defaultLabel));
	}

	public Memo update(User loginUser, Long id, String title, String content) throws AuthenticationException {
		Memo baseMemo = findMemoById(id);
		baseMemo.update(loginUser, title, content);
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

	public List<Memo> getMemos(Long labelId) {
		return 	memoRepository.findByLabel(findLabelById(labelId));
	}

	public int allMemoCount(User loginUser) {
		return 	memoRepository.findByWriter(loginUser).size();
	}

	public Page<Memo> findAll(Pagination pagination) {
		return 	memoRepository.findByLabel(findLabelById(pagination.getLabelId()), pagination.makePageReqeest());
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

	public List<Memo> findAllBySearch(Long labelId, String search, String value) {
		Label label = findLabelById(labelId);
		
		if(search.equals("title")) {
			return findAllByTitle (label, value);
		}
		if(search.equals("content")) {
		return findAllByContent(label, value);
		}
		return findAllByAll(label, value);

	}

}
