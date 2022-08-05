package com.sai.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.sai.model.dto.plan.CreatePlanRequsetDto;
import com.sai.model.dto.plan.PlanRequestDto;
import com.sai.model.dto.plan.PlanResponseDto;
import com.sai.model.dto.plan.UpdatePlanRequestDto;
import com.sai.model.entity.MainPlan;
import com.sai.model.repository.MainPlanRepository;
import com.sai.model.repository.PollRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
	
	private final ModelMapper modelMapper;
	private final PollRepository pollRepository;
	private final MainPlanRepository mainPlanRepository;

	
	// 일정 만들기
	@Override
	public String createPlan(CreatePlanRequsetDto createPlanRequestDto) {
		MainPlan mainPlan = modelMapper.map(createPlanRequestDto, MainPlan.class);
		mainPlanRepository.save(mainPlan);
		return "success";
	}

	
	// 일정 상세 조회
	@Override
	public PlanResponseDto getPlan(long mainPlanId) {
		MainPlan mainPlan = mainPlanRepository.findById(mainPlanId).get();
		return modelMapper.map(mainPlan, PlanResponseDto.class);
	}

	
	// 일정 목록 조회
	@Override
	public List<PlanResponseDto> getPlanAll(String familyId) {
		List<MainPlan> mainPlans = mainPlanRepository.findAllByFamilyId(familyId);
		
		return mainPlans.stream().map(plan -> modelMapper.map(plan, PlanResponseDto.class)).collect(Collectors.toList());
	}

	
	// 일정 삭제
	@Override
	public void deletePlan(long mainPlanId) {
		mainPlanRepository.deleteById(mainPlanId);
	}

	
	// 일정 수정
	@Override
	public void updatePlan(UpdatePlanRequestDto updatePlanRequestDto) {
		MainPlan mainPlan = mainPlanRepository.findById(updatePlanRequestDto.getMainPlanId()).get();
		mainPlan.update(updatePlanRequestDto);
		mainPlanRepository.save(mainPlan);
	}


}
