package com.sai.model.dto.feed;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDto {
	private String boardId;
	private String familyId;
	private String userId;
	private LocalDateTime boardRegDatetime;
	private String boardContent;
	private int boardLikeCnt;
	private int boardReplyCnt;
	private LocalDate boardDate;
	private String boardLocation;
	private Boolean boardPeopleYn;
	private Boolean boardMediaYn;
	private Boolean pollYn;
}
