package com.sai.dto;

import lombok.Data;

@Data
public class UserInfoRequestDto {
	private String userId;
	private String email;
	private String password;
	private String userName;
}
