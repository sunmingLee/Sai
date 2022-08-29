package com.sai.model.dto.boardLike;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardLikeDto {
	private Long boardLikeId;
	private Long boardId;
	private String userId;
}
