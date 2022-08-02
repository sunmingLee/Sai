package com.sai.model.dto.user;

import lombok.Data;

@Data
public class UserInfoDTO {
	// 유저 아이디
	private String userId;

	// 프로필 사진 이미지 경로
	private String userImagePath;

	// 프로필 사진 이미지 이름
	private String userImageName;

	// 프로필 사진 이미지 속성
	private String userImageType;
}
