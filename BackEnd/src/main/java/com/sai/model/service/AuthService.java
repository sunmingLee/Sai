package com.sai.model.service;

import com.sai.model.entity.User;

public interface AuthService {
	
	public User join(UserJoinResquest joinReq) throws Exception;
	
	

}
