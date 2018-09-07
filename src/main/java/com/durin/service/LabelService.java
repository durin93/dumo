package com.durin.service;



import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.durin.domain.Label;
import com.durin.domain.User;
import com.durin.exception.UnAuthorizedException;
import com.durin.repository.LabelRepository;

@Service
@Transactional
public class LabelService {

	@Resource(name="labelRepository")
	private LabelRepository labelRepository;

	
	public Label add(User loginUser ,String title) {
		if(labelRepository.findByTitle(title).isPresent()) {
			title += UUID.randomUUID(); 
		}
		return	labelRepository.save(new Label(loginUser, title));
	}

	public Label update(User loginUser, Long id, String title) throws AuthenticationException {
		Label baseLabel = labelRepository.findById(id).orElseThrow(NullPointerException::new);
		baseLabel.update(loginUser, title);
		return labelRepository.save(baseLabel);
	}

	public void delete(User loginUser, Long id) throws AuthenticationException {
		
		if(id==1) {
			throw new UnAuthorizedException("기본메모는 삭제할수없습니다.");
		}
		
		Label label = labelRepository.findById(id).orElseThrow(NullPointerException::new);
		label.isOwner(loginUser);
		labelRepository.delete(label);
	}

	public Label getLabel(Long labelId) {
		if(labelId==null) {
			labelId = 1L;
		}
		return labelRepository.findById(labelId).orElseThrow(NullPointerException::new);
	}

	public List<Label> getLabels(User loginUser) {
		return labelRepository.findByWriter(loginUser);
	}

	
}
