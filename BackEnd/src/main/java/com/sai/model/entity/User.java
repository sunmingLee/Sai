
package com.sai.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.sai.model.dto.user.UserInfoDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Table(name = "user")
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
//public class User extends DateAudit {
public class User {

	@Id
	@Column(name = "user_id")
	private String userId;

	// 가족 ID
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "family_id")
	private Family family;

	// 유저 이름
	@Column(name = "user_name")
	private String userName;

	// 유저 이메일
	private String email;

	// 패스워드
	private String password;

	// 유저 생일
	private LocalDate birthday;

	// 음력 사용 여부
	private Boolean lunar;

	// roles(for poll)
//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
//	private final Set<Role> roles = new HashSet<>();

	// role
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private UserRole role;

	// 프로필 사진 이미지 경로
	@Column(name = "user_image_path")
	private String userImagePath;

	// 프로필 사진 이미지 이름
	@Column(name = "user_image_name")
	private String userImageName;

	// 프로필 사진 이미지 속성
	@Column(name = "user_image_type")
	private String userImageType;

	// 프로필 메세지
	@Column(name = "user_message")
	private String userMessage;

	// 프로바이더 타입
//	private String providerType;

	public void setFamily(Family family) {
		if (this.family != null) {
			this.family.getUsers().remove(this);
		}
		this.family = family;

		if (family == null)
			return;
		if (!family.getUsers().contains(this)) {
			family.addUser(this);
		}

	}

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

	// 이름, 프로필 변경
	public User update(String name, String picture) {
		this.userName = name;
		this.userImagePath = picture;

		return this;
	}

	// 역할 확인
	public String getRoleKey() {
		return this.role.getKey();
	}

	// 추가정보 입력
	public User addUserinfo(UserInfoDTO userInfoDTO) {
		if (userInfoDTO.getBirthday() != null) {
			this.birthday = userInfoDTO.getBirthday();
		}
		if (userInfoDTO.getLunar() != null) {
			this.lunar = userInfoDTO.getLunar();
		}
		if (userInfoDTO.getUserMessage() != null) {
			this.userMessage = userInfoDTO.getUserMessage();
		}
		return this;
	}

	// 프로필 사진 넣기
	public void updateUserImage(String originalName, String thumbnailPath, String fileType) {
		this.userImageName = originalName;
		this.userImagePath = thumbnailPath;
		this.userImageType = fileType;
	}

}
