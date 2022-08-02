package com.sai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sai.model.dto.notification.GetNotificationResponseDTO;
import com.sai.model.dto.notification.NotificationDTO;
import com.sai.model.service.NotificationService;

@RestController
@RequestMapping(value = "/notification")
public class NotificationController {
	@Autowired
	NotificationService notificationService;
	
	// 알림 리스트 조회
	// 유저 아이디를 보내면, 알림 리스트를 반환
	@GetMapping("/{userId}")
	public ResponseEntity<?> getNoti(@PathVariable String userId){
		List<NotificationDTO> list = notificationService.getNotiList(userId);
		GetNotificationResponseDTO notiResponse = new GetNotificationResponseDTO();
		notiResponse.setNotiList(list);
		return new ResponseEntity<GetNotificationResponseDTO>(notiResponse, HttpStatus.OK);
	}
	
	
	// 알림 확인
	
	// 알림 삭제
	
	// 알림 전체 삭제
}
