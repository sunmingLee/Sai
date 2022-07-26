package com.sai.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class UserDto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String user_id;
	
	// 가족 ID
	private String family_id;
	
	// 유저 이름
	private String user_name;
	
	// 유저 이메일
	private String email;
	
	// 패스워드
	private String password;
	
	// 유저 닉네임
	private String nickname;
	
	// 유저 생일
	private Date birthday;
	
	// 음력 사용 여부
	private boolean lunar;
	
	// 프로필 사진 이미지 경로
	private String user_image_path;

	// 프로필 사진 이미지 이름
	private String user_image_name;
	
	// 프로필 사진 이미지 속성
	private String user_image_type;
	
	// 프로필 메세지
	private String user_message;
	
}
