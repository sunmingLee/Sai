package com.sai.model.service;

import com.sai.model.dto.MailDto;

public interface MailService {

	// 이메일 생성
	public MailDto createMail(String information, String memberEmail, String reqType);

	// 이메일 전송
	public void sendMail(MailDto mail);



}
