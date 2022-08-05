package com.sai.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.sai.model.audit.DateAudit;

import lombok.Data;

@Data
@Entity
@Table(name = "poll_result", uniqueConstraints = { @UniqueConstraint(columnNames = { "poll_id", "user_id" }) })
public class Vote extends DateAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "poll_result_id")
	private Long pollResultId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "poll_id", nullable = false)
	private Poll poll;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "choice_id", nullable = false)
	private Choice choice;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

}
