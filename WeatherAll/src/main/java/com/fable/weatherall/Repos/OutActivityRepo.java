package com.fable.weatherall.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fable.weatherall.OutEntities.Activity;

public interface OutActivityRepo extends JpaRepository<Activity, Integer>{
	
	 List<Activity> findAll(); 
	 
	 void deleteByActivityid(int a);

}
