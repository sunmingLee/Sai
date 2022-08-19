package com.sai.model.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.sai.model.dto.album.InputAlbumRequestDto;
import com.sai.model.dto.album.ViewAlbumResponseDto;
import com.sai.model.dto.albumMedia.DownloadAlbumMediaDto;
import com.sai.model.dto.albumMedia.ViewAlbumMediaResponseDto;

public interface AlbumService {

	public void makeAlbum(InputAlbumRequestDto inputAlbumRequestDto);

	public List<ViewAlbumResponseDto> getAlbumList(String familyId);

	public List<ViewAlbumMediaResponseDto> getAlbumMediaList(Long albumId, Pageable pageable);

	public void deleteAlbum(Long albumId);

	public void insertAlbumMedia(Long albumId, List<MultipartFile> files);

	public DownloadAlbumMediaDto downloadAlbumMedia(Long albumMediaId);

	public void deleteAlbumMedia(Long albumMediaId);

}
