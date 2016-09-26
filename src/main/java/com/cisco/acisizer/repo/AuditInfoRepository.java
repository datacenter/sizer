/**
 * 
 */
package com.cisco.acisizer.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cisco.acisizer.domain.AuditInfo;

/**
 * @author Mahesh
 *
 */
public interface AuditInfoRepository extends JpaRepository<AuditInfo, Integer> {

}
