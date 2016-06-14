package com.cisco.acisizer.physical.sizing.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.cisco.acisizer.domain.DeviceType;
import com.cisco.acisizer.domain.PortDomain;
import com.cisco.acisizer.domain.RackTable;
import com.cisco.acisizer.domain.RowTable;
import com.cisco.acisizer.domain.SwitchTable;
import com.cisco.acisizer.exceptions.TerminationException;
import com.cisco.acisizer.physical.rest.models.SwitchUi;
import com.cisco.acisizer.physical.services.SwitchServices;
import com.cisco.acisizer.repo.RackRepository;

@Component
public class PlaceSwitchAuto {

	public static final String NOT_ENOUGH_PLACE_FOR_SPINE_PLACEMENT = "not enough place for spine placement";

	@Inject
	private SwitchServices switchServices;

	@Inject
	private RackRepository rackRepository;

	public void PlaceSwitches(RowTable rowTable, SwitchUi switchUi, int versionId) throws TerminationException {

		switch (switchUi.getPlacementPattern()) {

		case "1-1-1":
			placeOneSwitchInAllRack(rowTable, switchUi, versionId);
			break;
		case "2-0-2":
			placeInOddRack(rowTable, switchUi, versionId);
			break;

		case "0-2-0":
			placeInEvenRack(rowTable, switchUi, versionId);
			break;
		case "2-2-2":
			placeTwoSwitchInAllRack(rowTable, switchUi, versionId);
			break;
		}

	}

	private void placeTwoSwitchInAllRack(RowTable rowTable, SwitchUi switchUi, int versionId)
			throws TerminationException {
		for (RackTable rackTable : rowTable.getRacks()) {
			switchServices.addSwitchAuto(rackTable, switchUi, versionId);
			switchServices.addSwitchAuto(rackTable, switchUi, versionId);
		}

	}

	private void placeInEvenRack(RowTable rowTable, SwitchUi switchUi, int versionId) throws TerminationException {
		for (RackTable rackTable : rowTable.getRacks()) {
			int x = TerminationAlgorithm.getIntOffset(rackTable);
			if (x % 2 == 0) {
				switchServices.addSwitchAuto(rackTable, switchUi, versionId);
				switchServices.addSwitchAuto(rackTable, switchUi, versionId);
			}
		}
	}

	private void placeInOddRack(RowTable rowTable, SwitchUi switchUi, int versionId) throws TerminationException {
		for (RackTable rackTable : rowTable.getRacks()) {
			int x = TerminationAlgorithm.getIntOffset(rackTable);
			if (x % 2 != 0) {
				switchServices.addSwitchAuto(rackTable, switchUi, versionId);
				switchServices.addSwitchAuto(rackTable, switchUi, versionId);
			}

		}
	}

	private void placeOneSwitchInAllRack(RowTable rowTable, SwitchUi switchUi, int versionId)
			throws TerminationException {
		for (RackTable rackTable : rowTable.getRacks()) {
			switchServices.addSwitchAuto(rackTable, switchUi, versionId);
		}
	}

	@Transactional
	public void placeSpine(List<RackTable> rackTables, DeviceType switchTable, int numOfSpines, int version)
			throws TerminationException {
		int i = 0;
		for (RackTable rackTable : rackTables) {
			while (i < numOfSpines) {
				if (switchServices.addSwitchAuto(rackTable, version, switchTable)) {
					i++;
				}
			}
			if (i >= numOfSpines) {
				break;
			}
		}
		if (i < numOfSpines) {
			throw new TerminationException(NOT_ENOUGH_PLACE_FOR_SPINE_PLACEMENT);
		}

	}

	public void removeUnusedSwitches(RowTable row, int version) {
		for (RackTable rack : row.getRacks()) {
			List<SwitchTable> switchesToRemove = new ArrayList<SwitchTable>();
			for (SwitchTable switchIter : rack.getSwitches()) {
				Map<String, PortDomain> portDomainD = switchIter.getPortDomainD();
				boolean unUsed = true;
				for (String speed : portDomainD.keySet()) {
					if (portDomainD.get(speed).getNumOfPortsTerminated() > 0 || !(switchIter.getPlacedIn() > 0)) {
						unUsed = false;
						break;
					}
				}
				if (unUsed) {
					switchesToRemove.add(switchIter);
				}
			}
			rack.getSwitches().removeAll(switchesToRemove);
			rackRepository.flush();
		}
	}
}
