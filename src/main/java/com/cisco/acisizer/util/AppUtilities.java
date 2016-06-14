/**
 * 
 */
package com.cisco.acisizer.util;

import com.cisco.acisizer.models.Application;
import com.cisco.acisizer.models.Tenant;

/**
 * @author Mahesh
 *
 */
public class AppUtilities {
	private AppUtilities() {
	}

	/**
	 * @param appId
	 * @param currTenant
	 * @return
	 */
	public static Application getCurrentApp(int appId, Tenant currTenant) {
		Application currApp = null;
		for (Application app : currTenant.getApps()) {
			if (appId == app.getId()) {
				currApp = app;
				break;
			}
		}
		return currApp;
	}

}
