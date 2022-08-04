package com.sai.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.model.entity.Board;
import com.sai.model.entity.BoardTagged;

public interface BoardTaggedRepository extends JpaRepository<BoardTagged, Long> {

	void deleteByBoard(Board board);

	List<BoardTagged> findByBoard(Board board);

}
