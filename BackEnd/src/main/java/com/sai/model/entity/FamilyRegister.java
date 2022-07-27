package com.sai.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "family_register")
public class FamilyRegister {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "family_register_id")
	private Long familyRegisterId;

	@Column(name = "family_id")
	private String familyId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "approve_yn")
	private Boolean approveYn;

	@Column(name = "approve_user_id")
	private String approveUserId;
}
