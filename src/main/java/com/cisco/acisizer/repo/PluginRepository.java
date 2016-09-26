package com.cisco.acisizer.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cisco.acisizer.domain.Plugin;

public interface PluginRepository extends JpaRepository<Plugin, Integer>{

}
