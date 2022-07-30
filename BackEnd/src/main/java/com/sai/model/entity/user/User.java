package com.sai.model.entity.user;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name="user")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id")
	private String userId;
	
	// 가족 ID
	@Column(name="family_id")
	private String familyId;
	
	// 유저 이름
	@Column(name="user_name")
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
	@Column(name="user_image_path")
	private String userImagePath;

	// 프로필 사진 이미지 이름
	@Column(name="user_image_name")
	private String userImageName;
	
	// 프로필 사진 이미지 속성
	@Column(name="user_image_type")
	private String userImageType;
	
	// 프로필 메세지
	@Column(name="user_message")
	private String userMessage;
	
	// 프로바이더 타입
//	private String providerType;
	
	
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	
}
