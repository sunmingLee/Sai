package com.sai.model.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sai.model.entity.User;
import com.sai.model.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	// 회원 탈퇴
	@DeleteMapping("/{userId}")
	public void delete(@PathVariable("userId") String userId) {
		Optional<User> user = userRepository.findById(userId);
		
		user.ifPresent(selectUser -> {
			userRepository.delete(selectUser);
		});
	}
	
	// 로그인

	
	// 소셜 로그인
	
	// 아이디 찾기
	
	
	
	// 비밀번호 찾기
	
	// 로그아웃
	
	// 유저 작성 게시글 조회
	
}
