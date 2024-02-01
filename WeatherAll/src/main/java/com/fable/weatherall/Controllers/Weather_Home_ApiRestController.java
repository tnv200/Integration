package com.fable.weatherall.Controllers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class Weather_Home_ApiRestController {
	
//	    @Value("${openweathermap.api.key}")
//	    private String apiKey;
//
//	    @Value("${openweathermap.api.url}")
//	    private String apiUrl;
	
	      //These values should be stored in database to change them dynamically because with code the values remain same even after changing with setters.
	
         private String apiKey="8c8f2a026dd44c7ee20c5a1a657bd2fa";
         private String apiUrl="https://api.openweathermap.org/data/2.5/weather"; 
         


	    public String getApiKey() {
			return apiKey;
		}

		public void setApiKey(String apiKey) {
			this.apiKey = apiKey;
		}

		public String getApiUrl() {
			return apiUrl;
		}

		public void setApiUrl(String apiUrl) {
			this.apiUrl = apiUrl;
		}

		@GetMapping("/api/config")
		@ResponseBody 
	    public Map<String, String> getApiConfig() {
	    	
	        Map<String, String> config = new HashMap<>();
	        config.put("apiKey", apiKey);
	        config.put("apiUrl", apiUrl);
	        
	        return config;
	    }
		
		
		@GetMapping("/api/view")
	    public String viewApiKeyUrl(Model model) {
	    	
	        model.addAttribute("apiKey", apiKey);
	        model.addAttribute("apiUrl", apiUrl);
	        
	        return "map-google";
	    }
		

}
