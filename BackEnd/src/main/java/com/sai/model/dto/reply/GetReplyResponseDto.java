package com.sai.model.dto.reply;

import java.util.List;

import lombok.Data;

@Data
public class GetReplyResponseDto {
	private List<ReplyDto> replyList;
}
