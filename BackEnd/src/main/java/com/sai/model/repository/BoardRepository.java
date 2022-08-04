package com.sai.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sai.model.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{

}
