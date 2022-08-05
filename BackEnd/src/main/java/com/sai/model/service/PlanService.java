package com.sai.model.service;

import java.util.List;

import com.sai.model.dto.plan.CreatePlanRequsetDto;
import com.sai.model.dto.plan.PlanRequestDto;
import com.sai.model.dto.plan.PlanResponseDto;
import com.sai.model.dto.plan.UpdatePlanRequestDto;

public interface PlanService {

	public String createPlan(CreatePlanRequsetDto createPlanRequestDto);
	public PlanResponseDto getPlan(long mainPlanId);
	public List<PlanResponseDto> getPlanAll(String familyId);
	public void deletePlan(long mainPlanId);
	public void updatePlan(UpdatePlanRequestDto updatePlanRequestDto);
	
}
