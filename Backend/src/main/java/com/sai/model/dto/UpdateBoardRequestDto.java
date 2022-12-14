package com.sai.model.dto;

import java.util.List;

import com.sai.model.dto.board.ModifyBoardRequestDto;
import com.sai.model.dto.boardTagged.InputBoardTaggedRequestDto;
import com.sai.model.dto.poll.PollRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateBoardRequestDto {
	private ModifyBoardRequestDto modifyBoardRequestDto;
	private List<Long> deleteBoardMediaIds;
	private PollRequest PollRequest;
	private List<InputBoardTaggedRequestDto> inputBoardTaggedRequestDtos;
	private boolean isBoardModified;
	private boolean isPollModified;
	private boolean isBoardTaggedModified;
}
