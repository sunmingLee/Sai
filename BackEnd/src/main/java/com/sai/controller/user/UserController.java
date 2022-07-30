package com.sai.controller.user;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sai.dto.UserDto;
import com.sai.jwt.JwtTokenProvider;
import com.sai.model.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

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
	public ResponseEntity<String> join(UserDto userInfo) {
		return ResponseEntity.status(200).body(userService.insertUser(userInfo));
	}

	// 사용자 정보 조회
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserInfoByUserId(@PathVariable String userId) throws JsonProcessingException {
		UserDto getUser = userService.getUserInfoByUserId(userId);
		return ResponseEntity.status(200).body(getUser);
	}

	// 비밀번호 변경
	@PatchMapping("/profile/{userId}")
	public ResponseEntity<String> changePassword(@PathVariable String userId, @RequestParam String password) {
		return ResponseEntity.status(200).body(userService.changePassword(userId, password));
	}

	// 회원 탈퇴
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable String userId) {
		return ResponseEntity.ok(userService.deleteUser(userId));
	}
	

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<String> login(UserDto user, HttpServletResponse response) throws Exception {
		
		
		
//		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword());
//		
//		 Authentication authentication = authenticationManagerBuilder.getObject()
//			        .authenticate(authenticationToken);
//		 SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		
//		String token = jwtTokenProvider.createToken(user.getUserId());
//        response.setHeader("X-AUTH-TOKEN", token);
//
//        Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
//        response.addCookie(cookie);
		

		return ResponseEntity.ok(userService.login(user));

	}
	
	// 로그아웃
	@PostMapping("/logout")
	public void logout(HttpServletResponse response) {
	     Cookie cookie = new Cookie("X-AUTH-TOKEN", null);
	        cookie.setHttpOnly(true);
	        cookie.setSecure(false);
	        cookie.setMaxAge(0);
	        cookie.setPath("/");
	        response.addCookie(cookie);
	}

	// 아이디 찾기
	@GetMapping("/user/findId")
	public ResponseEntity<Map<String, Object>> findUserId(UserDto user) throws Exception {
		System.out.println(user);

		return new ResponseEntity<Map<String, Object>>(userService.findUserId(user),
				(HttpStatus) userService.findUserId(user).get("status"));
	}

	// 비밀번호 찾기
	@GetMapping("/user/findPw")
	public ResponseEntity<Map<String, Object>> findUserPw(UserDto user) throws Exception {

		return new ResponseEntity<Map<String, Object>>(userService.findUserPw(user),
				(HttpStatus) userService.findUserPw(user).get("status"));
	}

	// 소셜 로그인

	// 소셜 회원가입

}
