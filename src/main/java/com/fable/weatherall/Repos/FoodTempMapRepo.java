package com.fable.weatherall.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fable.weatherall.FoodEntites.Food;
import com.fable.weatherall.FoodEntites.FoodTemperatureMap;



@EnableJpaRepositories
@Repository
public interface FoodTempMapRepo extends JpaRepository<FoodTemperatureMap, Integer>{
	
	
	List<FoodTemperatureMap> findAll();
	
	void deleteByFoodTemperatureId(int a);
	
	List<FoodTemperatureMap> findByFoodId(int foodid);

}
