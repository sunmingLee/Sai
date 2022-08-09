package com.sai.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notification")
public class Notification {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "noti_id")
	private Long notiId;
	
	@ManyToOne
	@JoinColumn(name = "noti_to_user_id")
	private User toUser;
	
	@ManyToOne
	@JoinColumn(name = "noti_from_user_id")
	private User fromUser;
	
	@Column(name = "noti_content")
	private String notiContent;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "noti_type")
	private NotiType notiType;
	
	@Column(name = "noti_datetime")
	private LocalDateTime notiDateTime;
	
	@Column(name = "noti_read_yn")
	private Boolean notiReadYn;
	
	@Column(name = "noti_content_id")
	private Long notiContentId;
	
	public void readNoti() {
		this.notiReadYn = true;
	}
}
