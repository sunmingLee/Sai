package com.sai.controller;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sai.model.dto.poll.ApiResponse;
import com.sai.model.dto.poll.PollRequest;
import com.sai.model.dto.poll.PollResponse;
import com.sai.model.dto.poll.VoteRequest;
import com.sai.model.entity.Poll;
import com.sai.model.repository.PollRepository;
import com.sai.model.repository.UserRepository;
import com.sai.model.repository.VoteRepository;
import com.sai.model.service.PollService;
import com.sai.security.CurrentUser;
import com.sai.security.UserPrincipal;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/poll")
@RequiredArgsConstructor
@Transactional
public class PollController {

	private final PollRepository pollRepository;
	private final VoteRepository voteRepository;
	private final UserRepository userRepository;
	private final PollService pollService;

	// 투표 만들기 
	@PostMapping
	public ResponseEntity<?> createPoll(@Valid @RequestBody PollRequest pollRequest) {
		Poll poll = pollService.createPoll(pollRequest);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{pollId}")
				.buildAndExpand(poll.getPollId()).toUri();
		return ResponseEntity.created(location)
				.body(new ApiResponse(true, "Poll Created Successfully"));
	}
	
	// 투표 삭제하기
	@DeleteMapping("/{pollId}")
	public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
		pollService.deletePoll(pollId);
		return ResponseEntity.ok().body(new ApiResponse(true, "Poll Removed Successfully"));
	}
	
	// 투표 보기
	@GetMapping("/{pollId}")
	public PollResponse getPollById(UserPrincipal currentUser, @PathVariable Long pollId) {
//		public PollResponse getPollById(@CurrentUser UserPrincipal currentUser, @PathVariable Long pollId) {
		return pollService.getPollById(pollId, currentUser);
	}

	
	// 선택 하기 & 선택 취소하기
	@PostMapping("/{pollId}/votes")
	public ResponseEntity<?> castVote(@PathVariable Long pollId,
//			 VoteRequest voteRequest,
			@Valid @RequestBody VoteRequest voteRequest,
			@CurrentUser UserPrincipal currentUser) {
		// 대부분의 경우 @RequestBody는 복수개를 사용하지 않는다
		// 화면 붙이기 전에 user 객체를 넘겼을 때 오류가 발생했던 이유는 @RequestBody가 두 개였기 때문
		// 화면 붙이고 로그인 구현이 되면 서버에서 현재 로그인한 유저 정보를 가져올 수 있으므로 이런 문제 발생하지 않을 것
		
		try {
			return ResponseEntity.status(200).body(pollService.castVoteAndGetUpdatedPoll(pollId, voteRequest, currentUser));
		} catch (Exception e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	// 기표 취소하기
	// 프론트에서 투표할 때, 


}
