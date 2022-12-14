package com.sai.model.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.model.entity.Board;
import com.sai.model.entity.Family;
import com.sai.model.entity.User;

public interface BoardRepository extends JpaRepository<Board, Long> {
	List<Board> findByFamily(Family family, Pageable pageable);
	
	List<Board> findByUser(User user, Pageable pageable);
	
	Integer countByUser(User user);
	
}
