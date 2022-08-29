package com.sai.model.dto.albumMedia;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DownloadAlbumMediaDto {
	private HttpHeaders headers;
	private Resource resource;
}
