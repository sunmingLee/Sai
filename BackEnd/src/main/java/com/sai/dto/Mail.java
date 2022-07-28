package com.sai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
	// 받는 메일 주소
	private String toAddress;
	
	// 이메일 제목
	private String title;

	//이메일 내용
	private String message;
	
	// 보내는 이메일 주소
	private String fromAddress;
	
}
