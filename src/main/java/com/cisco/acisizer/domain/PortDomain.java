package com.cisco.acisizer.domain;

public class PortDomain {

	private String type;

	private int numOfPorts;

	private int numOfPortsTerminated;

	private String domain;
	
	private int speed;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumOfPorts() {
		return numOfPorts;
	}

	public void setNumOfPorts(int numOfPorts) {
		this.numOfPorts = numOfPorts;
	}

	public int getNumOfPortsTerminated() {
		return numOfPortsTerminated;
	}

	public void setNumOfPortsTerminated(int numOfPortsTerminated) {
		this.numOfPortsTerminated = numOfPortsTerminated;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain
	 *            the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getPortsToTerminate() {
		return this.getNumOfPorts() - this.getNumOfPortsTerminated();
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

}