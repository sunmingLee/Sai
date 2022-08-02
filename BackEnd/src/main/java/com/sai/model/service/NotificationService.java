package com.sai.model.service;

import java.util.List;

import com.sai.model.dto.notification.NotificationDTO;

public interface NotificationService {
	public List<NotificationDTO> getNotiList(String userId);
}
