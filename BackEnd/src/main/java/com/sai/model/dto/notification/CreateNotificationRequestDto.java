package com.sai.model.dto.notification;

import com.sai.model.entity.NotiType;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateNotificationRequestDto {
	private String notiToUserId;
	
	private String notiFromUserId;
	
	private String notiContent;
	
	private NotiType notiType;
	
	private Long notiContentId;
}
