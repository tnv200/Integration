package com.fable.weatherall.Repos;

import java.util.Comparator;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fable.weatherall.OutEntities.ActivityRecommendation;
import com.fable.weatherall.Repos.ClothRepo.ClothingRecommendationDetailsProjection;



public interface OutRepo extends JpaRepository<ActivityRecommendation, Integer> {
	
	public interface ActivityRecommendationProjection {
		
        String getActivityname();
        String getLevel();
    }

    public interface ActivityRecommendationDetailsProjection {
		
	    Integer getRecommendationid();
        String getWeather_Description();
        String getActivityname();
        String getLevel();
    }
	
    @Query("SELECT a.Activityname as activityname, rlo.level as level " +
           "FROM ActivityRecommendation ar " +
           "JOIN ar.activity a " +
           "JOIN ar.recommendationLevel rlo " +
           "JOIN ar.weatherDescription wd " +
           "WHERE wd.description = :description")
    List<ActivityRecommendationProjection> findByWeatherDescriptionDescription(@Param("description") String description);

    
    @Query("SELECT ar.activityRecommendationid AS recommendationid, wd.description AS weather_Description, a.Activityname AS activityname, rlo.level AS level " +
	           "FROM ActivityRecommendation ar " +
	           "JOIN ar.weatherDescription wd " +
	           "JOIN ar.recommendationLevel rlo " +
	           "JOIN ar.activity a")
	 List<ActivityRecommendationDetailsProjection> findAllActivityRecommendationsWithDetails();
    
    
       default List<ActivityRecommendationDetailsProjection> findAllActivityRecommendationsWithDetailsSortedByRecommendationId() {
    	
        List<ActivityRecommendationDetailsProjection> results = findAllActivityRecommendationsWithDetails();
        
        results.sort(Comparator.comparing(ActivityRecommendationDetailsProjection::getRecommendationid));
        return results;
    }
	  
       
       void deleteByActivityRecommendationid(int a);

}
