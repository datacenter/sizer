package com.cisco.acisizer.physical.services;

import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import com.cisco.acisizer.domain.RackTypeTable;
import com.cisco.acisizer.repo.RackTypeRepository;

@Service
public class RackTypeServices {

	@Inject
	private RackTypeRepository rackTypeRepository;

	public List<RackTypeTable> getRackType() {
		List<RackTypeTable> rackType = rackTypeRepository.findAll();
		return rackType;
	}
}
