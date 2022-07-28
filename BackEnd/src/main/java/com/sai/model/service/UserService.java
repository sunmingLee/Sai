package com.sai.model.service;

import java.util.HashMap;

import com.sai.dto.UserInfoRequestDto;
import com.sai.model.entity.User;

public interface UserService {
	
	public boolean checkUserIdDuplicate(String userId);
	public boolean checkUserEmailDuplicate(String email);
	public User getUserInfoByUserId(String userId);
	public String insertUser(UserInfoRequestDto userInfo);
	public String changePassword(String userId, String password);
	public HashMap<String, Object> login(User user);
	public HashMap<String, Object> findUserId(User user);
	public HashMap<String, Object> findUserPw(User user);
	

}
