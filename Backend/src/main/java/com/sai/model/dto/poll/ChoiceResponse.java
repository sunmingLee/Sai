package com.sai.model.dto.poll;

import lombok.Data;

@Data
public class ChoiceResponse {
	private long id;
    private String text;
    private long voteCount;
}
