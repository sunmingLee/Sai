package com.sai.model.dto.family;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateFamilyVo {
	private FamilyDto familyDto;
	private List<FamilyCallsignDto> familyCallsignDtos;

//	// Entity to Dto
//	public UpdateFamilyDto(Family family, List<FamilyCallsign> familyCallsigns) {
//		this.familyDto = new FamilyDto(family);
//		this.familyCallsignDtos = new ArrayList<>();
//		for (FamilyCallsign familyCallsign : familyCallsigns) {
//			familyCallsignDtos.add(new FamilyCallsignDto(familyCallsign));
//		}
//	}
}