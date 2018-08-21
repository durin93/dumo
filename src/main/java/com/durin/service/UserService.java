package com.durin.service;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.durin.domain.Attachment;
import com.durin.domain.AttachmentRepository;
import com.durin.domain.Label;
import com.durin.domain.LabelRepository;
import com.durin.domain.User;
import com.durin.domain.UserRepository;
import com.durin.domain.friend.FriendRequestRepository;
import com.durin.dto.SearchUserDto;
import com.durin.dto.UserDto;
import com.durin.security.ExistException;

@Service
@Transactional
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	private static final String DEFAULT_LABEL = "전체메모";

	@Resource(name = "userRepository")
	private UserRepository userRepository;

	@Resource(name = "attachmentRepository")
	private AttachmentRepository attachmentRepository;

	@Resource(name = "friendRequestRepository")
	private FriendRequestRepository friendRequestRepository;

	@Resource(name = "passwordEncoder")
	private PasswordEncoder passwordEncoder;
	
	@Value("${file.upload.path}")
	private String uploadPath;

	@Resource(name = "labelRepository")
	private LabelRepository labelRepository;

	public User login(String userId, String password) throws AuthenticationException {
		User user = userRepository.findByUserId(userId).orElseThrow(() -> new NullPointerException("존재하지 않는 아이디입니다."));
		user.matchPassword(password,passwordEncoder);
		return user;
	}

	public User add(UserDto userDto) {
		User user = userDto.toUser(passwordEncoder);
		checkUser(user.getUserId());
		User bUser = userRepository.save(user);
		labelRepository.save(new Label(bUser, DEFAULT_LABEL));
		attachmentRepository.save(Attachment.ofProfile(bUser, "default.png", uploadPath));
		return bUser;
	}

	public User addOauth(UserDto userDto){
		User user = userDto.oauthToUser(passwordEncoder);
		if (checkOauth(user.getOauthId())) {
			return userRepository.findByOauthId(user.getOauthId()).get();
		}
		User bUser = userRepository.save(user);
		labelRepository.save(new Label(bUser, DEFAULT_LABEL));
		attachmentRepository.save(Attachment.ofProfile(bUser, userDto.getProfileImg(), uploadPath));
		return bUser;
	}

	public int memoSize(User loginUser) {
		User user = findByUser(loginUser);
		return user.getMemoCount();
	}

	public User findByUser(User loginUser) {
		return userRepository.findById(loginUser.getId()).orElseThrow(NullPointerException::new);
	}

	public User findByUserId(Long id) {
		return userRepository.findById(id).orElseThrow(NullPointerException::new);

	}

	public User update(UserDto userDto, MultipartFile file)
			throws AuthenticationException, IllegalStateException, IOException {
		User user = userRepository.findByUserId(userDto.getUserId()).orElseThrow(NullPointerException::new);
		user.update(userDto,passwordEncoder);
		profileUpdate(user, file);
		return user;
	}

	public void profileUpdate(User user, MultipartFile file) throws IllegalStateException, IOException {
		if (!file.isEmpty()) {
			Attachment baseAttachment = attachmentRepository.findByWriter(user);
			baseAttachment.update(file.getOriginalFilename());
			file.transferTo(new File(baseAttachment.getPath(), baseAttachment.getSaveFileName()));
		}
	}

	public SearchUserDto searchUser(String userId) {
		User user = userRepository.findByUserId(userId).orElseThrow(NullPointerException::new);
		return user.toSearchUserDto(attachmentRepository.findByWriter(user).getSaveFileName());
	}

	public void checkUser(String userId) {
		if (userRepository.findByUserId(userId).isPresent()) {
			log.debug("userService checkUser 이미 존재하는 아이디");
			throw new ExistException("이미 존재하는 아이디 입니다.");
		}
	}

	public Boolean checkOauth(String oauthId) {
		return userRepository.findByOauthId(oauthId).isPresent();
	}

	public User existOauth(String oauthId) {
		if (!userRepository.findByOauthId(oauthId).isPresent()) {
			throw new NullPointerException("가입되지않은회원입니다.");
		}
		return userRepository.findByOauthId(oauthId).get();
	}

	public UserDto pullUserInfo(User loginUser) {
		return loginUser.toUserDto(makeFilePath(loginUser));
	}

	public String makeFilePath(User loginUser) {
		Attachment attachment = attachmentRepository.findByWriter(loginUser);
		if (loginUser.isKakaoUser()) {
			return attachment.getOriginalFileName();
		}
		return "/upload/" + attachment.getSaveFileName();
	}

}
