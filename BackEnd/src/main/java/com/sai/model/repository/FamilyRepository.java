package com.sai.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.model.entity.Family;

public interface FamilyRepository extends JpaRepository<Family, String> {

}
