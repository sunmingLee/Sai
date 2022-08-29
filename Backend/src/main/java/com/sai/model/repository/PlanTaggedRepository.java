package com.sai.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.model.entity.MainPlan;
import com.sai.model.entity.PlanTagged;

public interface PlanTaggedRepository extends JpaRepository<PlanTagged, Long> {

	List<PlanTagged> findByMainPlan(MainPlan mainPlan);

}
