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
	 public String addFoods(@ModelAttribute("foodAdd") Food food ) 
	 
	 {
//		 Food food = new Food();
//		 
//		 food.setFoodName(foodname);
//		 food.setState(state);
		 
		 foodrepo.save(food);
		 
		 return "redirect:/getAllfood";
	 }
	 
	 
	 @PostMapping("/deleteFoods")
	 public String deleteFoods(  @RequestParam("foodId") int foodId) {
		 
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
	 
	 
	 @PostMapping("/addTempMap/{foodid}/{categoryid}")
	 public void addTempMap ( @PathVariable("foodid")Integer foodid, @PathVariable("categoryid") Integer categoryid)
	 {
		 FoodTemperatureMap ftm = new FoodTemperatureMap();
		 
		 ftm.setFoodId(foodid);
		 ftm.setCategoryId(categoryid);
		 
		 ftmrepo.save(ftm);
		 
	 }
	 
	 @PostMapping("/delTempMap/{ftmid}")
	 public void delTempMap(  @PathVariable("ftmid") Integer ftmid ) {

		 
//		 FoodTemperatureMap food = new FoodTemperatureMap();
//		 
//		 food.setFoodName(foodname);
//		 food.setState(state);
		 
		 adminService.delTempMap(ftmid);
		 
	 }
	 
	 
	 @GetMapping("/getClothItems")
	 public List<ClothingItem> allClothItems(){
   	 
		 List<ClothingItem> cis = cirepo.findAll();

		 return cis;
    }
	 
	 
	 @PostMapping("/addClothItem/{itemname}")
	 public void addClothItems ( @PathVariable("itemname")String itemname)
	 {
		 ClothingItem ci = new ClothingItem();
		 
		 ci.setItemName(itemname);
		 
		 cirepo.save(ci);
		 
	 }
	 
	 @PostMapping("/delClothItem/{clothid}")
	 public void delClothItem(  @PathVariable("clothid") Integer clothid ) {

		 
//		 FoodTemperatureMap food = new FoodTemperatureMap();
//		 
//		 food.setFoodName(foodname);
//		 food.setState(state);
		 
		 adminService.delClothItem(clothid);
		 
	 }
	
	
	 @GetMapping("/getClothReco")
	 public List<ClothingRecommendationDetailsProjection> allClothReco(){
   	 
		 List<ClothingRecommendationDetailsProjection> crs=clothrepo.findAllClothingRecommendationsWithDetailsSortedByClothingRecommendationId();

		 return crs;
    }
	 
	
	 @PostMapping("/addClothReco/{clothitem_id}/{clothtype_id}/{wthr_id}")
	 public void addClothReco ( @PathVariable("clothitem_id")Integer clothitem_id,
			                    @PathVariable("clothtype_id")Integer clothtype_id,
			                    @PathVariable("wthr_id")Integer wthr_id  )
	 {
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
		 
	 }
	 
	 
	 @PostMapping("/delClothReco/{cloreid}")
	 public void delClothReco(  @PathVariable("cloreid") Integer cloreid ) {
		 
		 adminService.delClothReco(cloreid);
		 
	 }
	 
	 @GetMapping("/getOutActs")
	 public List<Activity> allOutActs(){
   	 
		 List<Activity> acts = outactrepo.findAll();

		 return acts;
     }
	 
	 @PostMapping("/addOutActs/{actname}")
	 public void addOutActs ( @PathVariable("actname")String actname)
	 {
		 Activity a = new Activity();
		 
		 a.setActivityname(actname);
		 
		 outactrepo.save(a);
		 
	 }
	 
	 @PostMapping("/delOutActs/{actid}")
	 public void delOutActs(  @PathVariable("actid") Integer actid ) {

		 
		 adminService.delOutActs(actid);
		 
	 }
	 
	 
	 @GetMapping("/getOutReco")
	 public List<ActivityRecommendationDetailsProjection> allOutReco(){
   	 
		 List<ActivityRecommendationDetailsProjection> ors = outrepo.findAllActivityRecommendationsWithDetailsSortedByRecommendationId();

		 return ors;
    }
	 
	 @PostMapping("/addOutReco/{activity_id}/{level_id}/{wthr_id}")
	 public void addOutReco ( @PathVariable("activity_id")Integer activity_id,
			                    @PathVariable("level_id")Integer level_id,
			                    @PathVariable("wthr_id")Integer wthr_id  )
	 {
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
		 
	 }
	 
	 @PostMapping("/delOutReco/{outreid}")
	 public void delOutReco(  @PathVariable("outreid") Integer outreid ) {
		 
		 adminService.delOutReco(outreid);
		 
	 }
	 
	 @GetMapping("/getTrNms")
	 public List<TravelNames> allgetTrNms(){
   	 
		 List<TravelNames> trnms = trnmrepo.findAll();

		 return trnms;
     }
	 
	 @PostMapping("/addTrNms/{traname}")
	 public void addTrNms ( @PathVariable("traname")String traname)
	 {
		 TravelNames trnms = new TravelNames();
		 
		 trnms.setTravelname(traname);
		 
		 trnmrepo.save(trnms);
		 
	 }
	 
	 @PostMapping("/delTrNms/{traid}")
	 public void delTrNms(  @PathVariable("traid") Integer traid ) {

		 
		 adminService.delTrNms(traid);
		 
	 }
	 
	 @GetMapping("/getTraReco")
	 public List<TravelRecommendationDetailsProjection> getTraReco(){
   	 
		 List<TravelRecommendationDetailsProjection> trs = travelrepo.findAllTravelRecommendationsWithDetailsSortedByRecommendationId();

		 return trs;
    }
	 
	 @PostMapping("/addTraReco/{travel_id}/{level_id}/{wthr_id}")
	 public void addTraReco ( @PathVariable("travel_id")Integer travel_id,
			                    @PathVariable("level_id")Integer level_id,
			                    @PathVariable("wthr_id")Integer wthr_id  )
	 {
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
		 
	 }
	 
	 @PostMapping("/delTraReco/{trareid}")
	 public void delTraReco(  @PathVariable("trareid") Integer trareid ) {
		 
		 adminService.delTraReco(trareid);
		 
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
