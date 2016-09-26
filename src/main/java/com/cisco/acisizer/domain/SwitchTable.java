package com.cisco.acisizer.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cisco.acisizer.view.View;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author Mahesh
 *
 */
@Entity
@Table(name = "devices")
@DiscriminatorValue("SWITCH")
public class SwitchTable extends DeviceTable {

	@OneToMany(mappedBy = "switchTable", cascade = { CascadeType.ALL })
	@JsonManagedReference
	private List<RackTorMappingTable> racksTerminated;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "rack_id", updatable = false, insertable = false, nullable = false)
	@JsonBackReference
	private RackTable rack;

	@Column(name = "placed_in")
	@JsonView(View.Rows.class)
	private int placedIn;

	@Column(name = "is_spine_termination_done")
	private boolean isSpineTerminationDone;
	
	public boolean isSpineTerminationDone() {
		return isSpineTerminationDone;
	}

	public void setSpineTerminationDone(boolean isSpineTerminationDone) {
		this.isSpineTerminationDone = isSpineTerminationDone;
	}

	@Column(name = "uplink_bandwidth")
	private int uplinkBandwidth;
	
	@Column(name = "num_of_links")
	private int numOfLinks;

	/**
	 * @return the placedIn
	 */
	public int getPlacedIn() {
		return placedIn;
	}

	/**
	 * @param placedIn
	 *            the placedIn to set
	 */
	public void setPlacedIn(int placedIn) {
		this.placedIn = placedIn;
	}

	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (object == null || object.getClass() != getClass()) {
			result = false;
		} else {
			SwitchTable other = (SwitchTable) object;
			return this.id == other.id;
		}
		return result;
	}

	@Override
	public int hashCode() {
		return this.id;
	}

	public RackTable getRack() {
		return rack;
	}

	public void setRack(RackTable rack) {
		this.rack = rack;
	}

	/**
	 * @return the racksTerminated
	 */
	public List<RackTorMappingTable> getRacksTerminated() {
		return racksTerminated;
	}

	/**
	 * @param racksTerminated
	 *            the racksTerminated to set
	 */
	public void setRacksTerminated(List<RackTorMappingTable> racksTerminated) {
		this.racksTerminated = racksTerminated;
	}

	/**
	 * @return the uplinkBandwidth
	 */
	public int getUplinkBandwidth() {
		return uplinkBandwidth;
	}

	/**
	 * @param uplinkBandwidth the uplinkBandwidth to set
	 */
	public void setUplinkBandwidth(int uplinkBandwidth) {
		this.uplinkBandwidth = uplinkBandwidth;
	}

	/**
	 * @return the numOfLinks
	 */
	public int getNumOfLinks() {
		return numOfLinks;
	}

	/**
	 * @param numOfLinks the numOfLinks to set
	 */
	public void setNumOfLinks(int numOfLinks) {
		this.numOfLinks = numOfLinks;
	}

}