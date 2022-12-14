package com.sai.model.service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sai.exception.poll.BadRequestException;
import com.sai.exception.poll.ResourceNotFoundException;
import com.sai.model.dto.poll.PollRequest;
import com.sai.model.dto.poll.PollResponse;
import com.sai.model.dto.poll.VoteRequest;
import com.sai.model.entity.Choice;
import com.sai.model.entity.ChoiceVoteCount;
import com.sai.model.entity.Poll;
import com.sai.model.entity.User;
import com.sai.model.entity.Vote;
import com.sai.model.repository.BoardRepository;
import com.sai.model.repository.PollRepository;
import com.sai.model.repository.UserRepository;
import com.sai.model.repository.VoteRepository;
import com.sai.security.UserPrincipal;
import com.sai.util.PollModelMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PollService {

	private final PollRepository pollRepository;
	private final VoteRepository voteRepository;
	private final UserRepository userRepository;
	private final BoardRepository boardRepository;

	// 투표 만들기
	public Poll createPoll(PollRequest pollRequest) {
		Poll poll = new Poll();
		
		poll.setBoard(boardRepository.findById(pollRequest.getBoardId()).get());		
		poll.setPollTitle(pollRequest.getQuestion());
		
		pollRequest.getChoices().forEach(choiceRequest -> {
			poll.addChoice(new Choice(choiceRequest.getText()));
		});

		poll.setExpirationDateTime(pollRequest.getExpirationDateTime());

		return pollRepository.save(poll);
	}

	// 투표 삭제하기
	public void deletePoll(Long pollId) {
		pollRepository.deleteById(pollId);
	};

	// 투표 조회하기
	public PollResponse getPollById(Long pollId, UserPrincipal currentUser) {
		
		Poll poll = pollRepository.findById(pollId)
				.orElseThrow(() -> new ResourceNotFoundException("Poll", "id", pollId));

		// Retrieve Vote Counts of every choice belonging to the current poll
		List<ChoiceVoteCount> votes = voteRepository.countByPollIdGroupByChoiceId(pollId);
		
		Map<Long, Long> choiceVotesMap = votes.stream()
				.collect(Collectors.toMap(ChoiceVoteCount::getChoiceId, ChoiceVoteCount::getVoteCount));
		
		// Retrieve vote done by logged in user
		Vote userVote = null;
		if (currentUser != null) {
			userVote = voteRepository.findByUserIdAndPollId(currentUser.getUserId(), pollId);
		}

		return PollModelMapper.mapPollToPollResponse(poll, choiceVotesMap, null,
				userVote != null ? userVote.getChoice().getChoiceId() : null);
	}

	// 선택하고 업데이트 하기 혹은 취소하기
	public PollResponse castVoteAndGetUpdatedPoll(Long pollId, VoteRequest voteRequest, UserPrincipal currentUser) {
		Poll poll = pollRepository.findById(pollId)
				.orElseThrow(() -> new ResourceNotFoundException("Poll", "id", pollId));

		if (poll.getExpirationDateTime().isBefore(LocalDateTime.now())) {
			throw new BadRequestException("Sorry! This Poll has already expired");
		}

		User user = userRepository.findByUserId(currentUser.getUserId()).get();

		// user와 poll 이용해서 vote가 있는지 확인
		// 투표가 없는 경우
		if (voteRepository.findByUserIdAndPollId(user.getUserId(), pollId) == null) {

			Choice selectedChoice = poll.getChoices().stream()
					.filter(choice -> choice.getChoiceId().equals(voteRequest.getChoiceId())).findFirst()
					.orElseThrow(() -> new ResourceNotFoundException("Choice", "id", voteRequest.getChoiceId()));

			Vote vote = new Vote();
			vote.setPoll(poll);
			vote.setUser(user);
			vote.setChoice(selectedChoice);

			try {
				vote = voteRepository.save(vote);
			} catch (DataIntegrityViolationException ex) {
				throw new BadRequestException("이미 투표하셨습니다!");
			}
			
			// -- Vote Saved, Return the updated Poll Response now --

			// Retrieve Vote Counts of every choice belonging to the current poll
			List<ChoiceVoteCount> votes = voteRepository.countByPollIdGroupByChoiceId(pollId);
			
			Map<Long, Long> choiceVotesMap = votes.stream()
					.collect(Collectors.toMap(ChoiceVoteCount::getChoiceId, ChoiceVoteCount::getVoteCount));

			// Retrieve poll creator details
			User creator = userRepository.findById(poll.getCreatedBy())
					.orElseThrow(() -> new ResourceNotFoundException("User", "id", poll.getCreatedBy()));
			
			PollResponse pollResponse = PollModelMapper.mapPollToPollResponse(poll, choiceVotesMap, creator, vote.getChoice().getChoiceId());
			pollResponse.setIsDup(false);
			return pollResponse;
			
			// 투표가 있는 경우
		} else {
			Vote vote = voteRepository.findByUserIdAndPollId(user.getUserId(), pollId);

			// 다른 선택지를 고른 경우
			if(vote.getChoice().getChoiceId() != voteRequest.getChoiceId()) {
				throw new BadRequestException("이미 투표하셨습니다!");
				// 같은 선택지를 고른 경우
			} else {
				voteRepository.deleteById(vote.getVoteId());
			}
			// -- Vote Saved, Return the updated Poll Response now --
			// Retrieve Vote Counts of every choice belonging to the current poll
			List<ChoiceVoteCount> votes = voteRepository.countByPollIdGroupByChoiceId(pollId);

			Map<Long, Long> choiceVotesMap = votes.stream()
					.collect(Collectors.toMap(ChoiceVoteCount::getChoiceId, ChoiceVoteCount::getVoteCount));

			// Retrieve poll creator details
			User creator = userRepository.findById(poll.getCreatedBy())
					.orElseThrow(() -> new ResourceNotFoundException("User", "id", poll.getCreatedBy()));

			
			PollResponse pollResponse = PollModelMapper.mapPollToPollResponse(poll, choiceVotesMap, creator, vote.getChoice().getChoiceId());
			pollResponse.setIsDup(true);
			return pollResponse;
		}

	}

	private Map<Long, Long> getChoiceVoteCountMap(List<Long> pollIds) {
		// Retrieve Vote Counts of every Choice belonging to the given pollIds
		List<ChoiceVoteCount> votes = voteRepository.countByPollIdInGroupByChoiceId(pollIds);

		Map<Long, Long> choiceVotesMap = votes.stream()
				.collect(Collectors.toMap(ChoiceVoteCount::getChoiceId, ChoiceVoteCount::getVoteCount));

		return choiceVotesMap;
	}

	private Map<Long, Long> getPollUserVoteMap(UserPrincipal currentUser, List<Long> pollIds) {
		// Retrieve Votes done by the logged in user to the given pollIds
		Map<Long, Long> pollUserVoteMap = null;
		if (currentUser != null) {
			List<Vote> userVotes = voteRepository.findByUserIdAndPollIdIn(currentUser.getUserId(), pollIds);

			pollUserVoteMap = userVotes.stream().collect(
					Collectors.toMap(vote -> vote.getPoll().getPollId(), vote -> vote.getChoice().getChoiceId()));
		}
		return pollUserVoteMap;
	}

	Map<String, User> getPollCreatorMap(List<Poll> polls) {
		// Get Poll Creator details of the given list of polls
		List<String> creatorIds = polls.stream().map(Poll::getCreatedBy).distinct().collect(Collectors.toList());

		List<User> creators = userRepository.findByUserIdIn(creatorIds);
		Map<String, User> creatorMap = creators.stream()
				.collect(Collectors.toMap(User::getUserId, Function.identity()));

		return creatorMap;
	}

}
