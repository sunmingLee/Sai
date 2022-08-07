package com.sai.model.entity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class PlanTagged {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="plan_tagged_id")
	private Long planTaggedId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="plan_id")
	private MainPlan mainPlan;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
}
