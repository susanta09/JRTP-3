package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.request.LoginRequest;
import com.example.request.UserEmailDetails;
import com.example.request.UserReuest;
@Service
public interface UserService {
	public UserEmailDetails saveUser(UserReuest reuest);
	public boolean updatePasswordAndStatus(LoginRequest loginRequest);
	public String checkLoginStatusByEmailAndPassword(LoginRequest loginRequest);
	public User checkIdByEmail(LoginRequest request);
}
