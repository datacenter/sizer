package com.cisco.acisizer.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cisco.acisizer.domain.FilterEntry;

public interface FilterEntryRepository extends JpaRepository<FilterEntry, Integer>{

}
