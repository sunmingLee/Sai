package com.sai.dto.family;

import lombok.Data;

@Data
public class FamilyRegisterDto {
	private Long familyRegisterId;
	private String familyId;
	private String userId;
	private Boolean approveYn;

}
