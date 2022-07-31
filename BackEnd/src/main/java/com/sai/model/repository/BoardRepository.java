package com.sai.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.model.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
