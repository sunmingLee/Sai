package com.sai.model.service.user;

import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

import com.sai.model.dto.user.LoginUserRequestDto;
import com.sai.model.dto.user.LoginUserResponseDto;
import com.sai.model.dto.user.UserDto;
import com.sai.model.dto.user.UserInfoDTO;
import com.sai.security.UserPrincipal;

public interface UserService {

	// 아이디 중복 체크
	public boolean checkUserIdDuplicate(String userId);
	// 이메일 중복 체크
	public boolean checkUserEmailDuplicate(String email);
	// 직접 회원 가입
	public String insertUser(UserDto userInfo) throws Exception;
	// 회원 정보 추가
	public String addUserInfo(UserInfoDTO addInfo,  MultipartFile file) throws Exception;
	// 사용자 정보 조회
	public UserDto getUserInfoByUserId(String userId);
	// 사용자 비밀번호 검증
	public void verifyUser(String userId, String password);
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

}
