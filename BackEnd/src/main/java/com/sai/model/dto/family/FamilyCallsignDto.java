package com.sai.model.dto.family;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FamilyCallsignDto {
	
	private Long familyCallsignId;
	private String fromUserId;
	private String toUserId;
	private String callsign;
	
}
