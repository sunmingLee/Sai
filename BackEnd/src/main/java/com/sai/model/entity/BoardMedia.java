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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "board_media")
public class BoardMedia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_media_id", updatable = false, insertable = false)
	private Long boardMediaId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;

	@Column(name = "board_media_path")
	private String boardMediaPath;

	@Column(name = "board_media_name")
	private String boardMediaName;

	@Column(name = "board_media_type")
	private String boardMediaType;

	public void setBoard(Board board) {
		this.board = board;
	}

}
