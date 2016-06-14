/**
 * 
 */
package com.cisco.acisizer.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cisco.acisizer.domain.RackTorMappingTable;
import com.cisco.acisizer.domain.SwitchTable;

/**
 * @author Mahesh
 *
 */
public interface RackTorMappingRepository extends JpaRepository<RackTorMappingTable, Integer> {

	@Query("select p from RackTorMappingTable p where p.rackTable.id=?1 and p.switchTable.id=?2 and p.speed=?3 and p.domain=?4 ")
	RackTorMappingTable findRackTorMappingByRackIDSwitchIdAndDomain(int rackid, int switchId, String speed, String domain);
	
	@Query("select p from RackTorMappingTable p where p.rackTable.id=?1 and p.switchTable.id=?2 and p.speed=?3 ")
	RackTorMappingTable findRackTorMappingByRackIDAndSwitchId(int rackid, int switchId, String speed);
	
	@Query("select p from RackTorMappingTable p where p.rackTable.id=?1 and p.speed=?2 and p.domain=?3 ")
	List<RackTorMappingTable> findRackTorMappingByRackId(int rackid, String speed, String domain);
	
	@Query("delete from RackTorMappingTable p where p.rackTable.id=?1 ")
	@Modifying
	@Transactional
	void deleteByRackID(int rackId);
	
	@Query("delete from RackTorMappingTable p where p.switchTable=?1 ")
	@Modifying
	@Transactional
	void deleteBySwitchID(SwitchTable switchId);
	
	@Query("select p from RackTorMappingTable p where p.switchTable=?1")
	List<RackTorMappingTable> findRackTorMappingByswitchId(SwitchTable switchId);

}
