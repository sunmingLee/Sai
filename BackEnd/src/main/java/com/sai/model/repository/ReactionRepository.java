package com.sai.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.model.entity.Reaction;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

}
