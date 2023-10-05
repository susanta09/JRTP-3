package com.example.controller;


import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.emailHandl.EmailUtils;
import com.example.entity.User;
import com.example.repository.CityRepository;
import com.example.repository.CountryRepository;
import com.example.repository.StateRepository;
import com.example.repository.UserRepository;
import com.example.request.LoginRequest;
import com.example.request.UserEmailDetails;
import com.example.request.UserReuest;
import com.example.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
@Controller
public class UserController {
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private EmailUtils emailUtils;
	
	@GetMapping("/")
	public String loginForm(Model model)
	{
		LoginRequest request=new LoginRequest();
		model.addAttribute("request", request);
		return "login";
	}
	@PostMapping("/log")
	public String login(LoginRequest loginRequest,Model model,RedirectAttributes redirectAttributes)
	{
		loginRequest.setStatus("unlocked");
		//success-->email and password and status(input and output are same as unlocked) are valid
		//locked--->email and password are valid but status(input is unlocked but output is locked) is not valid
		//invalid-->email and password are invalid means output is null
		System.out.println("login "+loginRequest);
		
		String result=userService.checkLoginStatusByEmailAndPassword(loginRequest);
		if(result.equals("success"))
		{
			model.addAttribute("msg", "Welcome to Ashok IT….");
			return"loginSuccess";
		}else if (result.equals("locked")) {
			redirectAttributes.addFlashAttribute("msgW", "Your Account Is Locked....");
			return "redirect:/";
		}else{
			redirectAttributes.addFlashAttribute("msgR", "Invalid Credentials...");
			return "redirect:/";
		}
	}
	
	
	@GetMapping("/reg")
	public String showRegistration(Model model)
	{
		UserReuest reuest=new UserReuest();
		model.addAttribute("request", reuest);
	    List<Object[]>list=countryRepository.getAllCountry();
		model.addAttribute("countryList", list);
		return"regf";
	}

	@GetMapping("/getStates")
	public @ResponseBody String getStates(@RequestParam Integer countryId)
	{
		String json = null;
		List<Object[]> list = countryRepository.getAllState(countryId);
		try {
			json = new ObjectMapper().writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@GetMapping("/getCities")
	public @ResponseBody String getCities(@RequestParam Integer stateId)
	{
		String json = null;
		List<Object[]> list = stateRepository.getAllCity(stateId);
		try {
			json = new ObjectMapper().writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	@PostMapping("/checkEmail")
	public @ResponseBody String checkEmail(@RequestParam String email)
	{
		System.out.println(email);
		long eNu=userRepository.getNumberOfEmail(email);
		System.out.println(eNu);
		
		
		String json = null;
		try {
			json = new ObjectMapper().writeValueAsString(eNu);
		} catch (JsonProcessingException e) 
		{ e.printStackTrace(); }
		 
		return json;
	}
	
	@PostMapping("/save")
	public String getDataFromReg(UserReuest reuest,Model model,RedirectAttributes redirectAttributes) {
		System.out.println(reuest);
		UserEmailDetails user = userService.saveUser(reuest);
		System.out.println(user);
		if(user!=null)
		{
			redirectAttributes.addFlashAttribute("msg", "Plese check your mail...");
			emailUtils.sendMail(user);
			return "redirect:/reg";
		}
		else {
			return null;
		}
	}
	@GetMapping("/loginForm/{email}")
	public String createPassword(@PathVariable("email") String email,Model model)
	{
		//model.addAttribute("msg", "Create login page");
		System.out.println(email);
		LoginRequest loginRequest=new LoginRequest();
		loginRequest.setEmail(email);
		model.addAttribute("loginRequest", loginRequest);
		return"unlockAccount";
	}
	
	@PostMapping("/loginForm/checkPassword")
	public @ResponseBody String getTemPassword(@RequestParam String pass)
	{
		System.out.println("getTemPassword "+pass);
		String json = null;
		String password = userRepository.getPassword(pass);
		try {
			json = new ObjectMapper().writeValueAsString(password);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@PostMapping("/createPassword")
	public String createPassword(LoginRequest loginRequest,Model model)
	{
		System.out.println("ramatttt");
		System.out.println(loginRequest);
		boolean b=userService.updatePasswordAndStatus(loginRequest);
		model.addAttribute("msg", "Account unlocked, please proceed with login..");
		return "unlockedSuccess";
	}
	@GetMapping("/forgotPassForm")
	public String forgotPassForm(Model model)
	{
		LoginRequest request=new LoginRequest();
		model.addAttribute("request",request);
		return"forgotPassword";
	}
	@PostMapping("/getPasswordByemail")
	public String getPasswordByemail(LoginRequest loginRequest,RedirectAttributes redirectAttributes)
	{
		User user=userService.checkIdByEmail(loginRequest);
		if(user!=null)
		{
			UserEmailDetails emailDetails=new UserEmailDetails();
			BeanUtils.copyProperties(user, emailDetails);
			emailUtils.sendMail(emailDetails);
			redirectAttributes.addFlashAttribute("msgG", "Check your email for password..");
			return"redirect:/forgotPassForm";
		}else {
			redirectAttributes.addFlashAttribute("msgR", "User is not exits..");
			 return"redirect:/forgotPassForm";
		}
		
	}
	
	
	

}
