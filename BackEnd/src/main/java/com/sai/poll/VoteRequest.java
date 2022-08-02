package com.sai.poll;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class VoteRequest {
	@NotNull
	private Long choiceId;

}
