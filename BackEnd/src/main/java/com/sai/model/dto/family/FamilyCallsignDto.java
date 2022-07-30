package com.sai.model.dto.family;

import com.sai.model.entity.FamilyCallsign;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FamilyCallsignDto {
	private Long familyCallsignId;
	private UserDto fromUser;
//	private String fromUserId;
	private UserDto toUser;
//	private String toUserId;
	private String callsign;

//	// Entity to Dto
//	public FamilyCallsignDto(FamilyCallsign familyCallsign) {
//		this.familyCallsignId = familyCallsign.getFamilyCallsignId();
//		this.fromUser = new UserDto(familyCallsign.getFromUser());
//		this.fromUser = new UserDto(familyCallsign.getToUser());
//		this.callsign = familyCallsign.getCallsign();
//	}
//
//	// Dto to Entity
//	public FamilyCallsign toEntity() {
//		return FamilyCallsign.builder().familyCallsignId(familyCallsignId).fromUser(fromUser.toEntity())
//				.toUser(toUser.toEntity()).callsign(callsign).build();
//	}

}
