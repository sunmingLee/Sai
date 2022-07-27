package com.sai.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {
	
	@Id
	private String user_id;
	
	private String family_code;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String nickname;
	
	private Date birthday;
	
	private boolean lunar;
	
	private String user_image;
	
	private String user_message;
	
}
