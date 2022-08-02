package com.sai.model.dto.notification;

import java.util.List;

import lombok.Data;

@Data
public class GetNotificationResponseDTO {
	private List<NotificationDTO> notiList;
}
