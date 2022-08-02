package com.sai.model.entity.user;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class SessionUser implements Serializable {
	private String name;
	private String email;
	private String picture;

	public SessionUser(User user) {
		this.name = user.getUserName();
		this.email = user.getEmail();
		this.picture = user.getUserImagePath();
	}
}
