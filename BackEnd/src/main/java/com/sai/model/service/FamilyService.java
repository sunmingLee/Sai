package com.sai.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sai.model.dto.family.AnswerFamilyRegisterRequestDto;
import com.sai.model.dto.family.FamilyDto;
import com.sai.model.dto.family.FamilyRegisterDto;
import com.sai.model.dto.family.InsertFamilyRegisterRequestDto;
import com.sai.model.dto.family.UpdateFamilyRequestDto;
import com.sai.model.dto.family.ViewFamilyCallsignResponseDto;

public interface FamilyService {

	public String createFamilyId(String userId);

	public void disjoinFamily(String userId);

	public void applyFamily(InsertFamilyRegisterRequestDto insertFamilyRegisterRequestDto);

	public List<ViewFamilyCallsignResponseDto> responseApplication(String userId,
			AnswerFamilyRegisterRequestDto answerFamilyRegisterRequestDto);

	public FamilyRegisterDto resultApplication(String userId);

	public void deleteApplication(String userId);

	public FamilyDto searchFamily(String familyId);

	public List<ViewFamilyCallsignResponseDto> searchFamilyList(String userId);

	public void updateFamily(UpdateFamilyRequestDto updateFamilyRequestDto, MultipartFile file);

}
