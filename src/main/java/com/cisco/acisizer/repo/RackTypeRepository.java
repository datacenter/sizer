/**
 * 
 */
package com.cisco.acisizer.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cisco.acisizer.domain.RackTypeTable;

/**
 * @author Mahesh
 *
 */
public interface RackTypeRepository extends JpaRepository<RackTypeTable, Integer> {

}
