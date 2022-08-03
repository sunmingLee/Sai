package com.sai.model.service;

import com.sai.model.dto.CreateBoardRequestDto;
import com.sai.model.dto.ModifyBoardRequestDto;

public interface FeedService {

	// 반환값 수정 필요
	public void readAllBoard(String familyId, String userId);

	// 반환값 수정 필요
	public void readOneBoard(Long boardId, String userId);

	public void writeBoard(CreateBoardRequestDto createBoardRequestDto);

	public void updateBoard(ModifyBoardRequestDto modifyBoardRequestDto);

	public void deleteBoard(Long boardId);

	public void upBoardLike(Long boardId, String userId);

	public void downBoardLike(Long boardId, String userId);

}
