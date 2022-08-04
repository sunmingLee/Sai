package com.sai.dto.user;

import lombok.Data;

@Data
public class InfoUserResponseDto {

	// 유저 이름
	private String userName;
	
	// user ID
	private String userId;

	// 가족 ID
	private String familyId;
	
	// 가족신청 여부
	private boolean familyReg;


}
