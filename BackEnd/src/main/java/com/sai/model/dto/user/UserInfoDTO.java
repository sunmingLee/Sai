package com.sai.model.dto.user;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserInfoDTO {
	// 유저 아이디
	private String userId;

	// 유저 생일
	private LocalDate birthday;

	// 음력 사용 여부
	private Boolean lunar;


	// 프로필 메세지
	private String userMessage;

}
