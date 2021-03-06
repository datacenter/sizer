/**
 * 
 */
package com.cisco.acisizer.apic.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Mahesh
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Vrf {
	
	@XmlAttribute
	private String name;

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
}
