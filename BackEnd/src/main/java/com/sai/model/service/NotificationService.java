package com.sai.model.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.sai.model.dto.notification.CreateNotificationRequestDto;
import com.sai.model.dto.notification.NotificationDto;

public interface NotificationService {
	public List<NotificationDto> getNotiList(String userId, Pageable pageable);
	public void readNoti(String userId);
	public String createNoti(CreateNotificationRequestDto createNotiRequestDTO);
	public String deleteNoti(String userId, Long notiId);
	public String deleteEveryNoti(String userId);
}
