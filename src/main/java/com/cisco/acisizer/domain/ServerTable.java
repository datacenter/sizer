package com.cisco.acisizer.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cisco.acisizer.physical.rest.models.View;
import com.cisco.acisizer.physical.util.PortDomainJpaConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author Mahesh
 *
 */
@Entity
@Table(name="devices")
@DiscriminatorValue("SERVER")
public class ServerTable extends DeviceTable {

	@Column(name="is_ucs_managed")
	@JsonView(View.Rows.class)
	private boolean isUcsManaged;

	@Column(name="num_of_instances")
	@JsonView(View.Rows.class)
	private int numOfInstances;

	
	@ManyToOne
	@JoinColumn(name = "rack_id",updatable=false,insertable=false,nullable=false)
	@JsonBackReference
	private RackTable rack;
	
	@Column(name = "port_domain_list_d1")
	@Convert(converter=PortDomainJpaConverter.class)
	protected Map<String, PortDomain> portDomainD1;

	@Column(name = "port_domain_list_d2")
	@Convert(converter=PortDomainJpaConverter.class)
	protected Map<String, PortDomain> portDomainD2;
	
	public ServerTable() {
		super();
		setPortDomainD1(new HashMap<String,PortDomain>());
		setPortDomainD2(new HashMap<String,PortDomain>());
	}

	public Map<String, PortDomain> getPortDomainD1() {
		return portDomainD1;
	}

	public void setPortDomainD1(Map<String, PortDomain> portDomainD1) {
		this.portDomainD1 = portDomainD1;
	}

	public Map<String, PortDomain> getPortDomainD2() {
		return portDomainD2;
	}

	public void setPortDomainD2(Map<String, PortDomain> portDomainD2) {
		this.portDomainD2 = portDomainD2;
	}

	public RackTable getRack() {
		return rack;
	}

	public void setRack(RackTable rack) {
		this.rack = rack;
	}

	/**
	 * @return the isUcsManaged
	 */
	public boolean isUcsManaged() {
		return isUcsManaged;
	}

	/**
	 * @param isUcsManaged
	 *            the isUcsManaged to set
	 */
	public void setUcsManaged(boolean isUcsManaged) {
		this.isUcsManaged = isUcsManaged;
	}

	/**
	 * @return the numOfInstances
	 */
	public int getNumOfInstances() {
		return numOfInstances;
	}

	/**
	 * @param numOfInstances
	 *            the numOfInstances to set
	 */
	public void setNumOfInstances(int numOfInstances) {
		this.numOfInstances = numOfInstances;
	}
	
}