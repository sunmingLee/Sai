package com.sai.model.dto.reply;

import lombok.Data;

@Data
public class DeleteReplyRequestDTO {
	private Long replyId;

	private String userId;
}
