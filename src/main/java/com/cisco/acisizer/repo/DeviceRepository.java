package com.cisco.acisizer.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cisco.acisizer.domain.Device;

public interface DeviceRepository extends JpaRepository<Device, Integer> {

	@Query("SELECT p FROM Device p WHERE p.owner=?1")
	List<Device> findDevicesByUserId(String userId);

}
