/**
 * 
 */
package com.cisco.acisizer.utils;

/**
 * @author Mahesh
 *
 */
public class TestingConstants {
	
	public static final String S = "s";
	public static final String ID = "/{id}";
	public static final String UPDATE_PROJECT_INPUT = "{\"name\": \"proj22\",\"customerName\": \"harry\",\"salesContact\": \"kumar\",\"opportunity\": \"abc\",\"account\": \"abc\",\"type\":\"nexus 9000\"}";
	public static final String ADD_PROJECT_INPUT = "{\"name\": \"proj21\",\"customerName\": \"xyz\",\"salesContact\": \"abc\",\"opportunity\": \"abc\",\"account\": \"abc\",\"type\":\"nexus 9000\"}";
	public static final String PROJECT_BASE_URI = "/acisizer/v1/project";
	public static final String ADD_TENANT_URI=PROJECT_BASE_URI+"/{projectId}/tenant";
	public static final String UPDATE_TENANT_URI=ADD_TENANT_URI+ID;
	public static final String GET_TENANTS_URI=ADD_TENANT_URI+S;
	public static final String ADD_APP_URI=UPDATE_TENANT_URI+"/app";
	public static final String ADD_EPG_URI=ADD_APP_URI+ID+"/epg";
	public static final String UPDATE_EPG_URI=ADD_EPG_URI+ID;
	public static final String GET_EPG_URI=ADD_EPG_URI+S;

	public static final String ADD_VRF_URI=UPDATE_TENANT_URI+"/vrf";
	public static final String UPDATE_VRF_URI=ADD_VRF_URI+ID;
	public static final String GET_VRF_URI=ADD_VRF_URI+S;


	public static final String ADD_BD_URI=ADD_VRF_URI+"/{vrfId}/bd";
	public static final String UPDATE_BD_URI=ADD_BD_URI+ID;
	public static final String GET_BD_URI=ADD_BD_URI+S;


	public static final String ADD_L3OUT_URI=ADD_VRF_URI+"/{vrfId}/l3Out";
	public static final String UPDATE_L3OUT_URI=ADD_L3OUT_URI+ID;
	public static final String GET_L3OUT_URI=ADD_L3OUT_URI+S;

	

	public static final String UPDATE_APP_URI=ADD_APP_URI+ID;
	public static final String GET_APPS_URI=ADD_APP_URI+S;
	public static final String NAME = "name";
	public static final String DISPLAY_NAME="displayName";
	public static final String NODE_COLLECTION_URI=UPDATE_TENANT_URI+"/nodeCollection";
	public static final int COMMON_TENANT_ID = 1;
	public static final int NODE_COLLECTION_SIZE = 4;
	public static final String NODES = "nodes";
	public static final String TYPE =NODES+".type";
	public static final java.lang.String DUMMY = "dummy";
	

	private TestingConstants() {
	}

}
