package com.cisco.acisizer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cisco.acisizer.domain.Users;




public interface UserRepository extends JpaRepository<Users, Integer>  {
	
	@Query("SELECT p FROM Users p WHERE p.username=?1")	
	Users findUserByUserName(String username);

}
