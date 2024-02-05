package com.fable.weatherall.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fable.weatherall.Admin_User_Entities.User;
import com.fable.weatherall.DTOs.LoginDTO;
import com.fable.weatherall.DTOs.UserDTO;
import com.fable.weatherall.DTOs.VerifyOtpDTO;
import com.fable.weatherall.Responses.LoginResponse;
import com.fable.weatherall.Services.UserService;

import jakarta.servlet.http.HttpSession;



@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping(path = "/save")
	public String saveUser(@RequestBody UserDTO userDTO) {
		//userDTO.setUserType("user");
		userDTO.setUserType(userDTO.getUserType());
		String id = userService.addUser(userDTO);
		return id;
	}
	
	//"Login Success" response in login page
	@PostMapping(path = "/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO,HttpSession session) {
		//System.out.println("Working");
		
		session.setAttribute("userEmail", loginDTO.getEmail());
		LoginResponse loginResponse = userService.loginUser(loginDTO);
		return ResponseEntity.ok(loginResponse);
	}
	
	
	@PostMapping("/verify-otp")
	public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpDTO verifyOtpDTO) {
		System.out.println("verify otp");
	    boolean isOtpVerified = userService.verifyOtp(verifyOtpDTO.getEmail(), verifyOtpDTO.getOtp());
	    
	    if (isOtpVerified) {
	        return ResponseEntity.ok("OTP verified successfully");
	    } else {
	        return ResponseEntity.badRequest().body("Invalid OTP or OTP expired");
	    }
	}
	
	@PostMapping("/sendOtp/{email}")
	public String sendOtpToMail(@PathVariable("email") String email) {
		userService.sendOtpService(email);
		return "otp send successfully";
	}
	
	 @PostMapping("/authenticate")
	    public ResponseEntity<?> loginAdmin(@RequestBody User user,HttpSession session) {
	    	session.setAttribute("adminEmail", user.getEmail());
	    	LoginResponse loginResponse = userService.loginAdmin(user);
			return ResponseEntity.ok(loginResponse);
	    }
}
