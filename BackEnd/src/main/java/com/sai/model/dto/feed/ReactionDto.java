package com.sai.model.dto.feed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReactionDto {
	private String boardReactionId;
	private String boardId;
	private String userId;
}
