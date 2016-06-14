/**
 * 
 */
package com.cisco.acisizer.helper;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.cisco.acisizer.domain.PortDomain;
import com.cisco.acisizer.domain.PortGroup;
import com.cisco.acisizer.domain.RackTable;

/**
 * @author Mahesh
 *
 */
@Component
public class DeviceHelper {

	public static final String SPEED_TYPE_G = "G";

	@Inject
	private RackInventoryCalculator rackInventoryCalculator;

	@Inject
	private RowHelper rowHelper;

	@Inject
	private RoomHelper roomHelper;

	/**
	 * @param rackTable
	 */
	public void calculateInventryInfo(RackTable rackTable) {
		rackInventoryCalculator.calculateInventryInfo(rackTable);
		rowHelper.calculateInventryInfo(rackTable.getRow());
		roomHelper.calculateInventryInfo(rackTable.getRow().getRoom());
	}

	public void addPortDomain(Map<String, PortDomain> portDomainD1, PortGroup iter, int numOfPorts, String domain) {
		PortDomain portDomain = new PortDomain();
		portDomain.setType(iter.getType());
		portDomain.setNumOfPorts(numOfPorts);
		portDomain.setDomain(domain);
		portDomain.setSpeed(getSpeedInt(iter));
		portDomainD1.put(iter.getSpeed(), portDomain);
		

	}

	private int getSpeedInt(PortGroup iter) {
		return Integer.parseInt(iter.getSpeed().split(SPEED_TYPE_G)[0]);
	}

	
}
