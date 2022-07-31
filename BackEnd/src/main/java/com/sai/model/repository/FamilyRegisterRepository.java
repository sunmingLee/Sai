package com.sai.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.model.entity.FamilyRegister;
import com.sai.model.entity.User;

public interface FamilyRegisterRepository extends JpaRepository<FamilyRegister, Long> {

	FamilyRegister findOneByUser(User user);

}
