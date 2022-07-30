package com.sai.model.dto.family;

import com.sai.model.entity.FamilyCallsign;
import com.sai.model.entity.FamilyRegister;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FamilyRegisterDto {
	private Long familyRegisterId;
	private FamilyDto family;
	private UserDto user;
	private Boolean approveYn;

//	// Entity to Dto
//	public FamilyRegisterDto(FamilyRegister familyRegister) {
//		this.familyRegisterId = familyRegister.getFamilyRegisterId();
//		this.family = new FamilyDto(familyRegister.getFamily());
//		this.user = new UserDto(familyRegister.getUser());
//		this.approveYn = familyRegister.getApproveYn();
//	}
//
//	// Dto to Entity
//	public FamilyRegister toEntity() {
//		return FamilyRegister.builder().familyRegisterId(familyRegisterId).family(family.toEntity())
//				.user(user.toEntity()).approveYn(approveYn).build();
//	}

}
