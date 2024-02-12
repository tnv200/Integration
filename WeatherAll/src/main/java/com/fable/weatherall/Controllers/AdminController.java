package com.fable.weatherall.Controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fable.weatherall.ClothEntites.ClothingItem;
import com.fable.weatherall.ClothEntites.ClothingRecommendation;
import com.fable.weatherall.ClothEntites.ClothingType;
import com.fable.weatherall.ClothEntites.WeatherDescription;
import com.fable.weatherall.DTOs.UserDTO;
import com.fable.weatherall.FoodEntites.Food;
import com.fable.weatherall.FoodEntites.FoodTemperatureMap;
import com.fable.weatherall.OutEntities.Activity;
import com.fable.weatherall.OutEntities.ActivityRecommendation;
import com.fable.weatherall.OutEntities.RecommendationLevelOut;
import com.fable.weatherall.OutEntities.WeatherDescriptionOut;
import com.fable.weatherall.Repos.FoodRepo;
import com.fable.weatherall.Repos.FoodTempMapRepo;
import com.fable.weatherall.Repos.OutActivityRepo;
import com.fable.weatherall.Repos.OutRepo;
import com.fable.weatherall.Repos.ClothItemRepo;
import com.fable.weatherall.Repos.ClothRepo;
import com.fable.weatherall.Repos.ClothRepo.ClothingRecommendationDetailsProjection;
import com.fable.weatherall.Repos.OutRepo.ActivityRecommendationDetailsProjection;
import com.fable.weatherall.Repos.TravelNameRepo;
import com.fable.weatherall.Repos.TravelRepo;
import com.fable.weatherall.Repos.TravelRepo.TravelRecommendationDetailsProjection;
import com.fable.weatherall.Services.AdminService;
import com.fable.weatherall.TravelEntities.RecommendationLevelTravel;
import com.fable.weatherall.TravelEntities.TravelNames;
import com.fable.weatherall.TravelEntities.TravelRecommendation;
import com.fable.weatherall.TravelEntities.WeatherDescriptionTravel;

import jakarta.transaction.Transactional;

@Controller
public class AdminController {
	
	 @Autowired
     private AdminService adminService;
    
     @Autowired
     private FoodRepo foodrepo;
     
     
     @Autowired
     private FoodTempMapRepo ftmrepo;
     
     @Autowired
     private ClothRepo clothrepo;
     
     @Autowired
     private ClothItemRepo cirepo;
     
     
     @Autowired
     private OutActivityRepo outactrepo;
     
     @Autowired
     private OutRepo outrepo;
     
     @Autowired
     private TravelNameRepo trnmrepo;
     
     @Autowired
     private TravelRepo travelrepo;
     

	 @GetMapping("/getAllfood")
	 public String allFoods(Model model){
   	 
   	 List<Food> foods=foodrepo.findAll();
   	 
   	 List<FoodTemperatureMap> ftm=ftmrepo.findAll();
   	 
   	 model.addAttribute("ftm", ftm);

   	 model.addAttribute("foods", foods);
   	 
   	 return "foodtable";
   	 
    }
	 
	 @PostMapping("/addFoods")
	 public String addFoods(@ModelAttribute("foodAdd") Food food) 
	 
	 {
//		 Food food = new Food();
//		 
//		 food.setFoodName(foodname);
//		 food.setState(state);
		 
		 foodrepo.save(food);
//		 ftmrepo.save(ftempMapAdd);
		 
		 return "redirect:/getAllfood";
	 }
	 
	 
	 @PostMapping("/deleteFoods")
	 public String deleteFoods(  @RequestParam("foodId") int foodId ) {
		 
		 adminService.deleteFoodService(foodId);
		
		 
		 return "redirect:/getAllfood";
		 
	 }
	 
	 
//	 @GetMapping("/getAllTempMap")
//	 public String allTempMap(Model model){
//   	 
//		 List<FoodTemperatureMap> ftm=ftmrepo.findAll();
//		 
//	   	 model.addAttribute("ftm", ftm);
//
//
//		 return "";
//    }
	 
	 
//	 @PostMapping("/addTempMap/{foodid}/{categoryid}")
//	 public void addTempMap ( @PathVariable("foodid")Integer foodid, @PathVariable("categoryid") Integer categoryid)
//	 {
//		 FoodTemperatureMap ftm = new FoodTemperatureMap();
//		 
//		 ftm.setFoodId(foodid);
//		 ftm.setCategoryId(categoryid);
//		 
//		 ftmrepo.save(ftm);
//		 
//	 }
	 @PostMapping("/addTempMap")
	 public String addTempMap ( @ModelAttribute("ftempMapAdd") FoodTemperatureMap ftempMapAdd)
	 {
		 ftmrepo.save(ftempMapAdd);
		 
		 return "redirect:/getAllfood";
		 
	 }
	 
