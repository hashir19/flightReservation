package com.hashir.flightreservation.controllers;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hashir.flightreservation.entities.User;
import com.hashir.flightreservation.repos.UserRepository;
import com.hashir.flightreservation.services.SecurityService;

@Controller
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private SecurityService securityService;
	
	@RequestMapping("/showReg")
	public String showRegistrationPage() {
		LOGGER.info("Inside showRegistrationPage()");
		return "login/registerUser";
	}

	@RequestMapping(value="registerUser", method=RequestMethod.POST)
	public String register(@ModelAttribute("user")User user) {
		LOGGER.info("Inside register()"+user);
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
		return "login/login";
	}
	
	@RequestMapping("/showLogin")
	public String showLoginPage() {
		LOGGER.info("Inside showLoginPage()");

		return "login/login";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(@RequestParam(value="email",required=false)String email,@RequestParam("password")String password, ModelMap modelMap) {
		boolean loginResponse = securityService.login(email, password);
		LOGGER.info("Inside login() and the email is :"+email);		
		if(loginResponse) {
			return "findFlights";
		}else {
			modelMap.addAttribute("msg", "Invalid Username or Password. Please try again.");
		}
		return "login/login";
		
	}
}
