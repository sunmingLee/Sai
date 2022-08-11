package com.sai.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Memo {

	// 메모 id값, auto increment
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "memo_id")
	private Long memoId;
	
	@ManyToOne
	@JoinColumn(name = "family_id")
	private Family family;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "memo_reg_datetime")
	private LocalDateTime memoRegDateTime;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "memo_content")
	private String memoContent;
	
	@Column(name = "read_list")
	private String readList;
	
	public void updateReadList(String readList) {
		this.readList = readList;
	}
	
	public void updateColor(String color) {
		this.color = color;
	}
}
