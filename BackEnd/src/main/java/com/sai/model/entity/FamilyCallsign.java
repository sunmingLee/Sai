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
@Table(name = "family_callsign")
public class FamilyCallsign {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "family_callsign_id")
	private Long familyCallsignId;

	@Column(name = "from_user_id")
	private String fromUserId;

	@Column(name = "to_user_id")
	private String toUserId;

	private String callsign;

}
