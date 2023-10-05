package com.example.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.repository.CityRepository;
import com.example.repository.CountryRepository;
import com.example.repository.StateRepository;
import com.example.repository.UserRepository;
import com.example.request.LoginRequest;
import com.example.request.UserEmailDetails;
import com.example.request.UserReuest;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CityRepository cityRepository;
	@Override
	public UserEmailDetails saveUser(UserReuest reuest)
	{
		User user=new User();
		
		BeanUtils.copyProperties(reuest, user);
		Integer min=100000,max=999999;
		Integer pass=min + (int)(Math.random() * ((max - min) + 1));
		System.out.println(pass);
		user.setPassword(String.valueOf(pass));
		
		String cname=countryRepository.findById(Integer.parseInt(reuest.getCountryName()))
				.get().getCountryName();
		user.setCountryName(cname);
		
		String sname=stateRepository.findById(Integer.parseInt(reuest.getStateName())).get().getStateName();
		user.setStateName(sname);
		
		String ciname=cityRepository.findById(Integer.parseInt(reuest.getCityName())).get().getCityName();
		user.setCityName(ciname);
		
		user.setStatus("locked");
		User u=userRepository.save(user);
		
		UserEmailDetails emailDetails=new UserEmailDetails();
		BeanUtils.copyProperties(u, emailDetails);
		return emailDetails;
	}
	@Override
	public boolean updatePasswordAndStatus(LoginRequest loginRequest)
	{
		try {
			userRepository.updatePasswordAndStatus(loginRequest.getConPassword(),
					"unlocked", loginRequest.getEmail(),loginRequest.getPassword());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	@Override
	public String checkLoginStatusByEmailAndPassword(LoginRequest loginRequest)
	{
		String email=loginRequest.getEmail();
		String pass=loginRequest.getPassword();
		String status=loginRequest.getStatus();
		System.out.println(email+" "+pass+" "+status);
		String result=userRepository.getStatusByEmailAndPassword(email,pass);
		if(result!=null)
		{
			if(result.equals(status))
			{
				return "success";
			}
			else {
				return "locked";
			}
		}else {
			return "invalid";
		}
		
	}
	@Override
	public User checkIdByEmail(LoginRequest request) {
		return userRepository.getEmailByMail(request.getEmail());
		
	}
}
