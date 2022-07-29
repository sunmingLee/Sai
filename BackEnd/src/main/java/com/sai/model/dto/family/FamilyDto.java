package com.sai.model.dto.family;

import com.sai.model.entity.Family;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FamilyDto {

	private String familyId;
	private String familyName;
	private String familyImagePath;
	private String familyImageName;
	private String familyImageType;

	// Entity to Dto
	public FamilyDto(Family family) {
		this.familyId = family.getFamilyId();
		this.familyName = family.getFamilyName();
		this.familyImagePath = family.getFamilyImagePath();
		this.familyImageName = family.getFamilyImageName();
		this.familyImageType = family.getFamilyImageType();
	}

	// Dto to Entity
	public Family toEntity() {
		return Family.builder().familyId(familyId).familyName(familyName).familyImagePath(familyImagePath)
				.familyImageName(familyImageName).familyImageType(familyImageType).build();
	}

}
