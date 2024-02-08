package com.fable.weatherall.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fable.weatherall.Admin_User_Entities.ApiKeyUrl;
import com.fable.weatherall.Admin_User_Entities.User;
import com.fable.weatherall.DTOs.AdminDTO;
import com.fable.weatherall.DTOs.LoginDTO;
import com.fable.weatherall.DTOs.UserDTO;
import com.fable.weatherall.FoodEntites.Food;
import com.fable.weatherall.FoodEntites.FoodTemperatureMap;
import com.fable.weatherall.Repos.ApiKeyUrlRepo;
import com.fable.weatherall.Repos.ClothItemRepo;
import com.fable.weatherall.Repos.ClothRepo;
import com.fable.weatherall.Repos.FoodRepo;
import com.fable.weatherall.Repos.FoodTempMapRepo;
import com.fable.weatherall.Repos.OutActivityRepo;
import com.fable.weatherall.Repos.OutRepo;
import com.fable.weatherall.Repos.TravelNameRepo;
import com.fable.weatherall.Repos.TravelRepo;
import com.fable.weatherall.Repos.UserRepo;
import com.fable.weatherall.Responses.LoginResponse;
import com.fable.weatherall.Responses.UserAddResponse;

import jakarta.transaction.Transactional;


@Service
public class AdminService {
	
//	private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);
//    @Autowired
//    private UserRepo userRepo;
    
    @Autowired
    private ApiKeyUrlRepo apikeyurlrepo;
    
    
    @Autowired
    private FoodRepo foodrepo;
    
    @Autowired
    private FoodTempMapRepo ftmrepo;
    
    @Autowired
    private ClothItemRepo cirepo;
    
    @Autowired
    private ClothRepo clothrepo;
   
    @Autowired
    private OutActivityRepo outactrepo;
    
    @Autowired
    private OutRepo outrepo;
    
    @Autowired
    private TravelNameRepo trnmrepo;
    
    @Autowired
    private TravelRepo travelrepo;
    
//    @Autowired
//    private PasswordEncoder passwordEncoder;


//    public Optional<User> findByUserId(int userid) {
//        return userRepo.findByUserId(userid);
//    }

    
//    public boolean authenticateAdmin(Admin admin, String password) {
//        return admin != null && passwordEncoder.matches(password, admin.getPassword());
//    }
    
    @Transactional
    public void deleteFoodService(Integer foodid) {
    	       
             foodrepo.deleteByFoodId(foodid);  

     }

    @Transactional
    public void delTempMap(Integer ftm) {
    	
//    	FoodTemperatureMap ftm_in = new FoodTemperatureMap ();

     ftmrepo.deleteByFoodTemperatureId(ftm);
    	
    }
    
    @Transactional
    public void delClothItem(Integer clothid) {
    	


     cirepo.deleteByClothingItemId(clothid);
    	
    }
    
    
    @Transactional
    public void delClothReco(Integer cloreid) {


    	clothrepo.deleteByClothingRecommendationId(cloreid);
    	
    }
    
    @Transactional
    public void delOutActs(Integer actid) {
    	
//    	FoodTemperatureMap ftm_in = new FoodTemperatureMap ();

    	outactrepo.deleteByActivityid(actid);
    	
    }
    
    
    @Transactional
    public void delOutReco(Integer outreid) {


    	outrepo.deleteByActivityRecommendationid(outreid);
    	
    }
    
    @Transactional
    public void delTrNms(Integer traid) {
    	
//    	FoodTemperatureMap ftm_in = new FoodTemperatureMap ();

    	trnmrepo.deleteByTravelid(traid);
    	
    }
    

    @Transactional
    public void delTraReco(Integer outreid) {
    	
    	travelrepo.deleteByTravelRecommendationid(outreid);
    }
    
    public Map<String, String> getApiKeyUrl()
    {
    	ApiKeyUrl a= apikeyurlrepo.findById(1);
    	
    	
    	 Map<String, String> api = new HashMap<>();
	       
    	 api.put("apiKey", a.getApikey());
	       
    	 api.put("apiUrl", a.getApiurl());
	        

    	return api;
    }
    
    
    public void update_ApiKeyUrl(ApiKeyUrl apikeyurl) {
    	
    	ApiKeyUrl apikeyurl_in = apikeyurlrepo.findById(1);
    	
//    	ApiKeyUrl apikeyurl_in = new ApiKeyUrl();
//    	apikeyurl_in.setApikey(apikeyurl.getApikey());
//    	apikeyurl_in.setApiurl(apikeyurl.getApiurl());
    	
    	apikeyurl_in.setApikey(apikeyurl.getApikey());
    	apikeyurl_in.setApiurl(apikeyurl.getApiurl());

    	apikeyurlrepo.save(apikeyurl_in);
        
//
//        return user.getUsername();
    }
    
    
    
    
//     public List<Food> allFoods(){
//    	 
//    	 List<Food> a=foodrepo.findAll();
//    	 
//    	 return a;
//    	 
//     }
    
//    @Transactional
//    public void 
//   
    
//    public Admin findAdminByEmail(String email) {
//        return adminRepo.findByUsername(email).orElse(null);
//    }
       
    
//    public List<User> getAllUsers() {
//        return userRepo.findAll();
//    }
    
//    public void registerAdmin(AdminDTO adminDTO) {
//        Admin admin = new Admin();
//        admin.setUsername(adminDTO.getUsername());
//        admin.setEmail(adminDTO.getEmail());
//        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
//        admin.setConfirmpassword(passwordEncoder.encode(adminDTO.getConfirmpassword()));
//        adminRepo.save(admin);
//    }


	
//	public LoginResponse loginAdmin(Admin admin) {
//		Admin admin1 = adminRepo.findByEmail(admin.getEmail());
//        if (admin1 != null) {
//            String password = admin.getPassword();
//            String encodedPassword = admin1.getPassword();
//            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
//            if (isPwdRight) {
//                Optional<Admin> adm = adminRepo.findOneByEmailAndPassword(admin.getEmail(), encodedPassword);
//                if (adm.isPresent()){
//                    return new LoginResponse("Login Success", true); // Fixed syntax
//                } else {
//                    return new LoginResponse("Login Failed", false);
//                }
//            } else {
//                return new LoginResponse("Password Not Match", false); // Fixed typo
//            }
//        } else {
//            return new LoginResponse("Email not exists", false);
//        }
//	}
	
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



