package com.sai.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Builder;
import lombok.Data;

@Data
@Table(name="user")
@Entity
@Builder
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String userId;
	
	// 가족 ID
	private String familyId;
	
	// 유저 이름
	private String userName;
	
	// 유저 이메일
	private String email;
	
	// 패스워드
	private String password;
	
	// 유저 닉네임
	private String nickname;
	
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
	
	
	// 비밀번호 암호화
	public User hashPassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
		return this;
	}
	
	// 비밀번호 확인
	public boolean checkPassword(String plainPassword, PasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(plainPassword, this.password);
	}
	
	// 비밀번호 변경
	public void updateUserPassword(String password) {
		this.password = password;
	}
	
	
}
