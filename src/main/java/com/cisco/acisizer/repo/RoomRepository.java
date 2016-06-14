/**
 * 
 */
package com.cisco.acisizer.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cisco.acisizer.domain.RoomTable;

/**
 * @author Mahesh
 *
 */
public interface RoomRepository extends JpaRepository<RoomTable, Integer> {
	
	@Query("select p from RoomTable p where project_id = ?1")
	List<RoomTable> findByProjectId(int projectId);
	
	@Query("delete from RoomTable where project_id = ?1")
	void deleteByProjectId(int projectId);

}
