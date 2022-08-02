package com.sai.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Entity
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reply_id")
	private Long replyId;

	@Column(name = "reply_content")
	private String replyContent;

	@Column(name = "reply_reg_datetime")
	private LocalDateTime replyRegDateTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reply_user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "board_id")
	@JsonIgnore
	private Board board;

	public void update(String content) {
		this.replyContent = content;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
