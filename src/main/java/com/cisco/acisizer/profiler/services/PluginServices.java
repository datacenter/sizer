package com.cisco.acisizer.profiler.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.cisco.acisizer.domain.Plugin;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.repo.PluginRepository;
@Service
public class PluginServices {
	@Inject
	PluginRepository pluginRepository;

	public Plugin getPlugin(int id) throws AciEntityNotFound {
		Plugin plugin =pluginRepository.findOne(id);
		if (null == plugin) {
			throw new AciEntityNotFound("plugin do not exist");
		}
		return plugin;
	}

	public List<Plugin> getPlugins() {
		List<Plugin> plugin =pluginRepository.findAll();
		return plugin;
	}

}
