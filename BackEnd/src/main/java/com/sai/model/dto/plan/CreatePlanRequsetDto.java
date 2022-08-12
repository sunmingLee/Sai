package com.sai.model.dto.plan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class CreatePlanRequsetDto {
	private String familyId;
	private String userId;
	private String planType;
	private LocalDateTime mainPlanStartDatetime;
	private LocalDateTime mainPlanEndDatetime;
	private LocalDateTime planNotiDatetime;
	private int PlanRoutineDay;
	private LocalDate routineEndDate;
	private boolean planTaggedYn;
	private List<InputPlanTaggedRequestDto> inputPlanTaggedRequestDtos;

}
