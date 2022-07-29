package com.sai.model.repository.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sai.model.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	Optional<User> findByEmail(String email);
	Optional<User> findByUserId(String userId);
	Optional<User> findByUserName(String userName);
	boolean existsByUserId(String userId);
	boolean existsByEmail(String email);
	

	

}
