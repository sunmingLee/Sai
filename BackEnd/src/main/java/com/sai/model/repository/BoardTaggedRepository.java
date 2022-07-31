package com.sai.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.model.entity.BoardTagged;

public interface BoardTaggedRepository extends JpaRepository<BoardTagged, Long> {

}
