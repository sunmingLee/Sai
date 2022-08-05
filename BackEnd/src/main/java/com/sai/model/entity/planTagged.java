package com.sai.model.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class planTagged {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="plan_tagged_id")
	private Long planTaggedId;

	@Column(name="plan_id")
	private Long planId;
	
	@Column(name="user_id")
	private String userId;
	
}
