package com.sai.model.dto;

import java.util.List;

import com.sai.model.dto.board.UpdateBoardRequestDto;
import com.sai.model.dto.boardMedia.InputBoardMediaRequestDto;
import com.sai.model.dto.boardTagged.InputBoardTaggedRequestDto;
import com.sai.model.dto.poll.InputPollRequestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ModifyBoardRequestDto {
	private UpdateBoardRequestDto updateBoardRequestDto;
	private List<Long> deleteBoardMediaIds;
	private InputPollRequestDto inputPollRequestDto;
	private List<InputBoardTaggedRequestDto> inputBoardTaggedRequestDtos;
	private boolean isBoardModified;
	private boolean isPollModified;
	private boolean isBoardTaggedModified;
}
