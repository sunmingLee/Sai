package com.sai.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sai.model.entity.ChoiceVoteCount;
import com.sai.model.entity.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>{
	  @Query("SELECT NEW com.sai.model.entity.ChoiceVoteCount(v.choice.choiceId, count(v.voteId)) FROM Vote v WHERE v.poll.pollId in :pollIds GROUP BY v.choice.choiceId")
	    List<ChoiceVoteCount> countByPollIdInGroupByChoiceId(@Param("pollIds") List<Long> pollIds);

	    @Query("SELECT NEW com.sai.model.entity.ChoiceVoteCount(v.choice.choiceId, count(v.voteId)) FROM Vote v WHERE v.poll.pollId = :pollId GROUP BY v.choice.choiceId")
	    List<ChoiceVoteCount> countByPollIdGroupByChoiceId(@Param("pollId") Long pollId);

	    @Query("SELECT v FROM Vote v where v.user.userId = :userId and v.poll.pollId in :pollIds")
	    List<Vote> findByUserIdAndPollIdIn(@Param("userId") String userId, @Param("pollIds") List<Long> pollIds);

	    @Query("SELECT v FROM Vote v where v.user.userId = :userId and v.poll.pollId = :pollId")
	    Vote findByUserIdAndPollId(@Param("userId") String userId, @Param("pollId") Long pollId);

	    @Query("SELECT COUNT(v.voteId) from Vote v where v.user.userId = :userId")
	    long countByUserId(@Param("userId") String userId);

	    @Query("SELECT v.poll.pollId FROM Vote v WHERE v.user.userId = :userId")
	    Page<Long> findVotedPollIdsByUserId(@Param("userId") String userId, Pageable pageable);
	    
}
