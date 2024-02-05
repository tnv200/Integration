package com.fable.weatherall.Services;

import java.security.SecureRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service; // Added this import


import com.fable.weatherall.Admin_User_Entities.User;
import com.fable.weatherall.DTOs.LoginDTO;
import com.fable.weatherall.DTOs.UserDTO;
import com.fable.weatherall.Repos.UserRepo;
import com.fable.weatherall.Responses.LoginResponse;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service // Added annotation to indicate that this is a service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JavaMailSender javaMailSender;

    @Transactional
   
    public String addUserinAdmin(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setUserType(userDTO.getUserType());
       
//        user.setConfirmpassword(passwordEncoder.encode(userDTO.getConfirmpassword()));
//        user.setUserType(userDTO.getUserType());

        userRepo.save(user);
        

        return user.getUsername();
    }
    
    
    public String addUser(UserDTO userDTO) {
    	 User user = userRepo.findByEmail(userDTO.getEmail());
         if (user == null) {
        	 user = new User();  // Add this line to create a new User object
         }
         
         if(!user.getOtp().equals(userDTO.getOtp()))
         	return "Otp not matched";
    	
        //User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setConfirmpassword(passwordEncoder.encode(userDTO.getConfirmpassword()));
        user.setUserType(userDTO.getUserType());
        user.setOtp(userDTO.getOtp());
        userRepo.save(user);
        return user.getUsername();
    }
    
    
    
    
    public List<String> addMultipleUsers(List<UserDTO> userDTOList) {
        List<String> addedUsernames = new ArrayList<>();
        for (UserDTO userDTO : userDTOList) {
            String username = addUser(userDTO);
            addedUsernames.add(username);
        }
        return addedUsernames;
    }
    
    
    public String updateUser(int userId, UserDTO userDTO) {
        Optional<User> optionalUser = userRepo.findById(userId);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            
            // Update user information
            existingUser.setUsername(userDTO.getUsername());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            userRepo.save(existingUser);

            return existingUser.getUsername();
        } else {
            return "User not found";
        }
        }
        
        public void sendOtpService(String email) {
    		String otp=generateOtp();
    		if (otp == null) {
                throw new RuntimeException("Failed to generate OTP");
            }
    		User user = userRepo.findByEmail(email);
    		if (user==null){
    			 user=new User();
    		}
    		user.setEmail(email);
    		//System.out.println(user.getEmail());
    		user.setOtp(otp);
    		userRepo.save(user);
    		try {
    			sendOtpToMail(email,otp);
    			
    		}catch(MessagingException e) {
    			throw new RuntimeException("unable to send otp");
    		}
    		
    	}
    	public String generateOtp() {
    		SecureRandom random=new SecureRandom();
    		int otp= 100000 + random.nextInt(900000);
    		return String.valueOf(otp);
    	}
    	
    	private void sendOtpToMail(String email,String otp) throws MessagingException {
    		MimeMessage mimeMessage=javaMailSender.createMimeMessage();
    		MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage);
    		mimeMessageHelper.setTo(email);
    		mimeMessageHelper.setSubject("Your OTP is:");
    		mimeMessageHelper.setText(otp);
    		javaMailSender.send(mimeMessage);
    				
    	}


    	public boolean verifyOtp(String email, String otp) {
    		 User user = userRepo.findByEmail(email);
    		 if (user.getOtp() .equals(otp))
    		 {
    			 return true;
    		 }
    		return false;
    	}

    	
    	public int resetPassword(User user) {
    		User user1 = userRepo.findByEmail(user.getEmail());
    		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
    		if (user1==null) {
    			return 1;
    		}
    		 if (user1.getOtp() .equals(user.getOtp())){
    			 String password=encoder.encode(user.getPassword());
    			 user1.setPassword(password);
    			 userRepo.save(user1);
    			 return 2;
    		 }
    		 return 3;
    		 
    	}
    
    
    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public LoginResponse loginUser(LoginDTO loginDTO) {
        User user1 = userRepo.findByEmail(loginDTO.getEmail());
        if (user1 != null && user1.getUserType().equals("user")) {
            String password = loginDTO.getPassword();
            String encodedPassword = user1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<User> user = userRepo.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                if (user.isPresent()) {
                    return new LoginResponse("Login Success", true); // Fixed syntax
                } else {
                    return new LoginResponse("Login Failed", false);
                }
            } else {
                return new LoginResponse("Password Not Match", false); // Fixed typo
            }
        } else {
            return new LoginResponse("Email not exists", false);
        }
        
    }
    public LoginResponse loginAdmin(User user) {
		User user2 = userRepo.findByEmail(user.getEmail());
        if (user2 != null && user2.getUserType().equals("admin")) {
            String password = user.getPassword();
            String encodedPassword = user2.getPassword();
//            System.out.println(password);
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<User> adm = userRepo.findOneByEmailAndPassword(user.getEmail(), encodedPassword);
                if (adm.isPresent()){
                    return new LoginResponse("Login Success", true); // Fixed syntax
                } else {
                    return new LoginResponse("Login Failed", false);
                }
            } else {
                return new LoginResponse("Password Not Match", false); // Fixed typo
            }
        } else {
            return new LoginResponse("Email not exists", false);
        }
	}
    
    @Transactional
    public void deleteUserById(int userId) {
        userRepo.deleteByUserid(userId);
    }
    
    
//    public String editUserProfile(String userId,UserDTO userDTO) {
//    	Optional<User> optionalUser = userRepo.findById(userId);
//
//        if (optionalUser.isPresent()) {
//            User existingUser = optionalUser.get();
//            
//            // Update user information
//            existingUser.setUsername(userDTO.getUsername());
//            existingUser.setEmail(userDTO.getEmail());
//            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//
//            userRepo.save(existingUser);
//
//            return "Profile updated successfully";
//        } else {
//            return "User not found";
//        }
//    }
//    
    
}
