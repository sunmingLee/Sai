package com.sai.model.dto.family;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FamilyRegisterDto {
	
	private Long familyRegisterId;
	private String familyId;
	private String userId;
	private Boolean approveYn;
	
}
