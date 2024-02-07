package com.fable.weatherall.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fable.weatherall.FoodEntites.Food;
import com.fable.weatherall.FoodEntites.FoodTemperatureMap;

@EnableJpaRepositories
@Repository
public interface FoodRepo extends JpaRepository<Food, Integer> {

    @Query("SELECT tc.categoryId " +
           "FROM TemperatureCategory tc " +
           "WHERE :temperature BETWEEN tc.minTemperature AND tc.maxTemperature")
    Integer findCategoryIdByTemperature(@Param("temperature") Double temperature);
    
    @Query("SELECT ftm.foodId FROM FoodTemperatureMap ftm WHERE ftm.categoryId = :categoryId")
    List<Integer> findFoodIdsByCategoryId(@Param("categoryId") Integer categoryId);
    
    @Query("SELECT f.foodName FROM Food f WHERE f.foodId IN :foodIds AND f.state = :state")
    List<String> findFoodNamesByFoodIdsAndState(@Param("foodIds") List<Integer> foodIds, @Param("state") String state);

//	@Query("SELECT ftm.foodId " +
//		       "FROM FoodTemperatureMap ftm " +
//		       "WHERE ftm.food.state = :state AND ftm.temperatureCategory.categoryId = :categoryId")
//    List<Food> findByStateAndCategoryId(@Param("state") String state, @Param("categoryId") Integer categoryId);
	
	
	//Below methods for Admin Control
		
	@Query("SELECT f FROM Food f WHERE f.foodName = :foodName AND f.state = :state")
	List<Food> findByFoodNameAndState(@Param("foodName") String foodName, @Param("state") String state);
	
	@Query("SELECT f.foodId FROM Food f WHERE f.foodName = :foodName AND f.state = :state")
	Integer findFoodIdByFoodNameAndState(@Param("foodName") String foodName, @Param("state") String state);

	void deleteByFoodId(int id);
	
	List<Food> findAll();
	
//	List<FoodTemperatureMap> findAllTempMap();
	
//	  @Query("SELECT ftm.foodTemperatureId, ftm.food.foodId, ftm.temperatureCategory.categoryId FROM FoodTemperatureMap ftm WHERE ftm.food.foodId IN (SELECT f.foodId FROM Food f)")
//	  List<Object[]> findAllFoodTemperatureMapRecords();
	
	
    
    
}