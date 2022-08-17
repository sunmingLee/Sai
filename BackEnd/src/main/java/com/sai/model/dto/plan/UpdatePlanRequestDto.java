package com.sai.model.dto.plan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sai.model.entity.Family;
import com.sai.model.entity.User;

import lombok.Data;

@Data
public class UpdatePlanRequestDto {
	private Long mainPlanId;
	
	private String familyId;
	
	private String userId;
	
	private Boolean allDayYn;
	
	private String planType;
	
	private String planTitle;
	
	@JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
	private List<LocalDateTime> date;
	
	private Integer planRoutineDay;

	public String planPlace;
	
	private Boolean planNotiYn;
	
}
