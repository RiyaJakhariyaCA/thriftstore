package com.douglas.thriftstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.douglas.thriftstore.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUserid(String userid);
	
	 User findByEmail(String email);
}
