package com.bridgelabz.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.springboot.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByEmail(String email);
}
