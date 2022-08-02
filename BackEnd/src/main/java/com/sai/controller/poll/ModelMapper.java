package com.sai.controller.poll;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sai.model.entity.poll.Poll;
import com.sai.model.entity.user.User;
import com.sai.poll.ChoiceResponse;
import com.sai.poll.PollResponse;
import com.sai.poll.UserSummary;

public class ModelMapper {
	public static PollResponse mapPollToPollResponse(Poll poll, Map<Long, Long> choiceVotesMap, User creator,
			Long userVote) {
		PollResponse pollResponse = new PollResponse();
		pollResponse.setId(poll.getPollId());
		pollResponse.setQuestion(poll.getPollTitle());
		pollResponse.setCreationDateTime(poll.getCreatedAt());
		pollResponse.setExpirationDateTime(poll.getExpirationDateTime());
		Instant now = Instant.now();
		pollResponse.setIsExpired(poll.getExpirationDateTime().isBefore(now));

		List<ChoiceResponse> choiceResponses = poll.getChoices().stream().map(choice -> {
			ChoiceResponse choiceResponse = new ChoiceResponse();
			choiceResponse.setId(choice.getId());
			choiceResponse.setText(choice.getText());

			if (choiceVotesMap.containsKey(choice.getId())) {
				choiceResponse.setVoteCount(choiceVotesMap.get(choice.getId()));
			} else {
				choiceResponse.setVoteCount(0);
			}
			return choiceResponse;
		}).collect(Collectors.toList());

		pollResponse.setChoices(choiceResponses);
		UserSummary creatorSummary = new UserSummary(creator.getUserId(), creator.getUserName());
		pollResponse.setCreatedBy(creatorSummary);

		if (userVote != null) {
			pollResponse.setSelectedChoice(userVote);
		}

		long totalVotes = pollResponse.getChoices().stream().mapToLong(ChoiceResponse::getVoteCount).sum();
		pollResponse.setTotalVotes(totalVotes);

		return pollResponse;
	}
}
