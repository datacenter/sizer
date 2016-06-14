/**
 * 
 */
package com.cisco.acisizer.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.cisco.acisizer.physical.rest.models.View;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author Mahesh
 *
 */
@Embeddable
public class PhysicalStatsTable {

	@Column(name = "depth")
	@JsonView(View.Room.class)
	private double depth;

	@Column(name = "height")
	@JsonView(View.Room.class)
	private double height;

	@Column(name = "num_of_rus")
	@JsonView(View.Room.class)
	private int noOfRus;

	@Column(name = "weight")
	@JsonView(View.Room.class)
	private double weight;

	@Column(name = "width")
	@JsonView(View.Room.class)
	private double width;

	/**
	 * @return the depth
	 */
	public double getDepth() {
		return depth;
	}

	/**
	 * @param depth
	 *            the depth to set
	 */
	public void setDepth(double depth) {
		this.depth = depth;
	}

	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(double height) {
		this.height = height;
	}

	/**
	 * @return the noOfRus
	 */
	public int getNoOfRus() {
		return noOfRus;
	}

	/**
	 * @param noOfRus
	 *            the noOfRus to set
	 */
	public void setNoOfRus(int noOfRus) {
		this.noOfRus = noOfRus;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	public void clone(PhysicalStatsTable physicalStats) {
		physicalStats.setDepth(this.getDepth());
		physicalStats.setHeight(this.getHeight());
		physicalStats.setNoOfRus(this.getNoOfRus());
		physicalStats.setWeight(this.getWeight());
		physicalStats.setWidth(this.getWidth());
	}

}
