--liquibase formatted sql



--changeset runOnChange Release22:1


ALTER TABLE "aci_project" ADD "use_physical" Boolean;
ALTER TABLE "aci_project" ADD "room_id" Integer;
UPDATE aci_project set use_physical = false;

UPDATE DEVICE_TYPE set NAME='N9K-C9332PQ' WHERE ID=5;
UPDATE DEVICE_TYPE set NAME='N9K-C9372PX-E' WHERE ID=6;
UPDATE DEVICE_TYPE set NAME='N9K-C9372TX-E' WHERE ID=7;
UPDATE DEVICE_TYPE set NAME='N9K-C9396PX' WHERE ID=8;
UPDATE DEVICE_TYPE set NAME='N9K-C9396TX' WHERE ID=9;
UPDATE DEVICE_TYPE set NAME='N9K-C93128TX' WHERE ID=10;

UPDATE DEVICE_TYPE set NAME='N9K-C9336PQ' WHERE ID=11;
UPDATE DEVICE_TYPE set NAME='N9K-C9516' WHERE ID=12;
UPDATE DEVICE_TYPE set NAME='N9K-C9508' WHERE ID=13;
UPDATE DEVICE_TYPE set NAME='N9K-C9504' WHERE ID=14;



UPDATE POLICY  set details='{
	  "growthPolicy" : 30,
	  "leafOversubscription" : 20,
	  "cableType" : "SFP",
	  "leafsPerRack" : 2,
	  "leafUtilization" : 70,
	  "rackRuUtilization" : 70,
	  "rackPowerUtilization" : 70,
	  "preferredAciLeaf" : "N9K-C9372PX-E",
	  "preferredAciLeafId" : 6,
	  "preferredAciSpine" : "N9K-C9508",
	  "preferredAciSpineId" : 13,
	  "preferredN9kLeaf" : "N9K-C9372PX-E",
	  "preferredN9kLeafId" : 6,
	  "preferredN9kSpine" : "N9K-C9508",
	  "preferredN9kSpineId" : 13
	}'
 where ID=1;

UPDATE POLICY  set details='{
	  "growthPolicy" : 5,
	  "leafOversubscription" : 20,
	  "cableType" : "SFP",
	  "leafsPerRack" : 2,
	  "leafUtilization" : 95,
	  "rackRuUtilization" : 95,
	  "rackPowerUtilization" : 95,
	  "preferredAciLeaf" : "N9K-C9372PX-E",
	  "preferredAciLeafId" : 6,
	  "preferredAciSpine" : "N9K-C9508",
	  "preferredAciSpineId" : 13,
	  "preferredN9kLeaf" : "N9K-C9372PX-E",
	  "preferredN9kLeafId" : 6,
	  "preferredN9kSpine" : "N9K-C9508",
	  "preferredN9kSpineId" : 13
	}'
 where ID=2;
