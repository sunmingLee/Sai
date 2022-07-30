package com.sai.model.service;

import com.sai.dto.Mail;

public interface MailService {
	
	// 이메일 생성
	public Mail createMail(String information, String memberEmail, String reqType);
	
	// 이메일 전송
	public void sendMail(Mail mail);
	
	

}
