package com.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT COUNT(u) FROM User u WHERE u.email=:email")
	long getNumberOfEmail(String email);
	@Query("SELECT u.password FROM User u WHERE u.password=:pass")
	String getPassword(String pass);
	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.password=:npass , u.status=:status WHERE u.email=:email And u.password=:tpass")
	void updatePasswordAndStatus(String npass,String status,String email,String tpass);
	
	
	@Query("SELECT u.status FROM User u WHERE u.email=:email and u.password=:pass")
	String getStatusByEmailAndPassword(String email,String pass);
	
	@Query("FROM User u WHERE u.email=:email")
	User getEmailByMail(String email);
	

}
