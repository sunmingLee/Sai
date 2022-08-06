package com.sai.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sai.jwt.JwtTokenProvider;
import com.sai.model.dto.user.InfoUserResponseDto;
import com.sai.model.dto.user.LoginUserRequestDto;
import com.sai.model.dto.user.LoginUserResponseDto;
import com.sai.model.dto.user.UserDto;
import com.sai.model.service.user.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserServiceImpl userService;
	private final OAuth2UserService oAuth2UserService;
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
		return ResponseEntity.ok(userService.checkUserEmailDuplicate(email));
	}

	// 직접 회원가입
	@PostMapping("/join")
	public ResponseEntity<String> join(@RequestBody UserDto userInfo) {
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
	public ResponseEntity<LoginUserResponseDto> login(@RequestBody LoginUserRequestDto user, HttpServletResponse response) throws Exception {
		
		LoginUserResponseDto loginUserResponseDto = new LoginUserResponseDto();
		try {
			String JWT = userService.login(user);
			response.setHeader("X-AUTH-TOKEN", JWT);
			Cookie cookie = new Cookie("X-AUTH-TOKEN", JWT);
			cookie.setPath("/");
			cookie.setHttpOnly(true);
			cookie.setSecure(true);
			response.addCookie(cookie);
			loginUserResponseDto.setMsg("로그인 성공");
			return ResponseEntity.status(200).body(loginUserResponseDto);
		} catch (Exception e) {
			loginUserResponseDto.setMsg("로그인 에러");
			return ResponseEntity.status(400).body(loginUserResponseDto);
		}
	}

	// 소셜 로그인 요청
	@PostMapping("login/ouath2/naver")
	public ResponseEntity<OAuth2User> naverAuthRequest(@RequestBody OAuth2UserRequest userRequest) {
		LoginUserResponseDto loginUserResponseDto = new LoginUserResponseDto();
		
		try {
			return ResponseEntity.ok(oAuth2UserService.loadUser(userRequest)); // body에 appToken 반환(response code 200)
			
		} catch (Exception e) {
			return ResponseEntity.ok(oAuth2UserService.loadUser(userRequest)); 
		}
		
	}
	
	// 로그인 후 회원정보 요청
	@PostMapping("/login/info")
	public ResponseEntity<InfoUserResponseDto> loginUserInfo(@RequestBody LoginUserRequestDto loginUserRequestDto){
		try {
			return ResponseEntity.ok(userService.loginUserInfo(loginUserRequestDto));
		} catch (Exception e) {
			return ResponseEntity.status(400).body(userService.loginUserInfo(loginUserRequestDto));
		}
	};
	
	
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
	@GetMapping("/findId")
	public ResponseEntity<Map<String, Object>> findUserId(@RequestParam UserDto user) throws Exception {

		// 이부분 수정해야 함. findUserId 두 번 호출해서 메일이 두번 보내짐

		return ResponseEntity.ok(userService.findUserId(user));
//		return new ResponseEntity<Map<String, Object>>(userService.findUserId(user),
//				(HttpStatus) userService.findUserId(user).get("status"));
	}

	// 비밀번호 찾기
	@GetMapping("/findPw")
	public ResponseEntity<Map<String, Object>> findUserPw(@RequestParam UserDto user) throws Exception {

		return ResponseEntity.ok(userService.findUserPw(user));
//		return new ResponseEntity<Map<String, Object>>(userService.findUserPw(user),
//				(HttpStatus) userService.findUserPw(user).get("status"));
	}

}
