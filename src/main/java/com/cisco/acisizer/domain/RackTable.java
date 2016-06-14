/**
 * 
 */
package com.cisco.acisizer.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.cisco.acisizer.physical.rest.models.View;
import com.cisco.acisizer.physical.util.PortDomainJpaConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author Mahesh
 *
 */
@Entity
@Table(name = "rack")
@XmlRootElement
public class RackTable extends AciDomain {

	@Id
	@GenericGenerator(name = "seq_id", strategy = "com.cisco.acisizer.util.GenericIdGenerator")
	@GeneratedValue(generator = "seq_id")
	@Column(name = "id")
	@JsonView(View.Rows.class)
	private int id;

	@Column(name = "name")
	@JsonView(View.Rows.class)
	private String name;

	@ManyToOne
	@JoinColumn(name = "rack_type")
	@JsonView(View.Rows.class)
	private RackTypeTable rackType;

	@Column(name = "is_end_of_row")
	@JsonView(View.Rows.class)
	private boolean isEndOfRow;

	@ManyToOne
	@JoinColumn(name = "row_id")
	@JsonBackReference
	private RowTable row;

	@OneToMany(mappedBy = "rack", cascade = { CascadeType.ALL })
	@Where(clause = "descriminator='SERVER'")
	@JsonView(View.Rows.class)
	private List<ServerTable> servers;

	@OneToMany(mappedBy = "rack", cascade = CascadeType.ALL, orphanRemoval = true)
	@Where(clause = "descriminator='SWITCH'")
	@JsonView(View.Rows.class)
	private List<SwitchTable> switches;

	@Column(name = "policy_inherited")
	@JsonView(View.Rows.class)
	private boolean policyInherited;

	@Column(name = "aggregate_port_domain_list_d1")
	@Convert(converter = PortDomainJpaConverter.class)
	private Map<String, PortDomain> aggPortDomainListD1;

	@Column(name = "aggregate_port_domain_list_d2")
	@Convert(converter = PortDomainJpaConverter.class)
	private Map<String, PortDomain> aggPortDomainListD2;

	@Column(name = "aggregate_port_domain_list_d")
	@Convert(converter = PortDomainJpaConverter.class)
	private Map<String, PortDomain> aggPortDomainListD;

	@Column(name = "inventry_info")
	@JsonView(View.Rows.class)
	private String inventoryInfo;

	@ManyToOne
	@JoinColumn(name = "policy_id")
	@JsonView(View.Rows.class)
	private PolicyTable policy;

	@Column(name = "is_network_type_rack")
	@JsonView(View.Rows.class)
	private boolean isNetworkTypeRack;

	@Column(name = "is_Terminated")
	@JsonView(View.Rows.class)
	private boolean isTerminated;

	@Transient
	private int totalBandwidth;

	public boolean isTerminated() {
		return isTerminated;
	}

	public void setTerminated(boolean isTerminated) {
		this.isTerminated = isTerminated;
	}

	public boolean isNetworkTypeRack() {
		return isNetworkTypeRack;
	}

	public void setNetworkTypeRack(boolean isNetworkTypeRack) {
		this.isNetworkTypeRack = isNetworkTypeRack;
	}

	public RackTable() {
		setAggPortDomainListD(new HashMap<String, PortDomain>());
		setAggPortDomainListD1(new HashMap<String, PortDomain>());
		setAggPortDomainListD2(new HashMap<String, PortDomain>());
	}

	public List<SwitchTable> getSwitches() {
		return switches;
	}

	public void setSwitches(List<SwitchTable> switches) {
		this.switches = switches;
	}

	public List<ServerTable> getServers() {
		return servers;
	}

	public void setServers(List<ServerTable> servers) {
		this.servers = servers;
	}

	/**
	 * @return the row
	 */
	public RowTable getRow() {
		return row;
	}

	/**
	 * @param row
	 *            the row to set
	 */
	public void setRow(RowTable row) {
		this.row = row;
	}

	public Map<String, PortDomain> getAggPortDomainListD1() {
		return aggPortDomainListD1;
	}

	public void setAggPortDomainListD1(Map<String, PortDomain> aggPortDomainListD1) {
		this.aggPortDomainListD1 = aggPortDomainListD1;
	}

	public Map<String, PortDomain> getAggPortDomainListD2() {
		return aggPortDomainListD2;
	}

	public void setAggPortDomainListD2(Map<String, PortDomain> aggPortDomainListD2) {
		this.aggPortDomainListD2 = aggPortDomainListD2;
	}

	public Map<String, PortDomain> getAggPortDomainListD() {
		return aggPortDomainListD;
	}

	public void setAggPortDomainListD(Map<String, PortDomain> aggPortDomainListD) {
		this.aggPortDomainListD = aggPortDomainListD;
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

	/**
	 * @return the isEndOfRow
	 */
	public boolean isEndOfRow() {
		return isEndOfRow;
	}

	/**
	 * @param isEndOfRow
	 *            the isEndOfRow to set
	 */
	public void setEndOfRow(boolean isEndOfRow) {
		this.isEndOfRow = isEndOfRow;
	}

	/**
	 * @return the inventoryInfo
	 */
	public InventoryInfo getInventoryInfo() {
		return gson.fromJson(inventoryInfo, InventoryInfo.class);
	}

	/**
	 * @param inventoryInfo
	 *            the inventoryInfo to set
	 */
	public void setInventoryInfo(InventoryInfo inventoryInfo) {
		this.inventoryInfo = gson.toJson(inventoryInfo);
	}

	/**
	 * @return the policy
	 */
	public PolicyTable getPolicy() {
		return policy;
	}

	/**
	 * @param policy
	 *            the policy to set
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
	 * @param rackType
	 *            the rackType to set
	 */
	public void setRackType(RackTypeTable rackType) {
		this.rackType = rackType;
	}

	public boolean isPolicyInherited() {
		return policyInherited;
	}

	public void setPolicyInherited(boolean policyInherited) {
		this.policyInherited = policyInherited;
	}

	public void setInheritedPolicy(PolicyTable policy) {
		this.policyInherited = true;
		this.policy = policy;
	}

	public void clone(RackTable dest) {
		dest.setAggPortDomainListD(this.getAggPortDomainListD());
		dest.setAggPortDomainListD1(this.getAggPortDomainListD1());
		dest.setAggPortDomainListD2(this.getAggPortDomainListD2());
		dest.setEndOfRow(this.isEndOfRow());
		dest.setInventoryInfo(this.getInventoryInfo());
		dest.setName(this.getName());
		dest.setRackType(this.getRackType());
	}

	/**
	 * @return the totalBandwidth
	 */
	public int getTotalBandwidth() {
		return totalBandwidth;
	}

	/**
	 * @param totalBandwidth
	 *            the totalBandwidth to set
	 */
	public void setTotalBandwidth(int totalBandwidth) {
		this.totalBandwidth = totalBandwidth;
	}

}
