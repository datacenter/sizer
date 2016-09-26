package com.cisco.acisizer.profiler.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.cisco.acisizer.domain.Contract;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.repo.ContractRepository;

@Service
public class ContractServicesRepo {
	@Inject
	ContractRepository contractRepository;
	
	public Contract getContract(int id) throws AciEntityNotFound {
		Contract contract = ValidateAndGetContract(id);
		return contract;
	}

	private Contract ValidateAndGetContract(int id) throws AciEntityNotFound {
		Contract contract = contractRepository.findOne(id);
		if(null == contract){
			throw new AciEntityNotFound("Contract do not exist");
		}
		return contract;
	}

	public List<Contract> getContracts() {
		List<Contract> contract = contractRepository.findAll();
		return contract;
	}

	public Contract deleteContract(int id) throws AciEntityNotFound {
		Contract contract = ValidateAndGetContract(id);
		contractRepository.delete(id);
		return contract;
	}
}
