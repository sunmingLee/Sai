package com.sai.model.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sai.model.dto.notification.CreateNotificationRequestDto;
import com.sai.model.dto.notification.NotificationDto;
import com.sai.model.entity.Notification;
import com.sai.model.entity.User;
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
	public List<NotificationDto> getNotiList(String userId) { 
		List<Notification> list = notificationRepository.findNotificationsByToUser(userRepository.findById(userId).get());
		List<NotificationDto> resultList = new ArrayList<NotificationDto>();
		for(Notification noti : list) {
			// noti to notiDTO
			NotificationDto item = modelMapper.map(noti, NotificationDto.class);
			resultList.add(item);
		}
		return resultList;
	}


	@Override
	public void readNoti(String userId) {
		List<Notification> list = notificationRepository.findNotificationsByToUser(userRepository.findById(userId).get());
		for(Notification noti : list) {
			noti.readNoti();
			notificationRepository.save(noti);
		}
	}


	@Override
	public String createNoti(CreateNotificationRequestDto createNotiRequestDTO) {
		User toUser = userRepository.findById(createNotiRequestDTO.getNotiToUserId()).get();
		User fromUser = userRepository.findById(createNotiRequestDTO.getNotiFromUserId()).get();
		
		Notification noti = Notification.builder()
				.toUser(toUser)
				.fromUser(fromUser)
				.notiContent(createNotiRequestDTO.getNotiContent())
				.notiType(createNotiRequestDTO.getNotiType())
				.notiContentId(createNotiRequestDTO.getNotiContentId())
				.notiReadYn(false)
				.notiDateTime(LocalDateTime.now())
				.build();
		
		notificationRepository.save(noti);
		return "success";
	}


	@Override
	public String deleteNoti(String userId, Long notiId) {
		String message;
		if(notificationRepository.findNotificationdByNotiId(notiId).getToUser().getUserId().equals(userId)) {
			notificationRepository.deleteById(notiId);
			message = "success";
		} else {
			message = "fail";
		}
		return message;
	}


	@Override
	public String deleteEveryNoti(String userId) {
		User user = userRepository.findById(userId).get();
		notificationRepository.deleteAllByToUser(user);
		return "success";
	}
	
	
}
