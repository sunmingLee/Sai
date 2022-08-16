package com.sai.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sai.jwt.JwtTokenProvider;
import com.sai.model.dto.ReadFeedResponseDto;
import com.sai.model.dto.board.UserReadFeedResponseDto;
import com.sai.model.dto.user.AddUserInfoRequest;
import com.sai.model.dto.user.InfoUserResponseDto;
import com.sai.model.dto.user.LoginUserRequestDto;
import com.sai.model.dto.user.LoginUserResponseDto;
import com.sai.model.dto.user.UserDto;
import com.sai.model.dto.user.UserInfoDTO;
import com.sai.model.service.FeedService;
import com.sai.model.service.user.UserServiceImpl;
import com.sai.security.CurrentUser;
import com.sai.security.UserPrincipal;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserServiceImpl userService;
	private final FeedService feedService;
	private final OAuth2UserService oAuth2UserService;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	@ApiOperation(value = "checkUserIdDuplicate: 아이디 중복 검사")
	@GetMapping("/duplication/id")
	public ResponseEntity<Boolean> checkUserIdDuplicate(@RequestParam String userId) {
		return ResponseEntity.ok(userService.checkUserIdDuplicate(userId));
	}

	@ApiOperation(value = "checkUserEmailDuplicate: 이메일 중복 검사")
	@GetMapping("/duplication/email")
	public ResponseEntity<Boolean> checkUserEmailDuplicate(@RequestParam String email) {
		return ResponseEntity.ok(userService.checkUserEmailDuplicate(email));
	}

	@ApiOperation(value = "join: 직접 회원가입")
	@PostMapping("/join")
	public ResponseEntity<String> join(@RequestBody UserDto userInfo) {
		return ResponseEntity.status(200).body(userService.insertUser(userInfo));
	}
	
	@ApiOperation(value = "addUserInformation: 추가 정보 입력")
	@PostMapping("/addInfo")
	public ResponseEntity<?> addUserInformation(@RequestPart UserInfoDTO addInfo, @RequestPart(required = false) MultipartFile file) throws Exception {
		try {
			return ResponseEntity.status(200).body(userService.addUserInfo(addInfo, file));
		} catch (Exception e) {
			return ResponseEntity.status(400).body("오류가 발생했습니다.");
		}
		
	}

	@ApiOperation(value = "getUserInfoByUserId: 사용자 정보 조회")
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserInfoByUserId(@PathVariable String userId) throws JsonProcessingException {
		UserDto getUser = userService.getUserInfoByUserId(userId);
		return ResponseEntity.status(200).body(getUser);
	}

	// 개인정보 수정 전 사용자 확인
	@ApiOperation(value = "verifyUser: 개인정보 수정 전 사용자 확인")
	@PostMapping("/verify/{userId}")
	public ResponseEntity<?> verifyUser(@PathVariable String userId, @RequestBody String password) {
		try {
			System.out.println(password);
			userService.verifyUser(userId, password);
			return ResponseEntity.status(200).body("유저 확인");
			
		} catch (Exception e) {
			return ResponseEntity.status(400).body("잘못된 비밀번호입니다");
		}
	}
	
	// 비밀번호 변경
	@ApiOperation(value = "changePassword: 비밀번호 변경")
	@PatchMapping("/profile/{userId}")
	public ResponseEntity<String> changePassword(@PathVariable String userId, @RequestParam String password) {
		return ResponseEntity.status(200).body(userService.changePassword(userId, password));
	}

	// 회원 탈퇴
	@ApiOperation(value = "deleteUser: 회원 탈퇴")
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable String userId) {
		return ResponseEntity.ok(userService.deleteUser(userId));
	}

	// 로그인
	@ApiOperation(value = "login: 로그인")
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginUserRequestDto user, HttpServletResponse response) throws Exception {
		
		LoginUserResponseDto loginUserResponseDto = new LoginUserResponseDto();
		try {
			String JWT = userService.login(user);
//			response.setHeader("X-AUTH-TOKEN", JWT);
//			Cookie cookie = new Cookie("X-AUTH-TOKEN", JWT);
//			cookie.setPath("/");
//			cookie.setHttpOnly(true);
//			cookie.setSecure(true);
//			response.addCookie(cookie);
//			loginUserResponseDto.setMsg("로그인 성공");
			return ResponseEntity.status(200).body(JWT);
		} catch (Exception e) {
			loginUserResponseDto.setMsg("로그인 에러");
			return ResponseEntity.status(400).body("에러가 발생했습니다.");
		}
	}

	// 소셜 로그인 요청
