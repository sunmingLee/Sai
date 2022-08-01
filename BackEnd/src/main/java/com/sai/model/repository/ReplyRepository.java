package com.sai.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sai.model.entity.Board;
import com.sai.model.entity.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long>{
	List<Reply> findRepliesByBoard(Board board);
	String findUserIdByReplyId(Long replyId);
	void deleteById(Long replyId);
}
