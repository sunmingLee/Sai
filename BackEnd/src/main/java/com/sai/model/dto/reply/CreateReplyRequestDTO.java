package com.sai.model.dto.reply;

import lombok.Data;

@Data
public class CreateReplyRequestDTO {
	private String userId;
	
	private String replyContent;
}
