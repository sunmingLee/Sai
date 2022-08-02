package com.sai.model.repository.poll;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sai.model.entity.poll.Poll;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long>{
	Optional<Poll> findByPollId(Long pollId);
	
	Page<Poll> findByCreatedBy(String userId, Pageable pageable);
	
	long countByCreatedBy(String userId);
	
	List<Poll> findByPollIdIn(List<Long> pollIds);
	
	List<Poll> findByPollIdIn(List<Long> pollIds, Sort sort);
	

}
