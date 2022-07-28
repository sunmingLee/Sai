package com.sai.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sai.model.entity.User;
import com.sai.model.repository.UserRepository;
import com.sai.model.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	private final UserRepository userRepository;

	// 회원탈퇴
	@DeleteMapping("/{userId}")
	public void delete(@PathVariable("userId") String userId) {

	}

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(User user) throws Exception {

		return new ResponseEntity<Map<String, Object>>(userService.login(user),
				(HttpStatus) userService.login(user).get("status"));

	}

	// 소셜 로그인

	// 아이디 찾기
	@GetMapping("/user/findId")
	public ResponseEntity<Map<String, Object>> findUserId(User user) throws Exception {
		System.out.println(user);

		return new ResponseEntity<Map<String, Object>>(userService.findUserId(user),
				(HttpStatus) userService.findUserId(user).get("status"));
	}

	// 비밀번호 찾기
	@GetMapping("/user/findPw")
	public ResponseEntity<Map<String, Object>> findUserPw(User user) throws Exception {

		return new ResponseEntity<Map<String, Object>>(userService.findUserPw(user),
				(HttpStatus) userService.findUserPw(user).get("status"));
	}

	

}