	 @PostMapping("/delTempMap")
	 public String delTempMap(  @RequestParam("foodTemperatureId") int foodTemperatureId ) {

		 
//		 FoodTemperatureMap food = new FoodTemperatureMap();
//		 
//		 food.setFoodName(foodname);
//		 food.setState(state);
		 
		 adminService.delTempMap(foodTemperatureId);
		 
		 return "redirect:/getAllfood";

		 
	 }
	 
	 
	 @GetMapping("/getClothItems")
	 public String allClothItems(Model model){
   	 
		 List<ClothingItem> cis = cirepo.findAll();
		 List<ClothingRecommendationDetailsProjection> crs=clothrepo.findAllClothingRecommendationsWithDetailsSortedByClothingRecommendationId();
		 
		 model.addAttribute("cis", cis);
		 model.addAttribute("crs", crs);

		 return "clothtable";
    }
	 
	 
	 @PostMapping("/addClothItem")
	 public String addClothItems ( @ModelAttribute("clothadd") ClothingItem clothadd)
	 {
		 
		 cirepo.save(clothadd);
		 
		 return "redirect:/getClothItems";
		 
	 }
	 
	 @PostMapping("/delClothItem")
	 public String delClothItem(   @RequestParam("clothingItemId") int clothingItemId) {
		 
		 adminService.delClothItem(clothingItemId);
		 
		 return "redirect:/getClothItems";
		 
	 }

	 @PostMapping("/addClothReco")
	 public String addClothReco ( @RequestParam("clothingItemId")int clothingItemId,
			                      @RequestParam("clothingTypeId")int clothingTypeId,
			                      @RequestParam("weatherDescriptionId")int weatherDescriptionId   )
	 {
		 
		 Integer clothitem_id = Integer.valueOf(clothingItemId);
		 Integer clothtype_id = Integer.valueOf(clothingTypeId);
		 Integer wthr_id = Integer.valueOf(weatherDescriptionId);
		 
		 
		 ClothingRecommendation cr = new ClothingRecommendation();
		 
		 ClothingItem ci = new ClothingItem();
		 
		 ClothingType ct = new ClothingType();
		 
		 WeatherDescription wd = new WeatherDescription();
		 
		 ci.setClothingItemId(clothitem_id);
		 
		 ct.setClothingTypeId(clothtype_id);
		 
		 wd.setWeatherDescriptionId(wthr_id);
		 
		 cr.setClothingItemId(ci);
		 
		 cr.setClothingTypeId(ct);

		 cr.setWeatherDescriptionId(wd);

		 
		 clothrepo.save(cr);
		 
		 return "redirect:/getClothItems";
		 
	 }
	 
	 
	 @PostMapping("/delClothReco")
	 public String delClothReco(  @RequestParam("clothingRecoId") int clothingRecoId) {
		 
		 adminService.delClothReco(clothingRecoId);
		 
		 return "redirect:/getClothItems";
	 }
	 
	 
	 
	 //After adding record in recommendations map table and main table first should delete id from map table 
	 
	 @GetMapping("/getOutActs")
	 public String allOutActs(Model model){
   	 
		 List<Activity> acts = outactrepo.findAll();
		 List<ActivityRecommendationDetailsProjection> ors = outrepo.findAllActivityRecommendationsWithDetailsSortedByRecommendationId();
		 
		 model.addAttribute("acts", acts);
		 model.addAttribute("ors", ors);
		 
		 return "activetable";
     }
	 
	 @PostMapping("/addOutActs")
	 public String addOutActs ( @ModelAttribute("actname")Activity actname)
	 {
		 
		 
		 outactrepo.save(actname);
		 return "redirect:/getOutActs";
		 
	 }
	 
