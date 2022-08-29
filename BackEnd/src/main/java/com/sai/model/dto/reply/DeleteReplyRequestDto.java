package com.sai.model.dto.reply;

import lombok.Data;

@Data
public class DeleteReplyRequestDto {
	private Long replyId;

	private String userId;
}
