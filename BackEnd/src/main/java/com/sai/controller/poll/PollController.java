package com.sai.controller.poll;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sai.model.entity.poll.Poll;
import com.sai.model.repository.poll.PollRepository;
import com.sai.model.repository.poll.VoteRepository;
import com.sai.model.repository.user.UserRepository;
import com.sai.model.service.PollService;
import com.sai.poll.ApiResponse;
import com.sai.poll.PagedResponse;
import com.sai.poll.PollRequest;
import com.sai.poll.PollResponse;
import com.sai.poll.VoteRequest;
import com.sai.security.CurrentUser;
import com.sai.security.UserPrincipal;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/poll")
@RequiredArgsConstructor
public class PollController {

	private final PollRepository pollRepository;
	private final VoteRepository voteRepository;
	private final UserRepository userRepository;
	private final PollService pollService;

	@GetMapping
	public PagedResponse<PollResponse> getPolls(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
		return pollService.getAllPolls(currentUser, page, size);
	}

	@PostMapping
	public ResponseEntity<?> createPoll(@Valid @RequestBody PollRequest pollRequest) {
		Poll poll = pollService.createPoll(pollRequest);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{pollId}")
				.buildAndExpand(poll.getPollId()).toUri();
		return ResponseEntity.created(location)
				.body(new ApiResponse(true, "Poll Created Successfully"));
	}

	@GetMapping("/{pollId}")
	public PollResponse getPollById(@CurrentUser UserPrincipal currentUser, @PathVariable Long pollId) {
		return pollService.getPollById(pollId, currentUser);
	}

	@PostMapping("/{pollId}/votes")
	public PollResponse castVote(@PathVariable Long pollId,
			@Valid @RequestBody VoteRequest voteRequest,
			@CurrentUser UserPrincipal currentUser) {
		return pollService.castVoteAndGetUpdatedPoll(pollId, voteRequest, currentUser);
	}


}
