package com.sai.model.entity;

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
@Table(name = "vote", uniqueConstraints = { @UniqueConstraint(columnNames = { "poll_id", "user_id" }) })
public class Vote extends DateAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long voteId;

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
