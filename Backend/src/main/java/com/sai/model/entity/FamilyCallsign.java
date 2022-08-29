package com.sai.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name = "family_callsign")
public class FamilyCallsign {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "family_callsign_id", updatable = false, insertable = false)
	private Long familyCallsignId;

//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_user_id")
	private User fromUser;

//	@Column(name = "from_user_id")
//	private String fromUser;

//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_user_id")
	private User toUser;

//	@Column(name = "to_user_id")
//	private String toUser;

	private String callsign;

	public void updateCallsign(String callsign) {
		this.callsign = callsign;
	}

//	public void setFromUser(User fromUser) {
//		if (this.fromUser != null) {
//			this.fromUser.getFamilyCallsigns().remove(this);
//		}
//		this.fromUser = fromUser;
//
//		if (!fromUser.getFamilyCallsigns().contains(this)) {
//			fromUser.addFamilyCallsigns(this);
//		}
//
//	}

}
