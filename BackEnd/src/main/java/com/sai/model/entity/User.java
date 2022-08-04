package com.sai.model.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class User {

	@Id
	@Column(name = "user_id")
	private String userId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "family_id")
	private Family family;

	@Column(name = "user_name")
	private String userName;

	// 유저 이메일
	private String email;

	// 패스워드
	private String password;

	// 유저 닉네임
	private String nickname;

	// 유저 생일
	private LocalDate birthday;

	// 음력 사용 여부
	private Boolean lunar;

	// 프로필 사진 이미지 경로
	private String user_image_path;

	// 프로필 사진 이미지 이름
	private String user_image_name;

	// 프로필 사진 이미지 속성
	private String user_image_type;

	// 프로필 메세지
	@Lob
	private String user_message;

//	@Builder.Default
//	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//	@JsonIgnore
//	private List<FamilyRegister> familyRegisters = new ArrayList<>();

//	@Builder.Default
//	@OneToMany(mappedBy = "fromUser", fetch = FetchType.LAZY)
//	@JsonIgnore
//	private List<FamilyCallsign> familyCallsigns = new ArrayList<>();

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

//	public void addFamilyRegister(FamilyRegister familyRegister) {
//		this.familyRegisters.add(familyRegister);
//		if (familyRegister.getUser() != this)
//			familyRegister.setUser(this);
//	}

//	public void addFamilyCallsigns(FamilyCallsign familyCallsign) {
//		this.familyCallsigns.add(familyCallsign);
//		if (familyCallsign.getFromUser() != this)
//			familyCallsign.setFromUser(this);
//
//	}

	public void updateUserPassword(String password) {
		this.password = password;
	}

}
