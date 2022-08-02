package com.sai.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sai.model.entity.Notification;
import com.sai.model.entity.User;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
	public List<Notification> findNotificationsByToUser(User user);
	public Notification findNotificationdByNotiId(Long notiId);
	public void deleteAllByToUser(User user);
}
