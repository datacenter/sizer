/**
 * 
 */
package com.cisco.acisizer.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cisco.acisizer.domain.SwitchTable;

/**
 * @author Mahesh
 *
 */
public interface SwitchRepository extends JpaRepository<SwitchTable, Integer> {

	@Query("delete from SwitchTable p where p.placedIn =  (select MAX(x.placedIn) from SwitchTable x where x.placedIn > 0 "
			+ "and x.rackTable.id IN (select z from RackTable z where z.row.id=?1 ))"
			+ " and p.rackTable.id IN (select y from RackTable y where y.row.id=?1 )")
	@Modifying
	@Transactional
	void deleteMaxVersionByRow(int rowId);
	
	@Query("delete from SwitchTable p where p.placedIn =  (select MAX(x.placedIn) from SwitchTable x where x.placedIn > 0 "
			+ "and x.deviceType.type = 'Spine' and x.rackTable.id IN (select z from RackTable z where z.row.room.id=?1 ))"
			+ " and p.rackTable.id IN (select y from RackTable y where y.row.room.id=?1 ) " )
	@Modifying
	@Transactional
	void deleteMaxVersionByRoom(int roomId);
	
	@Query("select MAX(x.placedIn) from SwitchTable x "
			+ "where x.rackTable.id IN (select y from RackTable y where y.row.id=?1 )")
	Integer getMaXVersionByRow(int rowId);
	
	@Query("select MAX(x.placedIn) from SwitchTable x "
			+ "where x.rackTable.id IN (select y from RackTable y where y.row.room.id=?1 )")
	Integer getMaXVersionByRoom(int roomId);
	
	@Query("select x from SwitchTable x "
			+ "where x.rackTable.row.room.id =?1 and x.deviceType.type = 'Spine'")
	List<SwitchTable> getAllSpinesOfRoom(int roomId);
	
	@Query("select x from SwitchTable x "
			+ "where x.rackTable.row.id =?1 and x.deviceType.type = 'Leaf' and x.placedIn=?2")
	List<SwitchTable> getAllTorsOfRowByVersion(int rowId,int version);
	
	@Query("select x from SwitchTable x "
			+ "where x.rackTable.row.room.id =?1 and x.deviceType.type = 'Leaf'")
	List<SwitchTable> getAllTorsOfRoom(int roomId);
	
	@Query("select COUNT(*) from SwitchTable x "
			+ "where x.rackTable.row.room.id =?1 and x.deviceType.type = 'Spine'")
	int getSpineCountByRoom(int roomId);
	
	@Query("delete from SwitchTable p where p.id IN (select x.id from SwitchTable x where x.rackTable.row.room.id =?1 and x.deviceType.type = 'Spine' )")
	@Modifying
	@Transactional
	void deleteSpineByRoom(int roomId);

}
