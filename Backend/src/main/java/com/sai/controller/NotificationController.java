package com.sai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sai.model.dto.notification.CreateNotificationRequestDto;
import com.sai.model.dto.notification.GetNotificationResponseDto;
import com.sai.model.dto.notification.NotificationDto;
import com.sai.model.service.NotificationService;

@RestController
@RequestMapping(value = "/notification")
@CrossOrigin("*")
public class NotificationController {

	@Autowired
	NotificationService notificationService;

	// 알림 리스트 조회
	// 유저 아이디를 보내면, 알림 리스트를 반환
	@GetMapping("/{userId}")
	public ResponseEntity<?> getNoti(@PathVariable String userId, Pageable pageable) {
		List<NotificationDto> list = notificationService.getNotiList(userId, pageable);
		GetNotificationResponseDto notiResponse = new GetNotificationResponseDto();
		notiResponse.setNotiList(list);
		return new ResponseEntity<GetNotificationResponseDto>(notiResponse, HttpStatus.OK);
	}

	// 알림 확인
	@PutMapping("/{userId}")
	public void readNoti(@PathVariable String userId) {
		notificationService.readNoti(userId);
	}

	// 알림 생성
	@PostMapping
	public ResponseEntity<?> createNoti(@RequestBody CreateNotificationRequestDto createNotiRequestDTO) {
		ResponseEntity<String> response;
		try {
			String message = notificationService.createNoti(createNotiRequestDTO);
			response = new ResponseEntity<String>(message, HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
		return response;
	}

	// 알림 삭제
	@DeleteMapping("/{notiId}")
	public ResponseEntity<?> deleteNoti(@RequestBody String userId, @PathVariable Long notiId) {
		ResponseEntity<String> response;
		try {
			String message = notificationService.deleteNoti(userId, notiId);
			response = new ResponseEntity<String>(message, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}

		return response;
	}

	// 알림 전체 삭제
	@DeleteMapping
	public ResponseEntity<?> deleteEveryNoti(@RequestBody String userId) {
		ResponseEntity<String> response;
		try {
			String message = notificationService.deleteEveryNoti(userId);
			response = new ResponseEntity<String>(message, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}

		return response;
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		return new ResponseEntity<String>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
