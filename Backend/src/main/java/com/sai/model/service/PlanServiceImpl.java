package com.sai.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.sai.model.dto.boardTagged.InputBoardTaggedRequestDto;
import com.sai.model.dto.boardTagged.ViewBoardTaggedResponseDto;
import com.sai.model.dto.plan.CreatePlanRequsetDto;
import com.sai.model.dto.plan.InputPlanTaggedRequestDto;
import com.sai.model.dto.plan.PlanRequestDto;
import com.sai.model.dto.plan.PlanResponseDto;
import com.sai.model.dto.plan.UpdatePlanRequestDto;
import com.sai.model.dto.plan.ViewPlanResponseDto;
import com.sai.model.dto.plan.ViewPlanTaggedResponseDto;
import com.sai.model.entity.BoardTagged;
import com.sai.model.entity.MainPlan;
import com.sai.model.entity.User;
import com.sai.model.entity.PlanTagged;
import com.sai.model.repository.MainPlanRepository;
import com.sai.model.repository.PlanTaggedRepository;
import com.sai.model.repository.PollRepository;
import com.sai.model.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
	
	private final ModelMapper modelMapper;
	private final PollRepository pollRepository;
	private final UserRepository userRepository;
	private final MainPlanRepository mainPlanRepository;
	private final PlanTaggedRepository planTaggedRepository; 

	
	// 일정 만들기
	@Override
	public String createPlan(CreatePlanRequsetDto createPlanRequestDto) {
		
		MainPlan mainPlan = modelMapper.map(createPlanRequestDto, MainPlan.class);
		mainPlanRepository.save(mainPlan);
		
//		// 태그한 사람이 있는 경우
//		if(mainPlan.getPlanTaggedYn()) {
//			List<InputPlanTaggedRequestDto> inputPlanTaggedRequestDtos = 
//					createPlanRequestDto.getInputPlanTaggedRequestDtos();
//			for (InputPlanTaggedRequestDto inputPlanTaggedRequestDto : inputPlanTaggedRequestDtos) {
//				User user = userRepository.findById(inputPlanTaggedRequestDto.getUserId()).get();
//				PlanTagged planTagged = PlanTagged.builder().mainPlan(mainPlan).user(user).build();
//				
//				planTaggedRepository.save(planTagged);
//			}
//			
//		}
		
		return "success";
	}

	
	// 일정 상세 조회
	@Override
	public PlanResponseDto getPlan(long mainPlanId) {
//		ViewPlanResponseDto viewPlanResponseDto = new ViewPlanResponseDto();
		
		MainPlan mainPlan = mainPlanRepository.findById(mainPlanId).get();

//		// 태그된 사람 세팅
//		if(mainPlan.getPlanTaggedYn()) {
//			List<ViewPlanTaggedResponseDto> viewPlanTaggedResponseDtos = new ArrayList<>();
//			List<PlanTagged> planTaggeds = planTaggedRepository.findByMainPlan(mainPlan);
//			for (PlanTagged planTagged : planTaggeds) {
//				viewPlanTaggedResponseDtos.add(modelMapper.map(planTagged, ViewPlanTaggedResponseDto.class));
//			}
//			viewPlanResponseDto.setViewPlanTaggedResponseDtos(viewPlanTaggedResponseDtos);
//		}
//		
//		viewPlanResponseDto.setPlanResponseDto(modelMapper.map(mainPlan, PlanResponseDto.class));
		
		return modelMapper.map(mainPlan, PlanResponseDto.class);
	}

	
	// 일정 목록 조회
	@Override
	public List<PlanResponseDto> getPlanAll(String familyId) {
		List<MainPlan> mainPlans = mainPlanRepository.findAllByFamilyFamilyId(familyId);
		
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
