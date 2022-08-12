package com.sai.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sai.model.entity.FamilyCallsign;
import com.sai.model.entity.User;

public interface FamilyCallsignRepository extends JpaRepository<FamilyCallsign, Long> {

	List<FamilyCallsign> findByFromUser(User user);

//	List<FamilyCallsign> removeByFromUser(User user);
	void deleteByFromUser(User user);

//	List<FamilyCallsign> removeByToUser(User user);
	void deleteByToUser(User user);
}
