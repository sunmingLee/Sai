package com.sai.model.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.sai.model.dto.notification.CreateNotificationRequestDto;
import com.sai.model.dto.plan.CreatePlanRequsetDto;
import com.sai.model.dto.plan.PlanResponseDto;
import com.sai.model.dto.plan.UpdatePlanRequestDto;
import com.sai.model.entity.MainPlan;
import com.sai.model.entity.NotiType;
import com.sai.model.repository.FamilyRepository;
import com.sai.model.repository.MainPlanRepository;
import com.sai.model.repository.PollRepository;
import com.sai.model.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
	
	private final ModelMapper modelMapper;
	private final PollRepository pollRepository;
	private final UserRepository userRepository;
	private final FamilyRepository familyRepository;
	private final MainPlanRepository mainPlanRepository;
	private final NotificationService notificationService;

	
	// 일정 만들기
	@Override
	public String createPlan(CreatePlanRequsetDto createPlanRequestDto) {
		// 알림 설정한 경우 알림 보내기 - 언제 보낼 것인가?
		if(createPlanRequestDto.getPlanNotiYn()) {
			CreateNotificationRequestDto cnrd = CreateNotificationRequestDto.builder()
					.notiToUserId(createPlanRequestDto.getUserId())
					.notiFromUserId(createPlanRequestDto.getUserId())
					.notiContent("오늘은 "+createPlanRequestDto.getPlanTitle()+" 입니다.")
					.notiType(NotiType.PLAN)
					.build();
			notificationService.createNoti(cnrd);
		}
		
		MainPlan mainPlan = MainPlan.builder()
				.user(userRepository.findByUserId(createPlanRequestDto.getUserId()).get())
				.family(familyRepository.findById(createPlanRequestDto.getFamilyId()).get())
				.planType(createPlanRequestDto.getPlanType())
				.planTitle(createPlanRequestDto.getPlanTitle())
				.allDayYn(createPlanRequestDto.getAllDayYn())
				.mainPlanStartDatetime(createPlanRequestDto.getDate().get(0))
				.mainPlanEndDatetime(createPlanRequestDto.getDate().get(1))
				.planPlace(createPlanRequestDto.getPlanPlace())
				.build();
//		MainPlan mainPlan = modelMapper.map(createPlanRequestDto, MainPlan.class);
		mainPlanRepository.save(mainPlan);
		
		return "success";
	}

	
	// 일정 상세 조회
	@Override
	public PlanResponseDto getPlan(long mainPlanId) {
//		ViewPlanResponseDto viewPlanResponseDto = new ViewPlanResponseDto();
		
		MainPlan mainPlan = mainPlanRepository.findById(mainPlanId).get();
		return modelMapper.map(mainPlan, PlanResponseDto.class);
	}

	
	// 일정 목록 조회
	@Override
	public List<PlanResponseDto> getPlanAll(String familyId) {
		List<MainPlan> mainPlans = mainPlanRepository.findAllByFamilyFamilyIdOrderByMainPlanStartDatetimeAsc(familyId);
		
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
