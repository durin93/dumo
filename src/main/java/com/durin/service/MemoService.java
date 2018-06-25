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
import com.durin.domain.Memo;
import com.durin.domain.MemoRepository;
import com.durin.domain.User;

@Service
public class MemoService {

	@Resource(name="memoRepository")
	private MemoRepository memoRepository;
	
	@Resource(name="labelRepository")
	private LabelRepository labelRepository;
	
	
	public Memo add(User loginUser ,String title, String content) {
		Label defaultLabel = labelRepository.findById(1L).orElseThrow(NullPointerException::new);
		return	memoRepository.save(new Memo(loginUser, title, content, defaultLabel));
	}

	public Memo update(User loginUser, Long id, String title, String content) throws AuthenticationException {
		Memo baseMemo = memoRepository.findById(id).orElseThrow(NullPointerException::new);
		baseMemo.update(loginUser, title, content);
		return memoRepository.save(baseMemo);
	}

	public void delete(User loginUser, Long id) throws AuthenticationException {
		Memo memo = memoRepository.findById(id).orElseThrow(NullPointerException::new);
		memo.isOwner(loginUser);
		memoRepository.delete(memo);
	}
	

	public List<Memo> getMemos(Long labelId) {
		Label label = labelRepository.findById(labelId).orElseThrow(NullPointerException::new);
		return 	memoRepository.findByLabel(label);
	}
	
	/*public Page<Memo> findAll(Long labelId, Pageable pageable) {
		Label label = labelRepository.findById(labelId).orElseThrow(NullPointerException::new);
		return 	memoRepository.findByLabel(label, pageable);
	}*/
	public Page<Memo> findAll(Long labelId, PageRequest pageRequest) {
		Label label = labelRepository.findById(labelId).orElseThrow(NullPointerException::new);
		return 	memoRepository.findByLabel(label, new PageRequest(0, 9));
	}
	
}
