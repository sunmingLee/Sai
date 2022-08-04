package com.sai.model.dto.memo;

import lombok.Data;

@Data
public class CreateMemoRequestDto {
	private String userId;
	
	private Double x;
	
	private Double y;
	
	private String color;
	
	private String memoContent;
}