	 @PostMapping("/delOutActs")
	 public String delOutActs(  @RequestParam("activityid") int activityid ) {

		 
		 adminService.delOutActs(activityid);
		 return "redirect:/getOutActs";
	 }
	 
	 
		
//		 @GetMapping("/getOutReco") public
//		 List<ActivityRecommendationDetailsProjection> allOutReco(){
//		 
//		  List<ActivityRecommendationDetailsProjection> ors = outrepo.findAllActivityRecommendationsWithDetailsSortedByRecommendationId();
//		  
//		  return ors; 
//		  }
		 
	 
	 @PostMapping("/addOutReco")
	 public String addOutReco (   @RequestParam("activityid")int activityid,
			                    @RequestParam("recommendationLevelId")int recommendationLevelId,
			                    @RequestParam("weatherDescriptionId")int weatherDescriptionId  )
	 {
		 Integer activity_id = Integer.valueOf(activityid);
		 Integer level_id = Integer.valueOf(recommendationLevelId);
		 Integer wthr_id = Integer.valueOf(weatherDescriptionId);
		 
		 ActivityRecommendation ar = new ActivityRecommendation();
		 
		 Activity ac = new Activity();
		 
		 RecommendationLevelOut lvl = new RecommendationLevelOut();
		 
		 WeatherDescriptionOut wd = new WeatherDescriptionOut();
		 
		 ac.setActivityid(activity_id);
		 
		 lvl.setRecommendationLevelId(level_id);
		 
		 wd.setWeatherDescriptionId(wthr_id);
		 
		 ar.setActivity(ac);
		 
		 ar.setRecommendationLevel(lvl);

		 ar.setWeatherDescription(wd);

		 
		 outrepo.save(ar);
		 return "redirect:/getOutActs";
		 
	 }
	 
	 @PostMapping("/delOutReco")
	 public String delOutReco(  @RequestParam("outreid") int outreid ) {
		 
		 adminService.delOutReco(outreid);
		 return "redirect:/getOutActs";
		 
	 }
	 
	 @GetMapping("/getTrNms")
	 public String  allgetTrNms(Model model){
   	 
		 List<TravelNames> trnms = trnmrepo.findAll();
		 List<TravelRecommendationDetailsProjection> trs = travelrepo.findAllTravelRecommendationsWithDetailsSortedByRecommendationId();

		 model.addAttribute("trnms", trnms);
		 model.addAttribute("trs", trs);

		 return "traveltable";
     }
	 
	 @PostMapping("/addTrNms")
	 public String addTrNms ( @ModelAttribute("traname")TravelNames trnms)
	 {
		 
		 trnmrepo.save(trnms);
		 
		 return "redirect:/getTrNms";
		 
	 }
	 
	 @PostMapping("/delTrNms")
	 public String delTrNms(@RequestParam("travelid") int travelid ) {

		 
		 adminService.delTrNms(travelid);
		 return "redirect:/getTrNms";
		 
	 }
	 
//	 @GetMapping("/getTraReco")
//	 public List<TravelRecommendationDetailsProjection> getTraReco(){
//   	 
//		 List<TravelRecommendationDetailsProjection> trs = travelrepo.findAllTravelRecommendationsWithDetailsSortedByRecommendationId();
//
//		 return trs;
//    }
	 
	 @PostMapping("/addTraReco")
	 public String addTraReco ( @RequestParam("travelid")int travelid,
			                   @RequestParam("recommendationLevelId")int recommendationLevelId,
			                   @RequestParam("weatherDescriptionId")int weatherDescriptionId  )
	 {
		 

		 Integer travel_id = Integer.valueOf(travelid);
		 Integer level_id = Integer.valueOf(recommendationLevelId);
		 Integer wthr_id = Integer.valueOf(weatherDescriptionId);
		 
		 
		 TravelRecommendation tr = new TravelRecommendation();
		 
		 TravelNames tn = new TravelNames();
		 
		 RecommendationLevelTravel lvl = new RecommendationLevelTravel();
		 
		 WeatherDescriptionTravel wd = new WeatherDescriptionTravel();
		 
		 tn.setTravelid(travel_id);
		 
		 lvl.setRecommendationLevelId(level_id);
		 
		 wd.setWeatherDescriptionId(wthr_id);
		 
		 tr.setTravelnames(tn);
		 
		 tr.setRecommendationLevel(lvl);

		 tr.setWeatherDescription(wd);

		 
		 travelrepo.save(tr);
		 
		 return "redirect:/getTrNms";

	 }
	 
	 @PostMapping("/delTraReco")
	 public String delTraReco(  @RequestParam("outreid") int outreid ) {
		 
		 adminService.delTraReco(outreid);
		 
		 return "redirect:/getTrNms";

		 
	 }
//	 @PostMapping("/getf/{foodid}")
//	 public List<FoodTemperatureMap> getf(@PathVariable("foodid") Integer foodid){
//		 
//		 List<FoodTemperatureMap> a=ftmrepo.findByFoodId(foodid); 
//           
//		return a;
//	 }
		 
//		 if(foodrepo.findByFoodNameAndState(foodname, state )!= null)
//		 {
//
//		 foodrepo.deleteByFoodNameAndState(foodname, state);
//		 
//		 }
//		 
//		 else
//			 System.out.println("Record not present");
//		 
//	 }

}
