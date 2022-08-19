package com.sai.model.dto.plan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.sai.model.entity.Family;
import com.sai.model.entity.User;

import lombok.Data;

@Data
public class PlanResponseDto {

	private Long mainPlanId;
	
	private String planType;
	
	private String planTitle;
	
	private LocalDateTime mainPlanStartDatetime;
	
	private LocalDateTime mainPlanEndDatetime;
	
	private LocalDateTime planNotiDatetime;
	
	private Integer planRoutineDay;
	
	private String planPlace;
	
	private Boolean allDayYn; 
	
}
