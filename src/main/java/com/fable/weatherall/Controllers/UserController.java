package com.fable.weatherall.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fable.weatherall.Admin_User_Entities.User;
import com.fable.weatherall.DTOs.LoginDTO;
import com.fable.weatherall.DTOs.UserDTO;
import com.fable.weatherall.DTOs.VerifyOtpDTO;
import com.fable.weatherall.Repos.UserRepo;
import com.fable.weatherall.Responses.LoginResponse;
import com.fable.weatherall.Services.UserService;

import jakarta.servlet.http.HttpSession;



@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private UserRepo userRepo;
	
	

	
	
	@PostMapping(path = "/save")
	public String saveUser(@RequestBody UserDTO userDTO) {
		userDTO.setUserType("user");
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
	 
//	 @PostMapping("/edit-profile")
//	    public ResponseEntity<String> editProfile(@RequestBody UserDTO userDTO) {
//	        String username = userService.updateUserProfile(userDTO);
//	        return ResponseEntity.ok("Profile updated successfully for user: " + username);
//	    }
	 
//	
//
//	 @PostMapping(value = "/edit-profile", consumes = MediaType.APPLICATION_JSON_VALUE)
//	    public ResponseEntity<String> editProfile(@RequestBody UserDTO userDTO) {
//	        try {
//	            String username = userService.updateUserProfile(userDTO);
//	            return ResponseEntity.ok("Profile updated successfully for user: " + username);
//	        } catch (Exception e) {
//	            // Handle exceptions or return an appropriate response
//	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the profile.");
//	        }
//	    }
	 
//	 @PostMapping(value = "/edit-profile", consumes = MediaType.APPLICATION_JSON_VALUE)
//	    public String editProfile(@RequestBody UserDTO userDTO) {
//	        try {
//	            String username = userService.updateUserProfile(userDTO);
//	            return "redirect:/user_dashboard";
//	        } catch (Exception e) {
//	            // Handle exceptions or return an appropriate response
//	            return "/userprofile";
//	        }
//	    }
	 
	 @PostMapping(value = "/edit-profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<String> editProfile(@RequestBody UserDTO userDTO) {
	     try {
	         String username = userService.updateUserProfile(userDTO);
	         return ResponseEntity.ok("{\"url\": \"/user_dashboard\"}");
	     } catch (Exception e) {
	         // Handle exceptions or return an appropriate response
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"An error occurred while updating the profile.\"}");
	     }
	 }
	 

	    @GetMapping("/checkEmailExists/{email}")
	    public ResponseEntity<Map<String, Boolean>> checkEmailExists(@PathVariable String email) {
	        User user = userRepo.findByEmail(email);
	        Map<String, Boolean> response = new HashMap<>();
	        response.put("exists", user != null);
	        return ResponseEntity.ok(response);
	    }
	    
	    @PostMapping("/saveEmailAndOTP")
	    public ResponseEntity<String> saveEmailAndOTP(@RequestBody VerifyOtpDTO verifyOtpDTO) {
	        try {
	            userService.saveEmailAndOTP(verifyOtpDTO);
	            return ResponseEntity.ok("Email and OTP saved successfully!");
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Error saving email and OTP: " + e.getMessage());
	        }
	    }
	   

	 
	 

}
