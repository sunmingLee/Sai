package com.sai.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

}
