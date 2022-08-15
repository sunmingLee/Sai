package com.sai.model.dto.plan;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CreatePlanRequsetDto {
	private String familyId;
	private String userId;
	private Boolean allDayYn;
	private String planType;
	private String planTitle;
	
	@JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
//	@DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
	private List<LocalDateTime> date;
//	private LocalDateTime mainPlanStartDatetime;
//	private LocalDateTime mainPlanEndDatetime;
//	private LocalDateTime planNotiDatetime;
	private Integer planRoutineDay;
	private String planPlace;
	private Boolean planNotiYn;
//	private LocalDate routineEndDate;
//	private boolean planTaggedYn;
//	private List<InputPlanTaggedRequestDto> inputPlanTaggedRequestDtos;

}
