package com.sai.model.entity;

import javax.persistence.Entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;

@Getter
@Entity
public class Board {
	@Id
	@GeneratedValue
	@Column(name = "board_id")
	private Long boardId;
	
	private String boardContent;
	private LocalTime boardRegDatetime;
}
