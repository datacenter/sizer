/**
 * 
 */
package com.cisco.acisizer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author Mahesh
 *
 */
@Entity
@Table(name = "rack_tor_mapping")
@XmlRootElement
public class RackTorMappingTable extends AciDomain {

	@Id
	@GenericGenerator(name = "seq_id", strategy = "com.cisco.acisizer.util.GenericIdGenerator")
	@GeneratedValue(generator = "seq_id")
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "rack_id")
	@JsonBackReference
	private RackTable rackTable;

	@ManyToOne
	@JoinColumn(name = "device_id")
	@JsonBackReference
	private SwitchTable switchTable;
	
	@Column(name = "speed")
	private String speed;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "domain")
	private String domain;
	
	@Column(name = "num_of_ports_terminated")
	private int numOfPortsTerminated;
	

	/**
	 * @return the switchTable
	 */
	public SwitchTable getSwitchTable() {
		return switchTable;
	}

	/**
	 * @param switchTable
	 *            the switchTable to set
	 */
	public void setSwitchTable(SwitchTable switchTable) {
		this.switchTable = switchTable;
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
	 * @return the rackTable
	 */
	public RackTable getRackTable() {
		return rackTable;
	}

	/**
	 * @param rackTable
	 *            the rackTable to set
	 */
	public void setRackTable(RackTable rackTable) {
		this.rackTable = rackTable;
	}

	/**
	 * @return the speed
	 */
	public String getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(String speed) {
		this.speed = speed;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return the numOfPortsTerminated
	 */
	public int getNumOfPortsTerminated() {
		return numOfPortsTerminated;
	}

	/**
	 * @param numOfPortsTerminated the numOfPortsTerminated to set
	 */
	public void setNumOfPortsTerminated(int numOfPortsTerminated) {
		this.numOfPortsTerminated = numOfPortsTerminated;
	}

}
