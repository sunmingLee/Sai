package com.sai.model.service;

import java.util.List;

import com.sai.model.dto.plan.CreatePlanRequsetDto;
import com.sai.model.dto.plan.PlanRequestDto;
import com.sai.model.dto.plan.PlanResponseDto;
import com.sai.model.dto.plan.UpdatePlanRequestDto;
import com.sai.model.dto.plan.ViewPlanResponseDto;

public interface PlanService {

	public String createPlan(CreatePlanRequsetDto createPlanRequestDto);
	public ViewPlanResponseDto getPlan(long mainPlanId);
	public List<PlanResponseDto> getPlanAll(String familyId);
	public void deletePlan(long mainPlanId);
	public void updatePlan(UpdatePlanRequestDto updatePlanRequestDto);
	
}
