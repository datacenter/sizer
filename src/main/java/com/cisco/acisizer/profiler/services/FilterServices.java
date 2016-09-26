package com.cisco.acisizer.profiler.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.cisco.acisizer.domain.Filter;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.repo.FilterRepository;

@Service
public class FilterServices {
	@Inject
	private FilterRepository filterRepository;

	public Filter getFilter(int id) throws AciEntityNotFound {
		Filter filter = ValidateAndGetFilter(id);
		return filter;
	}

	public List<Filter> getFilters() {
		List<Filter> filter = filterRepository.findAll();
		return filter;
	}

	public Filter deleteFilter(int id) throws AciEntityNotFound {
		Filter filter = ValidateAndGetFilter(id);
		filterRepository.delete(id);
		return filter;
	}

	private Filter ValidateAndGetFilter(int id) throws AciEntityNotFound {
		Filter filter = filterRepository.findOne(id);
		if (null == filter) {
			throw new AciEntityNotFound("Filter do not exist");
		}
		return filter;
	}
}
