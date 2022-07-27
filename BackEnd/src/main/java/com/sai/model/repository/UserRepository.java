package com.sai.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.model.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
