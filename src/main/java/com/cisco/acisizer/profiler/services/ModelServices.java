package com.cisco.acisizer.profiler.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.cisco.acisizer.domain.Model;
import com.cisco.acisizer.domain.Plugin;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.repo.ModelRepository;
import com.cisco.acisizer.repo.PluginRepository;
import com.cisco.acisizer.ui.models.ModelUi;

@Service
public class ModelServices {
	@Inject
	private ModelRepository modelRepository;
	@Inject
	private PluginRepository pluginRepository;

	public Model getModel(int id) throws AciEntityNotFound {
		Model model = modelRepository.findOne(id);
		if (null == model) {
			throw new AciEntityNotFound("model do not exist");
		}
		return model;
	}

	public List<Model> getModels() throws AciEntityNotFound {
		List<Model> model = modelRepository.findAll();
		if (null == model) {
			throw new AciEntityNotFound("models do not exist");
		}
		return model;
	}

	public Model addModel(ModelUi modelUi) throws AciEntityNotFound {
		Model model = new Model();
		model.setName(modelUi.getName());
		Plugin plugin = pluginRepository.findOne(modelUi.getPluginId());
		if(null == plugin){
			throw new AciEntityNotFound("Plugin do not exist");
		}
		model.setPlugin(plugin);
		modelRepository.save(model);
		return model;
	}

}
