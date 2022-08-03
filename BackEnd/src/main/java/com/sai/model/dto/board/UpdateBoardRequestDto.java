package com.sai.model.dto.board;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateBoardRequestDto {

	private Long boardId;
	private String boardContent;
	private LocalDate boardDate;
	private String boardLocation;
	private Boolean boardTaggedYn;
	private Boolean boardMediaYn;
	private Boolean pollYn;
}
