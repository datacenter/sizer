package com.cisco.acisizer.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cisco.acisizer.domain.Filter;

public interface FilterRepository extends JpaRepository<Filter, Integer>{

}
