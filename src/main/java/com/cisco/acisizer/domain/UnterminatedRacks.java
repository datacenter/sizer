/**
 * 
 */
package com.cisco.acisizer.domain;

import com.cisco.acisizer.physical.rest.models.View;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author Mahesh
 *
 */
public class UnterminatedRacks {
	@JsonView(View.Rows.class)
	private int id;
	@JsonView(View.Rows.class)
	private String name;

	public UnterminatedRacks() {
	}

	public UnterminatedRacks(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
