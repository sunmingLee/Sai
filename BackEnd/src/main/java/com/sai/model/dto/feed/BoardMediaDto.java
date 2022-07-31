package com.sai.model.dto.feed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardMediaDto {
	private String boardMediaId;
	private String boardId;
	private String boardMediaPath;
	private String boardMediaName;
	private String boardMediaType;
}
