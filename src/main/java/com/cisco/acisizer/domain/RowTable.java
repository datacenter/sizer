/**
 * 
 */
package com.cisco.acisizer.domain;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import com.cisco.acisizer.view.View;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.reflect.TypeToken;

/**
 * @author Mahesh
 *
 */
@Entity
@Table(name = "row")
@XmlRootElement
public class RowTable extends AciDomain {

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
    @JoinColumn(name = "room_id")
	@JsonBackReference
    private RoomTable room;
	
	@Column(name = "inventry_info")
	@JsonView(View.Rows.class)
	private String inventoryInfo;
	
	@ManyToOne
    @JoinColumn(name = "policy_id")
	@JsonView(View.Rows.class)
	private PolicyTable policy;
	
	@OneToMany(mappedBy = "row", cascade={CascadeType.ALL})
	@JsonManagedReference
	private List<RackTable> racks;

	@Column(name = "policy_inherited")
	@JsonView(View.Rows.class)
	private boolean policyInherited;
	
	@Column(name = "unterminated_racks")
	@JsonView(View.Rows.class)
	private String unterminatedRacks;
	
	@Column(name = "state")
	@JsonView(View.Rows.class)
	private int state;
	
	@Transient
	private int totalBandwidth;
	
	public List<UnterminatedRacks> getUnterminatedRacks() {
		Type type = new TypeToken<List<UnterminatedRacks>>(){}.getType();
		List<UnterminatedRacks> list = gson.fromJson(this.unterminatedRacks,type );
		return list;
	}



	public void setUnterminatedRacks(List<UnterminatedRacks> unterminatedRacks) {
		this.unterminatedRacks = gson.toJson(unterminatedRacks);
	}

	@ManyToOne
    @JoinColumn(name = "rack_type_id")
	@JsonView(View.Room.class)
	private RackTypeTable rackType;
	
	
	
	public RackTypeTable getRackType() {
		return rackType;
	}



	public void setRackType(RackTypeTable rackType) {
		this.rackType = rackType;
	}



	/**
	 * @return the id
	 */
	public int getId() {
		return id;
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
	 * @return the room
	 */
	public RoomTable getRoom() {
		return room;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(RoomTable room) {
		this.room = room;
	}

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
	 * @return the racks
	 */
	public List<RackTable> getRacks() {
		return racks;
	}

	/**
	 * @param racks the racks to set
	 */
	public void setRacks(List<RackTable> racks) {
		this.racks = racks;
	}
	
	
	
	public boolean isPolicyInherited() {
		return policyInherited;
	}



	public void setPolicyInherited(boolean policyInherited) {
		this.policyInherited = policyInherited;
	}

	public void setInheritedPolicy(PolicyTable policy)
	{
		this.policyInherited = true;
		this.policy = policy;
	}

	public void clone (RowTable dest){
		dest.setInventoryInfo(this.getInventoryInfo());
		dest.setPolicy(this.getPolicy());
		dest.setRacks(new ArrayList<RackTable>());
		dest.setName(this.getName());
		//dest.setRoom(this.getRoom());
		RackTable rackDest;
		for (RackTable rack : this.racks) {
			rackDest=new RackTable();
			rack.clone(rackDest);
			rackDest.setRow(dest);
			dest.getRacks().add(rackDest);
		}
	}



	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}



	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
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
