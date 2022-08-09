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

}
