package com.sai.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.model.entity.Board;
import com.sai.model.entity.BoardLike;
import com.sai.model.entity.User;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

	BoardLike findOneByBoardAndUser(Board board, User user);

}
