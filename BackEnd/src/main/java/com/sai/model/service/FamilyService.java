package com.sai.model.service;

import java.util.List;

import com.sai.model.dto.family.FamilyCallsignDto;
import com.sai.model.dto.family.UpdateFamilyDto;
import com.sai.model.entity.Family;
import com.sai.model.entity.FamilyRegister;
import com.sai.model.entity.User;

public interface FamilyService {

	public User createFamilyId(String userId);

	public User disjoinFamily(String userId);

	public FamilyRegister applyFamily(FamilyRegister familyRegister);

	public FamilyRegister responseApplication(FamilyRegister familyRegister);

	public FamilyRegister resultApplication(String userId);

	public FamilyRegister deleteApplication(String userId);

	public Family searchFamily(String familyId);

	public List<FamilyCallsignDto> searchFamilyList(String userId);

	public UpdateFamilyDto updateFamily(UpdateFamilyDto updateFamilyDto);

}
