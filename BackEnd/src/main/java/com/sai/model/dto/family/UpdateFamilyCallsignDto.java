package com.sai.model.dto.family;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateFamilyCallsignDto {
	
	private Long familyCallsignId;
	private String callsign;
	
}
