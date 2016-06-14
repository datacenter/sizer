/**
 * 
 */
package com.cisco.acisizer.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cisco.acisizer.domain.PolicyTable;

/**
 * @author Mahesh
 *
 */
public interface PolicyRepository extends JpaRepository<PolicyTable, Integer> {

}
