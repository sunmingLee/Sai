package com.sai.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.model.entity.MainPlan;

public interface MainPlanRepository extends JpaRepository<MainPlan, Long> {


	List<MainPlan> findAllByFamilyFamilyIdOrderByMainPlanStartDatetimeAsc(String familyId);

}
