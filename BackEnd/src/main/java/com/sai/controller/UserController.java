package com.sai.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sai.dto.UserInfoRequestDto;
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

	// 아이디 중복 검사
	@GetMapping("/duplication/id")
	public ResponseEntity<Boolean> checkUserIdDuplicate(@RequestParam String userId) {
		return ResponseEntity.ok(userService.checkUserIdDuplicate(userId));
	}

	// 이메일 중복 검사
	@GetMapping("/duplication/email")
	public ResponseEntity<Boolean> checkUserEmailDuplicate(@RequestParam String email) {
		return ResponseEntity.ok(userService.checkUserIdDuplicate(email));
	}

	// 직접 회원가입
	@PostMapping("/join")
	public ResponseEntity<String> join(UserInfoRequestDto userInfo) {
		return ResponseEntity.status(200).body(userService.insertUser(userInfo));
	}

	// 사용자 정보 조회
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserInfoByUserId(@PathVariable String userId) throws JsonProcessingException {
		User getUser = userService.getUserInfoByUserId(userId);
		return ResponseEntity.status(200).body(getUser);
	}

	// 비밀번호 변경
	@PatchMapping("/profile/{userId}")
	public ResponseEntity<String> changePassword(@PathVariable String userId, @RequestParam String password) {
		return ResponseEntity.status(200).body(userService.changePassword(userId, password));
	}

	// 회원탈퇴
	@DeleteMapping("/{userId}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable("userId") String userId) {
		return null;
	}

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(User user) throws Exception {

		return new ResponseEntity<Map<String, Object>>(userService.login(user),
				(HttpStatus) userService.login(user).get("status"));

	}

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

	// 소셜 로그인
	
	// 소셜 회원가입
}
