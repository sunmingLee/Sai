package com.sai.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.sai.model.dto.board.ModifyBoardRequestDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id", updatable = false, insertable = false)
	private Long boardId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "family_id")
	private Family family;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@CreatedDate
	@Column(name = "board_reg_datetime", updatable = false)
	private LocalDateTime boardRegDatetime;

	@Column(name = "board_content")
	private String boardContent;

	@Column(name = "board_like_cnt")
	private Integer boardLikeCnt;

	@Column(name = "board_reply_cnt")
	private Integer boardReplyCnt;

	@Column(name = "board_date")
	private LocalDate boardDate;

	@Column(name = "board_location")
	private String boardLocation;

	@Column(name = "board_tagged_yn", columnDefinition = "TINYINT(1)")
	private Boolean boardTaggedYn;

	@Column(name = "board_media_yn", columnDefinition = "TINYINT(1)")
	private Boolean boardMediaYn;

	@Column(name = "poll_yn", columnDefinition = "TINYINT(1)")
	private Boolean pollYn;

//	public void setBoardId(Long boardId) {
//		this.boardId = boardId;
//	}

//	public void setFamily(String familyId) {
//		
//	}

	public void upBoardLike() {
		this.boardLikeCnt++;
	}

	public void downBoardLike() {
		this.boardLikeCnt--;
	}

	public void upBoardReply() {
		this.boardReplyCnt++;
	}

	public void downBoardReply() {
		this.boardReplyCnt--;
	}

	public void updateBoard(ModifyBoardRequestDto modifyBoardRequestDto) {
		this.boardContent = modifyBoardRequestDto.getBoardContent();
		this.boardDate = modifyBoardRequestDto.getBoardDate();
		this.boardLocation = modifyBoardRequestDto.getBoardLocation();
		this.boardTaggedYn = modifyBoardRequestDto.getBoardTaggedYn();
		this.boardMediaYn = modifyBoardRequestDto.getBoardMediaYn();
		this.pollYn = modifyBoardRequestDto.getPollYn();
	}

}
