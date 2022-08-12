package com.sai.model.dto.plan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class CreatePlanRequsetDto {
	private String familyId;
	private String userId;
	private Boolean allDayYn;
	private String planType;
	private String planTitle;
	private LocalDateTime mainPlanStartDatetime;
	private LocalDateTime mainPlanEndDatetime;
//	private LocalDateTime planNotiDatetime;
	private Integer planRoutineDay;
	private String planPlace;
//	private LocalDate routineEndDate;
//	private boolean planTaggedYn;
//	private List<InputPlanTaggedRequestDto> inputPlanTaggedRequestDtos;

}
