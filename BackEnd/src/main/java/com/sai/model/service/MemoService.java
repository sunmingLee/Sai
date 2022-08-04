package com.sai.model.service;

import java.util.List;

import com.sai.model.dto.memo.CreateMemoRequestDto;
import com.sai.model.dto.memo.MemoDto;
import com.sai.model.dto.memo.MemoPopupDto;

public interface MemoService {
	public List<MemoPopupDto> getMemoPopup(String userId, String familyId);
	public List<MemoDto> getMemo(String familyId);
	public void readMemo(String userId);
	public void createMemo(CreateMemoRequestDto createMemoRequestDto);
	public void changeColor(Long memoId, String color);
	public void deleteMemo(Long memoId);
}
