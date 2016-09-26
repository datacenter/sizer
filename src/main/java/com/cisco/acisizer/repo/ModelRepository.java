package com.cisco.acisizer.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cisco.acisizer.domain.Model;

public interface ModelRepository extends JpaRepository<Model, Integer> {

}
