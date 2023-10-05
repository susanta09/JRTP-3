package com.example.request;

import lombok.Data;

@Data
public class LoginRequest {
	private String email;
	private String password;
	private String newPassword;
	private String conPassword;
	private String status;

}
