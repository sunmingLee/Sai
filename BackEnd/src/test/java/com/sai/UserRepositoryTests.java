package com.sai;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sai.model.entity.User;
import com.sai.model.repository.UserRepository;

public class UserRepositoryTests extends SaiApplicationTests {
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void create() {
		User user = new User();
		
//		user.setUserId("testId123");
		user.setPassword("testPw123");
//		user.setUserName("Name");
		
		User newUser = userRepository.save(user);
	}
	
//	@Test
//	public void read() {
//		Optional<User> user = userRepository.findById("");
//		
//		user.ifPresent(selectUser -> {
//			System.out.println(selectUser.getUserName());
//			System.out.println(selectUser.getUserId());
//		});
		
		
//	}

	
}
