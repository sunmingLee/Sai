package com.sai.model.repository.family;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.model.entity.family.FamilyRegister;

public interface FamilyRegisterRepository {
	Optional<FamilyRegister> findOneByUserUserId(String userId);
}
