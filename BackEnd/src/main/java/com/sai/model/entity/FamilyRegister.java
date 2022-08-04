package com.sai.model.entity;

<<<<<<< HEAD
import javax.persistence.CascadeType;
=======
>>>>>>> BE-Feed-Board
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
<<<<<<< HEAD
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
=======
import javax.persistence.OneToOne;
import javax.persistence.Table;

>>>>>>> BE-Feed-Board
import com.fasterxml.jackson.annotation.JsonProperty;

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
@Table(name = "family_register")
public class FamilyRegister {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "family_register_id", updatable = false, insertable = false)
	private Long familyRegisterId;

//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "family_id")
//	@JsonIgnore
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Family family;

//	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
//	@JsonIgnore
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private User user;

	@Column(name = "approve_yn", columnDefinition = "TINYINT(1)")
	private Boolean approveYn;

//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name = "approve_user_id")
//	private User approveUser;

//	public void setFamily(Family family) {
//		this.family = family;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}
//
//	public void setApproveUser(User approveUser) {
//		this.approveUser = approveUser;
//	}

	public void updateResponse(Boolean approveYn) {
		this.approveYn = approveYn;
	}

//	public void updateResponse(User approveUser, Boolean approveYn) {
//		this.approveUser = approveUser;
//		this.approveYn = approveYn;
//	}

//	public void setFamily(Family family) {
//		if (this.family != null) {
//			this.family.getFamilyRegisters().remove(this);
//		}
//		this.family = family;
//
//		if (!family.getUsers().contains(this)) {
//			family.addFamilyRegister(this);
//		}
//
//	}

//	public void setUser(User user) {
//		if (this.user != null) {
//			this.user.getFamilyRegisters().remove(this);
//		}
//		this.user = user;
//
//		if (!user.getFamilyRegisters().contains(this)) {
//			user.addFamilyRegister(this);
//		}
//
//	}

//	@Column(name = "family_id")
//	private String familyId;

//	@Column(name = "user_id")
//	private String userId;

//	@Column(name = "approve_user_id")
//	private String approveUserId;
}
