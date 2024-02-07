package com.fable.weatherall.Repos;

import java.util.Comparator;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fable.weatherall.ClothEntites.ClothingItem;
import com.fable.weatherall.ClothEntites.ClothingRecommendation;
import com.fable.weatherall.Repos.OutRepo.ActivityRecommendationDetailsProjection;



public interface ClothRepo extends JpaRepository<ClothingRecommendation, Integer> {

	public interface ClothingRecommendationProjection {
		
	    String getItemName();
        String getTypeName();
	}
	
	public interface ClothingRecommendationDetailsProjection {
		
	    Integer getClothingRecommendationId();
        String getDescription();
        String getItemName();
        String getTypeName();
    }

	@Query("SELECT ci.itemName as itemName, ct.typeName as typeName	" +
	       "FROM ClothingRecommendation cr " +
	       "JOIN cr.weatherDescription wd " +
	       "JOIN cr.clothingType ct " +
	       "JOIN cr.clothingItem ci " +
	       "WHERE wd.description = :description")
	List<ClothingRecommendationProjection> findByWeatherDescriptionDescription(@Param("description") String description);
	
	
	  @Query("SELECT cr.clothingRecommendationId AS clothingRecommendationId, wd.description AS description, ci.itemName AS itemName, ct.typeName AS typeName " +
	           "FROM ClothingRecommendation cr " +
	           "JOIN cr.weatherDescription wd " +
	           "JOIN cr.clothingType ct " +
	           "JOIN cr.clothingItem ci")
	  List<ClothingRecommendationDetailsProjection> findAllClothingRecommendationsWithDetails();
	  
	  
	  
	  default List<ClothingRecommendationDetailsProjection> findAllClothingRecommendationsWithDetailsSortedByClothingRecommendationId() {
	    	
	        List<ClothingRecommendationDetailsProjection> results = findAllClothingRecommendationsWithDetails();
	        
	        results.sort(Comparator.comparing(ClothingRecommendationDetailsProjection::getClothingRecommendationId));
	        
	        return results;
	        
	  }
	  
	  void deleteByClothingRecommendationId(int a);
	  
	 

//	    @Query("SELECT cr FROM ClothingRecommendation cr " +
//	           "JOIN WeatherDescription wd ON cr.weatherDescriptionId = wd.weatherDescriptionId " +
//	           "WHERE wd.description = :weatherDescription")
//	    List<ClothingRecommendation> findByWeatherDescription(@Param("weatherDescription") String weatherDescription);
	}

