package com.cisco.acisizer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cisco.acisizer.domain.UserTable;

public interface UsersRepository extends JpaRepository<UserTable, String>  {

	@Query("select user from UserTable user where userId = ?1")
	UserTable getUser(String userId);

}
