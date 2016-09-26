package com.cisco.acisizer.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cisco.acisizer.domain.Contract;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

}