//	@GetMapping("/oauth2/authorization/naver")
//	public ResponseEntity<OAuth2User> naverAuthRequest(@RequestParam OAuth2UserRequest userRequest) {
//		LoginUserResponseDto loginUserResponseDto = new LoginUserResponseDto();
//		
//		try {
//			return ResponseEntity.ok(oAuth2UserService.loadUser(userRequest)); // body에 appToken 반환(response code 200)
//			
//		} catch (Exception e) {
//			return ResponseEntity.ok(oAuth2UserService.loadUser(userRequest)); 
//		}
//		
//	}
	
	// 로그인 후 회원정보 요청
	@ApiOperation(value = "loginUserInfo: 로그인 후 회원정보 요청")
	@PostMapping("/login/info")
	public ResponseEntity<InfoUserResponseDto> loginUserInfo(@RequestBody LoginUserRequestDto loginUserRequestDto){
		
		try {
			return ResponseEntity.ok(userService.loginUserInfo(loginUserRequestDto));
		} catch (Exception e) {
			return ResponseEntity.status(404).body(userService.loginUserInfo(loginUserRequestDto));
		}
	};
	
	
	// 로그아웃
	@ApiOperation(value = "logout: 로그아웃")
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
	@ApiOperation(value = "findUserId: 아이디 찾기")
	@GetMapping("/findId")
	public ResponseEntity<Map<String, Object>> findUserId(UserDto user) throws Exception {

		// 이부분 수정해야 함. findUserId 두 번 호출해서 메일이 두번 보내짐

		return ResponseEntity.ok(userService.findUserId(user));
//		return new ResponseEntity<Map<String, Object>>(userService.findUserId(user),
//				(HttpStatus) userService.findUserId(user).get("status"));
	}

	// 비밀번호 찾기
	@ApiOperation(value = "findUserPw: 비밀번호 찾기")
	@GetMapping("/findPw")
	public ResponseEntity<Map<String, Object>> findUserPw(UserDto user) throws Exception {

		return ResponseEntity.ok(userService.findUserPw(user));
//		return new ResponseEntity<Map<String, Object>>(userService.findUserPw(user),
//				(HttpStatus) userService.findUserPw(user).get("status"));
	}
	
	// 개인 페이지(개인 피드) 조회
	@ApiOperation(value = "readMyAllBoard: 개인 페이지 조회")
	@GetMapping("/myPage/{userId}")
	public ResponseEntity<?> readMyAllBoard(@PathVariable String userId, @PageableDefault(size = 16, sort = "boardRegDatetime", direction = Direction.DESC) Pageable pageable, @CurrentUser UserPrincipal currUser)
			throws Exception {

		try {
			UserReadFeedResponseDto userReadFeedResponseDto = new UserReadFeedResponseDto();
			
			List<ReadFeedResponseDto> readFeedResponseDtos = feedService.readAllBoard(userId, pageable, currUser);
			Integer userFeedNum = feedService.countAllBoard(userId);
			
			userReadFeedResponseDto.setReadFeedResponseDtos(readFeedResponseDtos);
			userReadFeedResponseDto.setUserBoardNum(userFeedNum);
			
			if (readFeedResponseDtos != null) {
				return new ResponseEntity<UserReadFeedResponseDto>(userReadFeedResponseDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

}
