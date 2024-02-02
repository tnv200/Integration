package com.fable.weatherall.Controllers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fable.weatherall.Admin_User_Entities.ApiKeyUrl;
import com.fable.weatherall.Repos.ApiKeyUrlRepo;
import com.fable.weatherall.Services.AdminService;

@Controller
public class Weather_Home_ApiRestController {
	
//	    @Value("${openweathermap.api.key}")
//	    private String apiKey;
//
//	    @Value("${openweathermap.api.url}")
//	    private String apiUrl;
	
	      //These values should be stored in database to change them dynamically because with code the values remain same even after changing with setters.
	       
	      @Autowired
	      private ApiKeyUrlRepo apikeyurlrepo;
	      
	     
	     
//	      @Autowired
//          private AdminService adminService;
	      
//	      Map<String, String> api = adminService.getApiKeyUrl();
	      
//	     String apiKey = apikeyurlrepo.findApikeyById(1);
//	     String apiUrl = apikeyurlrepo.findApiurlById(1);
	      
//	      @Autowired
//	      private ApiKeyUrlRepo apikeyurl;
//	
	
//         private String apiKey="8c8f2a026dd44c7ee20c5a1a657bd2fa";
//         private String apiUrl="https://api.openweathermap.org/data/2.5/weather"; 
         


//	    public String getApiKey() {
//			return apiKey;
//		}
//
//		public void setApiKey(String apiKey) {
//			this.apiKey = apiKey;
//		}
//
//		public String getApiUrl() {
//			return apiUrl;
//		}
//
//		public void setApiUrl(String apiUrl) {
//			this.apiUrl = apiUrl;
//		}
//
		@GetMapping("/api/config")
		@ResponseBody 
	    public Map<String, String> getApiConfig() {
			
			 ApiKeyUrl a=apikeyurlrepo.findById(1);
			
			 Map<String, String> config = new HashMap<>();
			 
		     config.put("apiKey", a.getApikey());
		     config.put("apiUrl", a.getApiurl());
			
	        return config;
	    }
	
		@GetMapping("/api/view")
	    public String viewApiKeyUrl(Model model) {
	    	
			 ApiKeyUrl a=apikeyurlrepo.findById(1);
			
	        model.addAttribute("apiKey", a.getApikey());
	        model.addAttribute("apiUrl", a.getApiurl());
	        
	        return "map-google";
	    }
		
//		@GetMapping("/apiku")
//		public void apiku() {
//			//System.out.println(apikeyurlrepo.findApikeyById(1));
//			ApiKeyUrl a= apikeyurlrepo.findById(1);
//			System.out.println(a.getApikey());
//		}
		

}
