package com.fable.weatherall.Services;

import java.util.List;
import java.util.Optional;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fable.weatherall.Admin_User_Entities.Admin;
import com.fable.weatherall.Admin_User_Entities.User;
import com.fable.weatherall.DTOs.AdminDTO;
import com.fable.weatherall.DTOs.LoginDTO;
import com.fable.weatherall.Repos.AdminRepo;
import com.fable.weatherall.Repos.UserRepo;
import com.fable.weatherall.Responses.LoginResponse;
import com.fable.weatherall.Responses.UserAddResponse;

import jakarta.transaction.Transactional;


@Service
public class AdminService {
	
//	private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


//    public Optional<User> findByUserId(int userid) {
//        return userRepo.findByUserId(userid);
//    }

    
    public boolean authenticateAdmin(Admin admin, String password) {
        return admin != null && passwordEncoder.matches(password, admin.getPassword());
    }
    
    @Transactional
    public void deleteUserById(int userId) {
        userRepo.deleteByUserid(userId);
    }
    
//    public Admin findAdminByEmail(String email) {
//        return adminRepo.findByUsername(email).orElse(null);
//    }
       
    
//    public List<User> getAllUsers() {
//        return userRepo.findAll();
//    }
    
    public void registerAdmin(AdminDTO adminDTO) {
        Admin admin = new Admin();
        admin.setUsername(adminDTO.getUsername());
        admin.setEmail(adminDTO.getEmail());
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        admin.setConfirmpassword(passwordEncoder.encode(adminDTO.getConfirmpassword()));
        adminRepo.save(admin);
    }


	
	public LoginResponse loginAdmin(Admin admin) {
		Admin admin1 = adminRepo.findByEmail(admin.getEmail());
        if (admin1 != null) {
            String password = admin.getPassword();
            String encodedPassword = admin1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<Admin> adm = adminRepo.findOneByEmailAndPassword(admin.getEmail(), encodedPassword);
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
	
//	public UserAddResponse useradd(User user) {
//		User user1 = userRepo.findByEmail(user.getEmail());
//        if (user1 != null) {
//            String password = user.getPassword();
//            String encodedPassword = user1.getPassword();
//            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
//            if (isPwdRight) {
//                Optional<User> usr = userRepo.findOneByEmailAndPassword(user.getEmail(), encodedPassword);
//                if (usr.isPresent()){
//                    return new UserAddResponse("UserAdd Success", true); // Fixed syntax
//                } else {
//                    return new UserAddResponse("UserAdd Failed", false);
//                }
//            } else {
//                return new UserAddResponse("Password Not Match", false); // Fixed typo
//            }
//        } else {
//            return new UserAddResponse("Email not exists", false);
//        }
//	}
	
	

}



