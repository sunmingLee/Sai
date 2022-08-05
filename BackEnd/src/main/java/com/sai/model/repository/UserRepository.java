package com.sai.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sai.model.entity.Family;
import com.sai.model.entity.User;

@Repository
public interface UserRepository {
	public Family findFamilyByUserId(String userId);
}
