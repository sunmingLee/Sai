package com.sai.model.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.sai.model.dto.CreateBoardRequestDto;
import com.sai.model.dto.ReadBoardResponseDto;
import com.sai.model.dto.ReadFeedResponseDto;
import com.sai.model.dto.UpdateBoardRequestDto;

public interface FeedService {

	public List<ReadFeedResponseDto> readAllBoard(String familyId, String userId, Pageable pageable);

	public ReadBoardResponseDto readOneBoard(Long boardId, String userId);

	public void writeBoard(CreateBoardRequestDto createBoardRequestDto, List<MultipartFile> files);

	public void updateBoard(UpdateBoardRequestDto updateBoardRequestDto);

	public void deleteBoard(Long boardId);

	public void upBoardLike(Long boardId, String userId);

	public void downBoardLike(Long boardId, String userId);

}
