package com.fable.weatherall.Repos;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.fable.weatherall.Admin_User_Entities.ApiKeyUrl;


@EnableJpaRepositories
@Repository
public interface ApiKeyUrlRepo extends JpaRepository<ApiKeyUrl, Long> {

	List<ApiKeyUrl> findAll();
	
//	@Query("SELECT a.apikey FROM ApiKeyUrl a WHERE a.id = :id")
//	String findApikeyById(long id);
	
	ApiKeyUrl findById(long id);
	
    
}