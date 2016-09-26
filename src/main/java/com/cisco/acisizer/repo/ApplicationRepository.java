/**
 * 
 */
package com.cisco.acisizer.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cisco.acisizer.domain.Application;

/**
 * @author Mahesh
 *
 */
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

}
