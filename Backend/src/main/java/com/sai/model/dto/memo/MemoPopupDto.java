package com.sai.model.dto.memo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MemoPopupDto {
	private String memoContent;
	
	private String color;
	
	private LocalDateTime memoRegDateTime;
}
