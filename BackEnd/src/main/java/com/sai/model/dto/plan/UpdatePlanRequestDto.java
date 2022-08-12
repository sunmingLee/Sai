package com.sai.model.dto.plan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.sai.model.entity.Family;
import com.sai.model.entity.User;

import lombok.Data;

@Data
public class UpdatePlanRequestDto {
private Long mainPlanId;
	
	private Boolean allDayYn;
	
	private String planType;
	
	private String planTitle;
	
	private LocalDateTime mainPlanStartDatetime;
	
	private LocalDateTime mainPlanEndDatetime;
	
	private int planRoutineDay;

	public String planPlace;
	
}
