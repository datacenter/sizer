/**
 * 
 */
package com.cisco.acisizer.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cisco.acisizer.domain.RowTable;



/**
 * @author Mahesh
 *
 */
public interface RowRepository extends JpaRepository<RowTable, Integer> {

}
