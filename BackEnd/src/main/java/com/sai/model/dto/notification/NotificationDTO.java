package com.sai.model.dto.notification;

import java.time.LocalDateTime;

import com.sai.model.dto.user.UserInfoDTO;

import lombok.Data;

@Data
public class NotificationDTO {
	private Long notiId;
	
	private UserInfoDTO toUser;
	
	private UserInfoDTO fromUser;
	
	private String notiContent;
	
	private String notiType;
	
	private Long notiContentId;
	
	private LocalDateTime notiDateTime;
	
	private Boolean notiReadYn;
}
