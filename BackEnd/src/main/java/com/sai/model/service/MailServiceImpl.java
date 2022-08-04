package com.sai.model.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sai.model.dto.MailDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

	private final JavaMailSender mailSender;

	private static final String fromAddress = "ssafy.sai@gmail.com";

	// 이메일 생성
	@Override
	public MailDto createMail(String information, String memberEmail, String reqType) {

		MailDto mail = new MailDto();

		if (reqType.equals("findUserId")) {
			mail = MailDto.builder().toAddress(memberEmail).title("사이 아이디 안내 이메일입니다")
					.message("안녕하세요. 사이 아이디 안내 메일입니다." + "\n" + "회원님의 아이디는 아래와 같습니다." + information)
					.fromAddress(fromAddress).build();
		} else if (reqType.equals("findUserPw")) {
			mail = MailDto.builder().toAddress(memberEmail).title("사이 임시 비밀번호 안내 이메일입니다")
					.message("안녕하세요. 사이 임시 비밀번호 안내 메일입니다." + "\n" + "회원님의 임시 비밀번호는 아래와 같습니다." + information)
					.fromAddress(fromAddress).build();
		}
		return mail;

	}

	// 이메일 전송
	@Override
	public void sendMail(MailDto mail) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(mail.getToAddress());
		mailMessage.setSubject(mail.getTitle());
		mailMessage.setText(mail.getMessage());
		mailMessage.setFrom(mail.getFromAddress());
		mailMessage.setReplyTo(mail.getFromAddress());

		mailSender.send(mailMessage);

	}

}
