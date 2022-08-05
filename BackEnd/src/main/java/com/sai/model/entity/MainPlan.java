package com.sai.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.sai.model.dto.plan.UpdatePlanRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MainPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="main_plan_id")
	private Long mainPlanId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "family_id")
	private Family family;
	
	@OneToMany
	@JoinColumn(name = "user_id")
	private List<User> users;
	
	@Column(name = "plan_type")
	private String planType;
	
	@Column(name = "plan_title")
	private String planTitle;
	
	@Column(name = "main_plan_start_datetime")
	private LocalDateTime mainPlanStartDatetime;
	
	@Column(name = "main_plan_end_datetime")
	private LocalDateTime mainPlanEndDatetime;
	
	@Column(name = "plan_noti_datetime")
	private LocalDateTime planNotiDatetime;
	
	@Column(name = "plan_routine_day")
	private int planRoutineDay;
	
	@Column(name = "routine_end_date")
	private LocalDate routineEndDate;
	
	// 업데이트
	public void update(UpdatePlanRequestDto updatePlanRequestDto) {
		this.planType = updatePlanRequestDto.getPlanType();
		this.planTitle = updatePlanRequestDto.getPlanTitle();
		this.mainPlanStartDatetime = updatePlanRequestDto.getMainPlanStartDatetime();
		this.mainPlanEndDatetime = updatePlanRequestDto.getMainPlanEndDatetime();
		this.planNotiDatetime = updatePlanRequestDto.getPlanNotiDatetime();
		this.planRoutineDay = updatePlanRequestDto.getPlanRoutineDay();
		this.routineEndDate = updatePlanRequestDto.getRoutineEndDate();
		
	}
	
	
}
