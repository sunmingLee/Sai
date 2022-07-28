package com.sai.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sai.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	Optional<User> findByEmail(String email);
	
//	Optional<User> findByUser_name(String user_name);
	
	// findByUser_name이 왜 override 되지 않는지??
	
	
	

}
