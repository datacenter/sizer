package com.cisco.acisizer.domain;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.cisco.acisizer.physical.util.PortDomainJpaConverter;
import com.cisco.acisizer.view.View;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.reflect.TypeToken;

/**
 * @author Mahesh
 *
 */
@Entity
@Table(name = "devices")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "descriminator", discriminatorType = DiscriminatorType.STRING)
public abstract class DeviceTable extends AciDomain{

	@ManyToOne
	@JoinColumn(name = "device_type_id")
	@JsonView(View.Rows.class)
	protected DeviceType deviceType;

	@Id
	@GenericGenerator(name = "seq_id", strategy = "com.cisco.acisizer.util.GenericIdGenerator")
	@GeneratedValue(generator = "seq_id")
	@Column(name = "id")
	@JsonView(View.Rows.class)
	protected int id;

	@Column(name = "port_group")
	@JsonView(View.Rows.class)
	protected String portGroups;

	@Column(name = "port_domain_list_d")
	@Convert(converter=PortDomainJpaConverter.class)
	protected Map<String, PortDomain> portDomainD;

	public Map<String, PortDomain> getPortDomainD() {
		return portDomainD;
	}

	public void setPortDomainD(Map<String, PortDomain> portDomainD) {
		this.portDomainD = portDomainD;
	}

	@ManyToOne
	@JoinColumn(name = "rack_id")
	@JsonBackReference
	protected RackTable rackTable;
	
	public DeviceTable() {
		setPortDomainD(new HashMap<String,PortDomain>());
	}

	/**
	 * @return the rackTable
	 */
	public RackTable getRackTable() {
		return rackTable;
	}

	/**
	 * @param rackTable the rackTable to set
	 */
	public void setRackTable(RackTable rackTable) {
		this.rackTable = rackTable;
	}

	/**
	 * @return the deviceType
	 */
	public DeviceType getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType
	 *            the deviceType to set
	 */
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
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
	 * @return the portGroups
	 */
	public List<PortGroup> getPortGroups() {
		Type type = new TypeToken<List<PortGroup>>(){}.getType();
		List<PortGroup> list = gson.fromJson(this.portGroups,type );
		return list;
	}

	/**
	 * @param portGroups
	 *            the portGroups to set
	 */
	public void setPortGroups(List<PortGroup> portGroups) {
		this.portGroups = gson.toJson(portGroups);
	}

}