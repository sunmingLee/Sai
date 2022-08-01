package com.sai.model.dto.feed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardLikeDto {
	private String boardLikeId;
	private String boardId;
	private String userId;
}
