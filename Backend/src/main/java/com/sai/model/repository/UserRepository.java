package com.sai.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sai.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByEmail(String email);

	Optional<User> findByUserIdOrEmail(String userId, String email);

	List<User> findByUserIdIn(List<String> userId);

	Optional<User> findByUserId(String userId);

	Optional<User> findByUserName(String userName);

	boolean existsByUserId(String userId);

	boolean existsByEmail(String email);

}
