package com.sai.model.repository.family;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.model.entity.family.FamilyRegister;

public interface FamilyRegisterRepository extends JpaRepository<FamilyRegister,	 Long> {
	FamilyRegister findOneByUserUserId(String userId);
}
