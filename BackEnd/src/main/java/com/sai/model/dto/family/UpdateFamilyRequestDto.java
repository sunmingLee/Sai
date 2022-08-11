package com.sai.model.dto.family;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateFamilyRequestDto {

	private UpdateFamilyDto updatefamilyDto;
	private List<UpdateFamilyCallsignDto> updateFamilyCallsignDtos;
	private boolean isCallsignModified;

}
