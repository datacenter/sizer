package com.cisco.acisizer.physical.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.cisco.acisizer.repo.PolicyRepository;


@Service
public class PolicyServices {
	@Inject
	private PolicyRepository policyRepository;


	public List<com.cisco.acisizer.domain.PolicyTable> getPolicy() {
		
		List<com.cisco.acisizer.domain.PolicyTable> policy = policyRepository.findAll();
		return policy;
	}

}
