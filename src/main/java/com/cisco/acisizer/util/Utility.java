package com.cisco.acisizer.util;

import java.util.Random;

import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.models.Contract;
import com.cisco.acisizer.models.LogicalRequirement;
import com.cisco.acisizer.models.LogicalSummary;
import com.cisco.acisizer.models.Tenant;
import com.cisco.acisizer.models.Vrf;
import com.cisco.acisizer.view.ViewConnectionItem;
import com.cisco.acisizer.view.ViewNodeConnection;
import com.cisco.acisizer.view.ViewNodes;

public class Utility {
	private Utility() {
	}

	public static String getDisplayName(String str1, String str2, String str3) {
		return str1 + ACISizerConstant.TENANT_FIELD_SEPERATOR + str2 + ACISizerConstant.TENANT_FIELD_SEPERATOR + str3;
	}

	public static String getConsumedContractName(String str1, String str2) {
		return str1 + ACISizerConstant.TENANT_FIELD_SEPERATOR + str2;
	}

	public static Tenant getCurrentTenant(int tenantId, LogicalRequirement logicalRequirement) {
		Tenant currentTenant = null;
		for (Tenant tenant : logicalRequirement.getTenants()) {
			if (tenantId == tenant.getId()) {
				currentTenant = tenant;
				break;
			}
		}
		return currentTenant;
	}

	public static Tenant getCommonTenant(LogicalRequirement logicalRequirement) {
		Tenant commonTenant = null;
		for (Tenant tenant : logicalRequirement.getTenants()) {
			if (ACISizerConstant.TENANT_TYPE_UTILITY.equals(tenant.getType())) {
				commonTenant = tenant;
				break;
			}
		}
		return commonTenant;
	}

	/**
	 * @param vrfId
	 * @param currTenant
	 * @return
	 * @throws AciEntityNotFound
	 */
	public static Vrf getCurrentVrf(int vrfId, final Tenant currTenant) {
		/*
		 * Optional<Vrf> vrfOptional = currTenant.getVrfs().stream().filter(vrf
		 * -> vrf.getId() == vrfId).findFirst(); return vrfOptional.isPresent()
		 * ? vrfOptional.get() : null;
		 */
		Vrf target = null;
		for (Vrf iter : currTenant.getVrfs()) {
			if (vrfId == iter.getId()) {
				target = iter;
				break;
			}
		}

		return target;
	}

	public static String getSnapshotId(int projectId, int id) {
		return projectId + "" + id;
	}

	public static void convertToViewConnectionType(Integer from, Integer to, ViewNodes nodes) {

		if (null == nodes)
			return;

		ViewNodeConnection connection = new ViewNodeConnection();

		ViewConnectionItem item = new ViewConnectionItem();
		Random rnd = new Random();
		int randomId = rnd.nextInt(ACISizerConstant.RANDOM_NUMBER_GENERATION_MAX);

		item.setFrom(from.toString());
		item.setTo(to.toString());
		connection.setId(randomId);
		connection.setDataItem(item);
		nodes.getConnections().add(connection);
	}

	/*public static void setNodeCountForFabricPane(Tenant targetTenant, LogicalSummary logicalSummary) {

		int countVrf = logicalSummary.getVrfCount();
		int countBd = logicalSummary.getBdCount();

		countVrf += targetTenant.getVrfs().size();
		countBd += targetTenant.getBds().size();

		logicalSummary.setVrfCount(countVrf);
		logicalSummary.setBdCount(countBd);
	}*/

}
