/**
 * 
 */
package com.cisco.acisizer.services.applicationTemplates;

import com.cisco.acisizer.models.Application;
import com.cisco.acisizer.models.LogicalSummary;
import com.cisco.acisizer.models.Tenant;
import com.cisco.acisizer.ui.models.ApplicationTemplate;

/**
 * @author Mahesh
 *
 */
public interface ApplicationTemplateInterface {

	Application addApplication(ApplicationTemplate appTemplate, Tenant targetTenant,
			Tenant commonTenant, LogicalSummary logicalSummary);
}
