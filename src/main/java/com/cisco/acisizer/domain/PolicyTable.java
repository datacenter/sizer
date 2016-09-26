package com.cisco.acisizer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import com.cisco.acisizer.view.View;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author Mahesh
 *
 */
@Entity
@Table(name = "policy")
@XmlRootElement
public class PolicyTable extends AciDomain {
	
	@Id
	@GenericGenerator(name = "seq_id", strategy = "com.cisco.acisizer.util.GenericIdGenerator")
	@GeneratedValue(generator = "seq_id")
	@Column(name = "id")
	@JsonView(View.Room.class)
	private int id;
	
	@Column(name = "name")
	@JsonView(View.Room.class)
	private String name;

	@Column(name = "details")
	@JsonView(View.Room.class)
	private String details;
	
	public PolicyDetail getDetails() {
		return gson.fromJson(details,PolicyDetail.class);
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
