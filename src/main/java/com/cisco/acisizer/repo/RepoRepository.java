package com.cisco.acisizer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cisco.acisizer.domain.RepoObjects;


public interface RepoRepository extends JpaRepository<RepoObjects, Integer> {
	
}
