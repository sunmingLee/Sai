package com.sai.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateDto {
	private String nickname;
	private Date birthday;
	private Boolean lunar;
	private String userImagePath;
	private String userImageName;
	private String userImageType;
	private String userMessage;
	
//	@Builder
//	public UserUpdateDto() {
}
