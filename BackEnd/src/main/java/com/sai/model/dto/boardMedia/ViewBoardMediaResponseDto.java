package com.sai.model.dto.boardMedia;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ViewBoardMediaResponseDto {
	private Long boardMediaId;
	private String boardMediaThumbnail;
	private String boardMediaPath;
	private String boardMediaOriginalName;
	private String boardMediaSaveName;
	private String boardMediaType;
}
