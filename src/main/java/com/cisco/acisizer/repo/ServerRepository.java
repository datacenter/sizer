/**
 * 
 */
package com.cisco.acisizer.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cisco.acisizer.domain.ServerTable;

/**
 * @author Mahesh
 *
 */
public interface ServerRepository extends JpaRepository<ServerTable, Integer>{

}
