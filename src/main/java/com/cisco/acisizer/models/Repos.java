package com.cisco.acisizer.models;

import java.util.ArrayList;
import java.util.List;

import com.cisco.acisizer.domain.RepoObjects;

public class Repos {

	private int totalRecords;

	private List<RepoObjects> records = new ArrayList<RepoObjects>();

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<RepoObjects> getRecords() {
		return records;
	}

	public void setRecords(List<RepoObjects> records) {
		this.records = records;
	}

}
