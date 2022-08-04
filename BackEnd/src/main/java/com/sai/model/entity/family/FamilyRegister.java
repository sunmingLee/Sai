package com.sai.model.entity.family;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sai.model.entity.user.User;

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
	@Column(name = "family_register_id")
	private Long familyRegisterId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "family_id")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Family family;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private User user;

	@Column(name = "approve_yn", columnDefinition = "TINYINT(1)")
	private Boolean approveYn;


	public void updateResponse(Boolean approveYn) {
		this.approveYn = approveYn;
	}

}

