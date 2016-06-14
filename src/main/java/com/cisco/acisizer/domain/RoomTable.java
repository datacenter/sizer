/**
 * 
 */
package com.cisco.acisizer.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import com.cisco.acisizer.physical.rest.models.View;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;



/**
 * @author Mahesh
 *
 */
@Entity
@Table(name = "room")
@XmlRootElement
public class RoomTable extends AciDomain {

	@Id
	@GenericGenerator(name = "seq_id", strategy = "com.cisco.acisizer.util.GenericIdGenerator")
	@GeneratedValue(generator = "seq_id")
	@Column(name = "id")
	@JsonView(View.Room.class)
	private int id;
	
	@Column(name = "name")
	@JsonView(View.Room.class)
	private String name;
	
	@Column(name = "inventry_info")
	@JsonView(View.Room.class)
	private String inventoryInfo;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id")
	@JsonView(View.Room.class)
	private PolicyTable policy;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
	@JsonBackReference
	private ProjectTable projectTable;

	@Column(name = "no_of_rows")
	@JsonView(View.Room.class)
	private int noOfRows;
	
	@Column(name = "no_of_racks")
	@JsonView(View.Room.class)
	private int noOfRacks;
	
	@OneToMany(mappedBy = "room",fetch = FetchType.LAZY, cascade={CascadeType.ALL})
	@JsonManagedReference
	@JsonView(View.Rows.class)
	private List<RowTable> rows;
	
	@ManyToOne
    @JoinColumn(name = "rack_type_id")
	@JsonView(View.Room.class)
	private RackTypeTable rackType;
	
	@Transient
	private int totalBandwidth;
	
	/**
	 * @return the inventoryInfo
	 */
	public InventoryInfo getInventoryInfo() {
		return gson.fromJson(inventoryInfo, InventoryInfo.class);
	}

	/**
	 * @param inventoryInfo the inventoryInfo to set
	 */
	public void setInventoryInfo(InventoryInfo inventoryInfo) {
		this.inventoryInfo = gson.toJson(inventoryInfo);
	}

	/**
	 * @return the projectTable
	 */
	public ProjectTable getProjectTable() {
		return projectTable;
	}

	/**
	 * @param projectTable the projectTable to set
	 */
	public void setProjectTable(ProjectTable projectTable) {
		this.projectTable = projectTable;
	}

	

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
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

	/**
	 * @return the rows
	 */
	public List<RowTable> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<RowTable> rows) {
		this.rows = rows;
	}

	/**
	 * @return the noOfRows
	 */
	public int getNoOfRows() {
		return noOfRows;
	}

	/**
	 * @param noOfRows the noOfRows to set
	 */
	public void setNoOfRows(int noOfRows) {
		this.noOfRows = noOfRows;
	}

	/**
	 * @return the noOfRacks
	 */
	public int getNoOfRacks() {
		return noOfRacks;
	}

	/**
	 * @param noOfRacks the noOfRacks to set
	 */
	public void setNoOfRacks(int noOfRacks) {
		this.noOfRacks = noOfRacks;
	}

	/**
	 * @return the policy
	 */
	public PolicyTable getPolicy() {
		return policy;
	}

	/**
	 * @param policy the policy to set
	 */
	public void setPolicy(PolicyTable policy) {
		this.policy = policy;
	}

	/**
	 * @return the rackType
	 */
	public RackTypeTable getRackType() {
		return rackType;
	}

	/**
	 * @param rackType the rackType to set
	 */
	public void setRackType(RackTypeTable rackType) {
		this.rackType = rackType;
	}

	/**
	 * @return the totalBandwidth
	 */
	public int getTotalBandwidth() {
		return totalBandwidth;
	}

	/**
	 * @param totalBandwidth the totalBandwidth to set
	 */
	public void setTotalBandwidth(int totalBandwidth) {
		this.totalBandwidth = totalBandwidth;
	}

}
