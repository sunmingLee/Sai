package com.sai.model.service;

import java.util.List;

import com.sai.model.dto.notification.CreateNotificationRequestDTO;
import com.sai.model.dto.notification.NotificationDTO;

public interface NotificationService {
	public List<NotificationDTO> getNotiList(String userId);
	public void readNoti(String userId);
	public String createNoti(CreateNotificationRequestDTO createNotiRequestDTO);
	public String deleteNoti(String userId, Long notiId);
	public String deleteEveryNoti(String userId);
}
