/**
 * 
 */
package com.cisco.acisizer.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cisco.acisizer.domain.RackTable;

/**
 * @author Mahesh
 *
 */
public interface RackRepository extends JpaRepository<RackTable, Integer> {

	@Query("select p from RackTable p where p.name IN  (:names)")
	List<RackTable> findRacksByNames(@Param("names") List<String> names);

	@Query("select p from RackTable p where p.name = (:name) and p.row.id=(:id)")
	RackTable findRacksByName(@Param("name") String names, @Param("id") int id);

	@Query("select p from RackTable p where  p.row.room.id=(:id)")
	List<RackTable> findAllRacksByRoomId(@Param("id") int roomId);
	
	@Query("select p from RackTable p where  p.row.room.id=(:id) and p.isNetworkTypeRack = true")
	List<RackTable> findAllNetworkRacksByRoomId(@Param("id") int roomId);

}
