package com.sai.model.dto.reply;

import lombok.Data;

@Data
public class CreateReplyRequestDto {
	private String userId;
	
	private String replyContent;
}
