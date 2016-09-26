package com.cisco.acisizer.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.cisco.acisizer.view.View;
import com.fasterxml.jackson.annotation.JsonView;

@Embeddable
public class PowerCoolingTable {

	@Column(name = "no_of_inputs")
	@JsonView(View.Room.class)
	private int noOfInputs; // power inputs

	@Column(name = "power_rating_per_input")
	@JsonView(View.Room.class)
	private double powerRatingPerInputInKWatts;

	@Column(name = "power")
	@JsonView(View.Room.class)
	private double power;

	@Column(name = "cooling_in_btu")
	@JsonView(View.Room.class)
	private int coolingInBTU;

	/**
	 * @return the noOfInputs
	 */
	public int getNoOfInputs() {
		return noOfInputs;
	}

	/**
	 * @param noOfInputs
	 *            the noOfInputs to set
	 */
	public void setNoOfInputs(int noOfInputs) {
		this.noOfInputs = noOfInputs;
	}

	/**
	 * @return the powerRatingPerInputInKWatts
	 */
	public double getPowerRatingPerInputInKWatts() {
		return powerRatingPerInputInKWatts;
	}

	/**
	 * @param powerRatingPerInputInKWatts
	 *            the powerRatingPerInputInKWatts to set
	 */
	public void setPowerRatingPerInputInKWatts(double powerRatingPerInputInKWatts) {
		this.powerRatingPerInputInKWatts = powerRatingPerInputInKWatts;
	}

	/**
	 * @return the coolingInBTU
	 */
	public int getCoolingInBTU() {
		return coolingInBTU;
	}

	/**
	 * @param coolingInBTU
	 *            the coolingInBTU to set
	 */
	public void setCoolingInBTU(int coolingInBTU) {
		this.coolingInBTU = coolingInBTU;
	}

	public void clone(PowerCoolingTable powerCooling) {
		powerCooling.setCoolingInBTU(this.getCoolingInBTU());
		powerCooling.setNoOfInputs(this.getNoOfInputs());
		powerCooling.setPowerRatingPerInputInKWatts(this.getPowerRatingPerInputInKWatts());
	}

	/**
	 * @return the power
	 */
	public double getPower() {
		return power;
	}

	/**
	 * @param power
	 *            the power to set
	 */
	public void setPower(double power) {
		this.power = power;
	}

}
