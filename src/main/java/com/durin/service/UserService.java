package com.durin.service;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;

import org.springframework.stereotype.Service;

import com.durin.domain.User;
import com.durin.domain.UserRepository;
import com.durin.dto.UserDto;

@Service
public class UserService {

	@Resource(name="userRepository")
	private UserRepository userRepository;
	
    public User login(String userId, String password) throws AuthenticationException{
    	User user = userRepository.findByUserId(userId).orElseThrow(() -> new NullPointerException("존재하지 않는 아이디입니다."));
    	user.matchPassword(password);
        return user;
    }
    
	public User add(UserDto userDto) throws Exception {
		User user = userDto.toUser();
		if(userRepository.findByUserId(user.getUserId()).isPresent()) {
			throw new Exception("이미 존재하는 아이디 입니다.");
		}
		return userRepository.save(user);
	}


	public int memoSize(User loginUser) {
		User user = userRepository.findById(loginUser.getId()).get();
		return user.getMemoCount();
	}
	
	public User findByUser(User loginUser) {
		return userRepository.findById(loginUser.getId()).orElseThrow(NullPointerException::new);
	}
	
}
