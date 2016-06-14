/**
 * 
 */
package com.cisco.acisizer.physical.util;

/**
 * @author Mahesh
 *
 */
public class AciPhysicalSizerConstants {
	public static final String BASE_URL = "/acisizer/v1/project/{id}/physical/";
	public static final String ROOM_URL = BASE_URL + "rooms";
	public static final String ROW_URL = BASE_URL + "rooms/{roomId}/rows";
	public static final String RACK_URL = BASE_URL + "rooms/{roomId}/rows/{rowId}/racks";
	public static final String DEVICE_URL = BASE_URL + "racks/{id}/devices";
	public static final String RACKTYPE_URL = "/acisizer/v1/physical/racktypes";
	public static final String POLICY_URL = "/acisizer/v1/physical/policy";
	public static final String SERVER_URL = BASE_URL + "rooms/{roomId}/rows/{rowId}/racks/{rackId}/servers";
	public static final String SWITCH_URL = BASE_URL + "rooms/{roomId}/rows/{rowId}/racks/{rackId}/switches";
	public static final String DEVICETYPE_URL = "/acisizer/v1/physical/devices";
	public static final String PORT_DOMAIN_D1 = "D1";
	public static final String PORT_DOMAIN_D2 = "D2";
	public static final String PORT_DOMAIN_D = "D";
	public static final String RACK_PREFIX = "Rack-";
	public static final String SPINE = "Spine";
	public static final String LEAF = "Leaf";
	public static final int TERMINATION_NOT_STARTED = 0;
	public static final int TERMINATED_SUCCESSFUL = 2;
	public static final int TERMINATED_UNSUCCESSFUL = 1;
	public static final String SPINE_TERMINATION_FAILED = "spine termination failed";
	public static final String SPEED_UNIT_G = "G";
	public static final String DATE_FORMAT_HH_MM_SS = "hh:mm:ss";
	public static final String SEPERATOR = ",";

}
