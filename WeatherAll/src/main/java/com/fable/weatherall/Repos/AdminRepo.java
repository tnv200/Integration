package com.fable.weatherall.Repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fable.weatherall.Admin_User_Entities.Admin;


@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer> {

	 Admin findByUsername(String username);
	 Admin findByEmail(String email);
	 Admin findByPassword(String password);
	 
	 @Query("SELECT a.username FROM Admin a WHERE a.email = :email")
	 String findUsernameByEmail(String email);
	 
	 Optional<Admin> findOneByEmailAndPassword(String email, String encodedPassword);
}
