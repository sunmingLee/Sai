package com.sai.model.dto.album;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ViewAlbumResponseDto {
	private String familyId;
	private String albumName;
	private LocalDate albumStartDate;
	private LocalDate albumEndDate;
	private String thumbnailPath;
}
