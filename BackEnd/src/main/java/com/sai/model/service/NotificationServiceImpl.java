package com.sai.model.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sai.model.dto.notification.NotificationDTO;
import com.sai.model.entity.Notification;
import com.sai.model.repository.NotificationRepository;
import com.sai.model.repository.UserRepository;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	ModelMapper modelMapper;
	

	// userId를 활용해서, Noti를 쫙 불러모아서, 노티를 DTO에 담아서 반환
	@Override
	public List<NotificationDTO> getNotiList(String userId) { 
		List<Notification> list = notificationRepository.findNotificationsByToUser(userRepository.findById(userId).get());
		List<NotificationDTO> resultList = new ArrayList<NotificationDTO>();
		for(Notification noti : list) {
			// noti to notiDTO
			NotificationDTO item = modelMapper.map(noti, NotificationDTO.class);
			resultList.add(item);
		}
		return resultList;
	}
	
}
