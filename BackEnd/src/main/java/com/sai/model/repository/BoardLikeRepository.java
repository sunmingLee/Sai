package com.sai.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.model.entity.BoardLike;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

}
