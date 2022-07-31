package com.sai.model.dto.feed;

import java.time.LocalDate;

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
	private String boardContent;
	private Boolean boardMediaYn;
	private Boolean pollYn;
	private LocalDate boardDate;
	private String boardLocation;
	private Boolean boardPeopleYn;
}
