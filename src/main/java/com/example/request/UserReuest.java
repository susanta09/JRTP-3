package com.example.request;

import java.time.LocalDate;

import lombok.Data;
@Data
public class UserReuest {
	private String firstName;
	private String lastNama;
	private String email;
	private String phNo;
	private LocalDate dob;
	private String gender;
	private String countryName;
	private String stateName;
	private String cityName;
}
