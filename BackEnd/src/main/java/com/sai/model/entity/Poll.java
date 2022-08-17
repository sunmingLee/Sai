package com.sai.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "poll")
public class Poll extends UserDateAudit {
//	public class Poll  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "poll_id")
	private Long pollId;

//	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "board_id")
	private Board board;

	@Column(name = "poll_title")
	private String pollTitle; // questions

	@OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	@BatchSize(size = 30)
	private List<Choice> choices = new ArrayList<>();

	@Column(name = "poll_end_datetime")
	private LocalDateTime expirationDateTime;

	public void addChoice(Choice choice) {
		choices.add(choice);
		choice.setPoll(this);
	}

	public void removeChoice(Choice choice) {
		choices.remove(choice);
		choice.setPoll(null);
	}
	
}