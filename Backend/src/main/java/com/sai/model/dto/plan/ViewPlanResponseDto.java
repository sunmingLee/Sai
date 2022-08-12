package com.sai.model.dto.plan;

import java.util.List;

import lombok.Data;

@Data
public class ViewPlanResponseDto {

	private PlanResponseDto planResponseDto;
	
	private List<ViewPlanTaggedResponseDto> viewPlanTaggedResponseDtos;
}
