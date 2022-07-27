package com.sai.model.service;

import com.sai.dto.Mail;

public interface MailService {
	
	// 이메일 생성
	public Mail createMail(String tmpPassword, String memberEmail);
	
	// 이메일 전송
	public void sendMail(Mail mail);
	
	

}
