package com.sai.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

	@Id
	@Column(name = "user_id")
	private String userId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "family_id")
	private Family family;

	@Column(name = "user_name")
	private String userName;

	private String nickname;

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

}
