package com.cisco.acisizer.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cisco.acisizer.domain.DeviceType;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, Integer> {

	
	
	@Query("SELECT p from DeviceType p WHERE  p.type !='Spine' AND p.type !='Leaf' AND p.type !='Firewall' AND p.type!='Fabric Interconnect' AND p.type !='Load Balancer'")	
	List<DeviceType> findServers();
	
	@Query("select p.type from DeviceType p ORDER BY order_no ASC")
	List<String> findDevicesByOrder();
	
	@Query("select p from DeviceType p where type = ?1")
	List<DeviceType> findDeviceWithType(String type);

}


