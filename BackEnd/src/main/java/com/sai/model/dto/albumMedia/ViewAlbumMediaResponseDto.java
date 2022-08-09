package com.sai.model.dto.albumMedia;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ViewAlbumMediaResponseDto {
	private Long albumMediaId;
	private String albumMediaThumbnail;
	private String albumMediaPath;
	private String albumMediaOriginalName;
	private String albumMediaSaveName;
	private String albumMediaType;
}
