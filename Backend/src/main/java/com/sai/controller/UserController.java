package com.sai.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.security.SecurityUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
//	private final UserPrincipal userPrincipal;

	@ApiOperation(value = "checkUserIdDuplicate: ????????? ?????? ??????")
	@GetMapping("/duplication/id")
	public ResponseEntity<Boolean> checkUserIdDuplicate(@RequestParam String userId) {
		return ResponseEntity.ok(userService.checkUserIdDuplicate(userId));
	}

	@ApiOperation(value = "checkUserEmailDuplicate: ????????? ?????? ??????")
	@GetMapping("/duplication/email")
	public ResponseEntity<Boolean> checkUserEmailDuplicate(@RequestParam String email) {
		return ResponseEntity.ok(userService.checkUserEmailDuplicate(email));
	}

	@ApiOperation(value = "join: ?????? ????????????")
	@PostMapping("/join")
	public ResponseEntity<String> join(@RequestBody UserDto userInfo) {
		try {
			return ResponseEntity.status(200).body(userService.insertUser(userInfo));
		} catch (Exception e) {
			return ResponseEntity.status(409).body(e.getMessage());
		}
	}
	
	@ApiOperation(value = "addUserInformation: ?????? ?????? ??????")
	@PostMapping("/addInfo")
	public ResponseEntity<?> addUserInformation(@RequestPart UserInfoDTO addInfo, @RequestPart(required = false) MultipartFile file) throws Exception {
		try {
			return ResponseEntity.status(200).body(userService.addUserInfo(addInfo, file));
		} catch (Exception e) {
			return ResponseEntity.status(400).body("????????? ??????????????????.");
		}
		
	}

	@ApiOperation(value = "getUserInfoByUserId: ????????? ?????? ??????")
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserInfoByUserId(@PathVariable String userId) throws JsonProcessingException {
		UserDto getUser = userService.getUserInfoByUserId(userId);
		return ResponseEntity.status(200).body(getUser);
	}

	// ???????????? ?????? ??? ????????? ??????
	@ApiOperation(value = "verifyUser: ???????????? ?????? ??? ????????? ??????")
	@PostMapping("/verify/{userId}")
	public ResponseEntity<?> verifyUser(@PathVariable String userId, @RequestBody String password) {
		try {
			userService.verifyUser(userId, password);
			return ResponseEntity.status(200).body("?????? ??????");
			
		} catch (Exception e) {
			return ResponseEntity.status(400).body("????????? ?????????????????????");
		}
	}
	
	// ???????????? ??????
	@ApiOperation(value = "changePassword: ???????????? ??????")
	@PatchMapping("/profile/{userId}")
	public ResponseEntity<String> changePassword(@PathVariable String userId, @RequestParam String password) {
		return ResponseEntity.status(200).body(userService.changePassword(userId, password));
	}

	// ?????? ??????
	@ApiOperation(value = "deleteUser: ?????? ??????")
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable String userId) {
		return ResponseEntity.ok(userService.deleteUser(userId));
	}

	// ?????????
	@ApiOperation(value = "login: ?????????")
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginUserRequestDto user, HttpServletResponse response) throws Exception {
		
		LoginUserResponseDto loginUserResponseDto = new LoginUserResponseDto();
		try {
			String JWT = userService.login(user);
			return ResponseEntity.status(200).body(JWT);
		} catch (Exception e) {
			loginUserResponseDto.setMsg("????????? ??????");
			return ResponseEntity.status(400).body("????????? ??????????????????.");
		}
	}

	// ?????? ????????? ??????
//	@GetMapping("/oauth2/authorization/naver")
//	public ResponseEntity<OAuth2User> naverAuthRequest(@RequestParam OAuth2UserRequest userRequest) {
//		LoginUserResponseDto loginUserResponseDto = new LoginUserResponseDto();
//		
//		try {
//			return ResponseEntity.ok(oAuth2UserService.loadUser(userRequest)); // body??? appToken ??????(response code 200)
//			
//		} catch (Exception e) {
//			return ResponseEntity.ok(oAuth2UserService.loadUser(userRequest)); 
//		}
//		
//	}
	
	// ????????? ??? ???????????? ??????
	@ApiOperation(value = "loginUserInfo: ????????? ??? ???????????? ??????")
	@PostMapping("/login/info")
	public ResponseEntity<InfoUserResponseDto> loginUserInfo(@RequestBody LoginUserRequestDto loginUserRequestDto){
		try {
			return ResponseEntity.ok(userService.loginUserInfo(loginUserRequestDto));
		} catch (Exception e) {
			return ResponseEntity.status(404).body(userService.loginUserInfo(loginUserRequestDto));
		}
	};
	
	
	// ????????????
	@ApiOperation(value = "logout: ????????????")
	@PostMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null && auth.isAuthenticated()) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		
		
//		Cookie cookie = new Cookie("X-AUTH-TOKEN", null);
//		cookie.setHttpOnly(true);
//		cookie.setSecure(false);
//		cookie.setMaxAge(0);
//		cookie.setPath("/");
//		response.addCookie(cookie);
	}

	// ????????? ??????
	@ApiOperation(value = "findUserId: ????????? ??????")
	@GetMapping("/findId")
	public ResponseEntity<Map<String, Object>> findUserId(UserDto user) throws Exception {

		// ????????? ???????????? ???. findUserId ??? ??? ???????????? ????????? ?????? ?????????

		return ResponseEntity.ok(userService.findUserId(user));
//		return new ResponseEntity<Map<String, Object>>(userService.findUserId(user),
//				(HttpStatus) userService.findUserId(user).get("status"));
	}

	// ???????????? ??????
	@ApiOperation(value = "findUserPw: ???????????? ??????")
	@GetMapping("/findPw")
	public ResponseEntity<Map<String, Object>> findUserPw(UserDto user) throws Exception {

		return ResponseEntity.ok(userService.findUserPw(user));
//		return new ResponseEntity<Map<String, Object>>(userService.findUserPw(user),
//				(HttpStatus) userService.findUserPw(user).get("status"));
	}
	
	// ?????? ?????????(?????? ??????) ??????
	@ApiOperation(value = "readMyAllBoard: ?????? ????????? ??????")
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
