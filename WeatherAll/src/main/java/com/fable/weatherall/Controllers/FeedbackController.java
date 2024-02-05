package com.fable.weatherall.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fable.weatherall.Repos.UserRepo;
import com.fable.weatherall.Responses.Feedback;
import com.fable.weatherall.Services.EmailService;

import jakarta.servlet.http.HttpSession;

@RestController
public class FeedbackController {
	
	 @Autowired
	    private EmailService emailService;
	 
	 @Autowired
	    private UserRepo userRepo;

	    @PostMapping("/submit-feedback")
	    public Map<String, Object> submitFeedback(@RequestBody Feedback feedback,HttpSession session,Model model) {
	        Map<String, Object> response = new HashMap<>();
	        String email = (String) session.getAttribute("userEmail");
	        String name = userRepo.findUsernameByEmail(email);
	        
	       
	        List<String> user1 = new ArrayList<>();
	        List<String> user2 = new ArrayList<>();
	        user1.add(email);
	        user2.add(name);
	        model.addAttribute("user1", user1);
	        model.addAttribute("user2", user2);
	        

	        try {
	            // Send email with feedback suggestion
	            emailService.sendFeedbackEmail("weatherfable@gmail.com", "Feedback Suggestion", "from, \n"+"User: "+name+"\n"+"Email: "+ email+"\n\n"+ feedback.getSuggestion());

	            // Optionally, you can perform additional actions here (e.g., save feedback to a database)

	            response.put("success", true);
	            response.put("message", "Feedback submitted successfully!");
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.put("success", false);
	            response.put("message", "Error submitting feedback");
	        }

	        return response;
	    }
	    
  
}
