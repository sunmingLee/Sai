package com.sai.model.dto;

import java.util.List;

import com.sai.model.dto.board.ViewBoardResponseDto;
import com.sai.model.dto.boardMedia.ViewBoardMediaResponseDto;
import com.sai.model.dto.poll.PollResponse;
import com.sai.model.dto.reply.ReplyDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadFeedResponseDto {
	private ViewBoardResponseDto viewBoardResponseDto;
	private List<ViewBoardMediaResponseDto> viewBoardMediaResponseDto;
	private PollResponse PollResponse;
	private boolean isBoardLiked;
	private ReplyDto replyDto;
}
