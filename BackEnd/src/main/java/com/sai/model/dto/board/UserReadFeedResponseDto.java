package com.sai.model.dto.board;

import java.util.List;

import com.sai.model.dto.ReadFeedResponseDto;

import lombok.Data;


@Data
public class UserReadFeedResponseDto {
	
	public List<ReadFeedResponseDto> readFeedResponseDtos;
	
	public Integer userBoardNum;
	
}
