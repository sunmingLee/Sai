package com.sai.model.dto.user;

import lombok.Data;

@Data
public class UserDto {

	// user ID
	private String userId;

	// 가족 ID
	private String familyId;

	// 유저 이름
	private String userName;

	// 유저 이메일
	private String email;

	// 패스워드
	private String password;
	
	// 유저 역할
	private String role;

	// 유저 생일
	private String birthday;

	// 음력 사용 여부
	private Boolean lunar;

	// 프로필 사진 이미지 경로
	private String userImagePath;

	// 프로필 사진 이미지 이름
	private String userImageName;

	// 프로필 사진 이미지 속성
	private String userImageType;

	// 프로필 메세지
	private String userMessage;

}
