package com.sai.model.service.user;

import java.util.HashMap;

import com.sai.model.dto.user.LoginUserRequestDto;
import com.sai.model.dto.user.LoginUserResponseDto;
import com.sai.model.dto.user.UserDto;

public interface UserService {

	// 아이디 중복 체크
	public boolean checkUserIdDuplicate(String userId);
	// 이메일 중복 체크
	public boolean checkUserEmailDuplicate(String email);
	// 직접 회원 가입
	public String insertUser(UserDto userInfo);
	// 사용자 정보 조회
	public UserDto getUserInfoByUserId(String userId);
	// 비밀번호 수정
	public String changePassword(String userId, String password);
	// 회원 탈퇴
	public String deleteUser(String userId);
	// 로그인
	public String login(LoginUserRequestDto user);
	// 아이디 찾기
	public HashMap<String, Object> findUserId(UserDto user);
	// 비밀번호 찾기
	public HashMap<String, Object> findUserPw(UserDto user);
//	public Optional<UserDto> getUser(String userId);

}