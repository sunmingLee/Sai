package com.sai.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

public class SubPlan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="sub_plan_id")
	private long subPlanId;
	
	@JoinColumn(name="main_plan_id")
	private MainPlan mainPlan;
	
	@Column(name = "sub_plan_start_datetime")
	private LocalDateTime subPlanStartDatetime;
	
	@Column(name = "sub_plan_end_datetime")
	private LocalDateTime subPlanEndDatetime;
}
