/**
 * 
 */
package com.cisco.acisizer.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import com.cisco.acisizer.physical.rest.models.View;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author Mahesh
 *
 */
@Entity
@Table(name = "rack_type")
@XmlRootElement
public class RackTypeTable {

	@Id
	@GenericGenerator(name = "seq_id", strategy = "com.cisco.acisizer.util.GenericIdGenerator")
	@GeneratedValue(generator = "seq_id")
	@Column(name = "id")
	@JsonView(View.Room.class)
	private int id;

	@Embedded
	@JsonView(View.Room.class)
	private PhysicalStatsTable physicalStats;

	@Embedded
	@JsonView(View.Room.class)
	private PowerCoolingTable powerCooling;

	@Column(name = "type")
	@JsonView(View.Room.class)
	private String type;

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
	 * @return the physicalStats
	 */
	public PhysicalStatsTable getPhysicalStats() {
		return physicalStats;
	}

	/**
	 * @param physicalStats
	 *            the physicalStats to set
	 */
	public void setPhysicalStats(PhysicalStatsTable physicalStats) {
		this.physicalStats = physicalStats;
	}

	/**
	 * @return the powerCooling
	 */
	public PowerCoolingTable getPowerCooling() {
		return powerCooling;
	}

	/**
	 * @param powerCooling
	 *            the powerCooling to set
	 */
	public void setPowerCooling(PowerCoolingTable powerCooling) {
		this.powerCooling = powerCooling;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
}
