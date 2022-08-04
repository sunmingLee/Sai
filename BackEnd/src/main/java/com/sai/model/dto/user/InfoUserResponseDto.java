package com.sai.model.dto.user;

import lombok.Data;

@Data
public class InfoUserResponseDto {

	// user ID
	private String userId;

	// 가족 ID
	private String familyId;
	
	// 가족신청 여부
	private boolean familyReg;


}
