package com.sai.model.service;

import com.sai.model.dto.reply.CreateReplyRequestDto;
import com.sai.model.dto.reply.GetReplyResponseDto;
import com.sai.model.dto.reply.UpdateReplyRequestDto;
import com.sai.model.entity.Reply;

public interface ReplyService {
	public String createReply(Long boardId, CreateReplyRequestDto createReplyRequestDTO);
	public GetReplyResponseDto getReply(Long id);
	public String getUserIdByReplyId(Long replyId);
	public void deleteReplyByReplyId(Long replyId);
	public void updateReply(UpdateReplyRequestDto updateReplyRequestDTO);
}

