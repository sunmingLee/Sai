package com.sai.model.dto.memo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MemoDto {
	private Long memoId;
	
	private String userId;
	
	private LocalDateTime memoRegDateTime;
	
	private Double x;
	
	private Double y;
	
	private String color;
	
	private String memoContent;
}
