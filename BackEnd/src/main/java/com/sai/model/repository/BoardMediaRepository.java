package com.sai.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.model.entity.Board;
import com.sai.model.entity.BoardMedia;

public interface BoardMediaRepository extends JpaRepository<BoardMedia, Long> {
	List<BoardMedia> findByBoard(Board board);

}
