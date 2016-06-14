--liquibase formatted sql



--changeset runOnChange Release20:1


-- Create tables section -------------------------------------------------

-- Table room

CREATE TABLE "room"(
 "id" Integer NOT NULL,
 "name" Text,
 "inventry_info" Text,
 "policy_id" Integer,
 "project_id" Integer NOT NULL,
 "no_of_rows" Integer,
 "no_of_racks" Integer,
 "rack_type_id" Integer
)
;

-- Create indexes for table room

CREATE INDEX "IX_Relationship61" ON "room" ("policy_id")
;

CREATE INDEX "IX_Relationship41" ON "room" ("rack_type_id")
;

-- Add keys for table room

ALTER TABLE "room" ADD CONSTRAINT "Key1" PRIMARY KEY ("id")
;

-- Table row

CREATE TABLE "row"(
 "id" Integer NOT NULL,
 "name" Text,
 "room_id" Integer,
 "inventry_info" Text,
 "policy_id" Integer,
 "policy_inherited" Boolean,
 "rack_type_id" Integer,
 "unterminated_racks" Text,
 "state" Integer
 
)
;

-- Create indexes for table row

CREATE INDEX "IX_Relationship4" ON "row" ("room_id")
;

CREATE INDEX "IX_Relationship2" ON "row" ("policy_id")
;

-- Add keys for table row

ALTER TABLE "row" ADD CONSTRAINT "room_fk" PRIMARY KEY ("id")
;

-- Table device_type

CREATE TABLE "device_type"(
 "id" Integer NOT NULL,
 "name" Text NOT NULL,
 "fans" Integer,
 "no_of_usb" Integer,
 "no_of_vga" Integer,
 "no_of_kvm" Integer,
 "type" Text,
 "depth" Double precision,
 "height" Double precision,
 "num_of_rus" Integer,
 "weight" Double precision,
 "width" Double precision,
 "no_of_inputs" Integer,
 "power_rating_per_input" Double precision,
 "power" Double precision,
 "cooling_in_btu" Integer,
 "redundancy_model" Text,
 "default_port_group" Text,
 "description" Text,
 "color" Text,
 "order_no" Integer
)
;

-- Add keys for table device_type

ALTER TABLE "device_type" ADD CONSTRAINT "Key5" PRIMARY KEY ("id")
;

ALTER TABLE "device_type" ADD CONSTRAINT "id" UNIQUE ("id")
;

-- Table rack

CREATE TABLE "rack"(
 "id" Integer NOT NULL,
 "name" Text NOT NULL,
 "rack_type" Integer,
 "is_end_of_row" Boolean,
 "row_id" Integer,
 "aggregate_port_domain_list_d1" Text,
 "aggregate_port_domain_list_d2" Text,
 "aggregate_port_domain_list_d" Text,
 "inventry_info" Text,
 "policy_id" Integer,
 "policy_inherited" Boolean,
 "is_network_type_rack" Boolean,
 "is_terminated" Boolean
)
;

-- Create indexes for table rack

CREATE INDEX "IX_Relationship15" ON "rack" ("row_id")
;

CREATE INDEX "IX_Relationship1" ON "rack" ("rack_type")
;

CREATE INDEX "IX_Relationship53" ON "rack" ("policy_id")
;

-- Add keys for table rack

ALTER TABLE "rack" ADD CONSTRAINT "Key6" PRIMARY KEY ("id")
;

-- Table devices

CREATE TABLE "devices"(
 "id" Integer NOT NULL,
 "device_type_id" Integer,
 "rack_id" Integer,
 "port_domain_list_d1" Text,
 "port_domain_list_d2" Text,
 "port_domain_list_d" Text,
 "port_group" Text,
 "is_ucs_managed" Boolean,
 "num_of_instances" Integer,
 "descriminator" Text,
 "placed_in" Integer,
 "domain" Text,
 "uplink_bandwidth" Integer,
 "num_of_links" Integer
)
;

-- Create indexes for table devices

CREATE INDEX "IX_Relationship13" ON "devices" ("device_type_id")
;

CREATE INDEX "IX_Relationship52" ON "devices" ("rack_id","rack_id")
;

-- Add keys for table devices

ALTER TABLE "devices" ADD CONSTRAINT "Key10" PRIMARY KEY ("id")
;

-- Table rack_type

CREATE TABLE "rack_type"(
 "id" Integer NOT NULL,
 "type" Text,
 "no_of_inputs" Integer,
 "power_rating_per_input" Double precision,
 "power" Double precision,
 "cooling_in_btu" Integer,
 "depth" Double precision,
 "height" Double precision,
 "num_of_rus" Integer,
 "weight" Double precision,
 "width" Double precision,
 "redundancy_model" Text,
 "description" Text
)
;

-- Add keys for table rack_type

ALTER TABLE "rack_type" ADD CONSTRAINT "Key12" PRIMARY KEY ("id")
;

-- Table rack_tor_mapping

CREATE TABLE "rack_tor_mapping"(
 "id" Integer NOT NULL,
 "rack_id" Integer,
 "device_id" Integer,
 "speed" Text,
 "type" Text,
 "domain" Text,
 "num_of_ports_terminated" Integer
)
;

-- Create indexes for table rack_tor_mapping

CREATE INDEX "IX_Relationship3" ON "rack_tor_mapping" ("rack_id","rack_id","rack_id")
;

CREATE INDEX "IX_Relationship62" ON "rack_tor_mapping" ("device_id")
;

-- Add keys for table rack_tor_mapping

ALTER TABLE "rack_tor_mapping" ADD CONSTRAINT "Key14" PRIMARY KEY ("id")
;

-- Table policy

CREATE TABLE "policy"(
 "id" Integer NOT NULL,
 "name" Text,
 "details" Text
)
;

-- Add keys for table policy

ALTER TABLE "policy" ADD CONSTRAINT "Key15" PRIMARY KEY ("id")
;

-- Create relationships section ------------------------------------------------- 

ALTER TABLE "devices" ADD CONSTRAINT "device-fk" FOREIGN KEY ("device_type_id") REFERENCES "device_type" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "rack" ADD CONSTRAINT "row-fk" FOREIGN KEY ("row_id") REFERENCES "row" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "rack_tor_mapping" ADD CONSTRAINT "source-rack-fk" FOREIGN KEY ("rack_id") REFERENCES "rack" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "rack" ADD CONSTRAINT "rack-type-fk" FOREIGN KEY ("rack_type") REFERENCES "rack_type" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "devices" ADD CONSTRAINT "rack-fk" FOREIGN KEY ("rack_id") REFERENCES "rack" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "row" ADD CONSTRAINT "room-fk" FOREIGN KEY ("room_id") REFERENCES "room" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "room" ADD CONSTRAINT "project-fk" FOREIGN KEY ("project_id") REFERENCES "aci_project" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "room" ADD CONSTRAINT "policy_fk" FOREIGN KEY ("policy_id") REFERENCES "policy" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "row" ADD CONSTRAINT "row_policy_fk" FOREIGN KEY ("policy_id") REFERENCES "policy" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "rack" ADD CONSTRAINT "rack_policy_fk" FOREIGN KEY ("policy_id") REFERENCES "policy" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "room" ADD CONSTRAINT "room_rack_fk" FOREIGN KEY ("rack_type_id") REFERENCES "rack_type" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "rack_tor_mapping" ADD CONSTRAINT "device_fk" FOREIGN KEY ("device_id") REFERENCES "devices" ("id") ON DELETE CASCADE ON UPDATE NO ACTION
;

INSERT INTO RACK_TYPE  (ID,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,COOLING_IN_BTU,POWER) 
VALUES (1,'12RU',1000,12,25,23.6,33.5,0,0.0,'',10800.0,3.6);
INSERT INTO RACK_TYPE  (ID,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,COOLING_IN_BTU,POWER) 
VALUES (2,'24RU',1000,24,45.7,23.6,33.5,0,0.0,'',21600.0,7.2);
INSERT INTO RACK_TYPE  (ID,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,COOLING_IN_BTU,POWER) 
VALUES (3,'42RU',2100,42,78.74,24,43.38,0,0.0,'',27800.0,12.6);
INSERT INTO RACK_TYPE  (ID,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,COOLING_IN_BTU,POWER) 
VALUES (4,'48RU',3000,48,88.9,29.53,47.24,0,0.0,'',43200.0,14.4);

INSERT INTO POLICY VALUES (1,'Conservative Port Allocation',
	'{
	  "growthPolicy" : 30,
	  "leafOversubscription" : 20,
	  "cableType" : "SFP",
	  "leafsPerRack" : 2,
	  "leafUtilization" : 70,
	  "rackRuUtilization" : 70,
	  "rackPowerUtilization" : 70,
	  "preferredAciLeaf" : "N9K-9372PX-E",
	  "preferredAciLeafId" : 6,
	  "preferredAciSpine" : "N9K-9508",
	  "preferredAciSpineId" : 13,
	  "preferredN9kLeaf" : "N9K-9372PX-E",
	  "preferredN9kLeafId" : 6,
	  "preferredN9kSpine" : "N9K-9508",
	  "preferredN9kSpineId" : 13
	}'
);
INSERT INTO POLICY VALUES (2,'Aggressive Port Allocation',
	'{
	  "growthPolicy" : 5,
	  "leafOversubscription" : 20,
	  "cableType" : "SFP",
	  "leafsPerRack" : 2,
	  "leafUtilization" : 95,
	  "rackRuUtilization" : 95,
	  "rackPowerUtilization" : 95,
	  "preferredAciLeaf" : "N9K-9372PX-E",
	  "preferredAciLeafId" : 6,
	  "preferredAciSpine" : "N9K-9508",
	  "preferredAciSpineId" : 13,
	  "preferredN9kLeaf" : "N9K-9372PX-E",
	  "preferredN9kLeafId" : 6,
	  "preferredN9kSpine" : "N9K-9508",
	  "preferredN9kSpineId" : 13
	}'
);
 
INSERT INTO DEVICE_TYPE
(ID, NAME,DESCRIPTION,color,FANS,NO_OF_USB, NO_OF_VGA, NO_OF_KVM,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,POWER,COOLING_IN_BTU,DEFAULT_PORT_GROUP,ORDER_NO)
VALUES (1,'UCS C220 M3','UCS C220-M3 Rack Server','#ffffff', 5,4,1,1,'Server',16.8,1,1.7,16.9,28.5,2,930,'A-S',122.76,418.86,'[{ "name": "Mgmt","speed": "1G", "numOfPorts": 1,"type": "RJ45",
"redundancyModel": "Individual", "placement":"1-TOR"},{"name": "Data1","speed": "10G","numOfPorts": 4,"type": "SFP+","redundancyModel": "A-A", "placement":"2-TOR"}]',3);
INSERT INTO DEVICE_TYPE
(ID, NAME,DESCRIPTION,color,FANS,NO_OF_USB, NO_OF_VGA, NO_OF_KVM,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,POWER,COOLING_IN_BTU,DEFAULT_PORT_GROUP,ORDER_NO)
VALUES (2,'UCS C220 M4','UCS C220-M4 Rack Server','#ffffff',6,4,1,1,'Server',14.3,1,1.7,16.9,26.0,2,1050,'A-S',250.24,853.86,'[{"name": "Mgmt","speed": "1G","numOfPorts": 1,"type": "RJ45",
"redundancyModel": "Individual", "placement": "1-TOR"},{"name": "Data1","speed": "10G","numOfPorts": 4,"type": "SFP+","redundancyModel": "A-A","placement":"2-TOR" }]',3);
INSERT INTO DEVICE_TYPE
(ID, NAME,DESCRIPTION,color,FANS,NO_OF_USB, NO_OF_VGA, NO_OF_KVM,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,POWER,COOLING_IN_BTU,DEFAULT_PORT_GROUP,ORDER_NO)
VALUES (3,'UCS C240 M3','UCS C240-M3 Rack Server','#ffffff', 6,4,1,1,'Server',28.1,2,3.4,17.5,28.0,2,930,'A-A',119.33,407.18,'[{"name": "Mgmt","speed": "1G","numOfPorts": 1,"type": "RJ45"
,"redundancyModel": "Individual", "placement":"1-TOR"},{"name": "Data1","speed": "10G","numOfPorts": 8,"type": "SFP+","redundancyModel": "A-A", "placement":"2-TOR"}]',3);
INSERT INTO DEVICE_TYPE
(ID, NAME,DESCRIPTION,color,FANS,NO_OF_USB, NO_OF_VGA, NO_OF_KVM,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,POWER,COOLING_IN_BTU,DEFAULT_PORT_GROUP,ORDER_NO)
VALUES (4,'UCS C240 M4','UCS C240-M4 Rack Server','#ffffff', 6,4,1,1,'Server',30.6,2,3.43,17.65,29.0,2,1400,'A-S',332.23,1133.6,'[{"name": "Mgmt","speed": "1G","numOfPorts": 1,"type": "RJ45"
,"redundancyModel": "Individual", "placement":"1-TOR"},{"name": "Data1","speed": "10G","numOfPorts": 4,"type": "SFP+","redundancyModel": "A-A", "placement":"2-TOR"}]',3);

INSERT INTO DEVICE_TYPE
(ID, NAME,DESCRIPTION,color,FANS,NO_OF_USB, NO_OF_VGA, NO_OF_KVM,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,POWER,COOLING_IN_BTU,DEFAULT_PORT_GROUP,ORDER_NO)
VALUES (5,'N9K-9332PQ','Nexus 9332PQ Switch','#87CEEC', 4,0,0,0,'Leaf',9.7,1,1.72,17.3,22.5,2,930,'A-S',228,777.94,'[{"name": "40G", "speed": "40G",  "numOfPorts":32,"type": "QSFP+"}]',1);
INSERT INTO DEVICE_TYPE
(ID, NAME,DESCRIPTION,color,FANS,NO_OF_USB, NO_OF_VGA, NO_OF_KVM,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,POWER,COOLING_IN_BTU,DEFAULT_PORT_GROUP,ORDER_NO)
VALUES (6,'N9K-9372PX-E', 'Nexus 9372PX-E Switch','#87CEEC',4,0,0,0,'Leaf',10.1,1,1.72,17.3,22.5,2,930,'A-S',210,716.52,'[{"name": "1/10G","speed": "10G", "numOfPorts": 48,"type": "SFP+"},
       {"name": "40G", "speed": "40G", "numOfPorts": 6, "type": "QSFP+"}]',1);
INSERT INTO DEVICE_TYPE
(ID, NAME,DESCRIPTION,color,FANS,NO_OF_USB, NO_OF_VGA, NO_OF_KVM,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,POWER,COOLING_IN_BTU,DEFAULT_PORT_GROUP,ORDER_NO)
VALUES (7,'N9K-9372TX-E','Nexus 9372TX-E Switch','#87CEEC', 4,0,0,0,'Leaf',10.25,1,1.72,17.3,22.5,2,930,'A-S',374.5,1277.79,'[{"name": "1/10G", "speed": "10G", "numOfPorts": 48,"type": "RJ45"},
       {"name": "40G","speed": "40G", "numOfPorts": 6, "type": "QSFP+"}]',1);
INSERT INTO DEVICE_TYPE
(ID, NAME,DESCRIPTION,color,FANS,NO_OF_USB, NO_OF_VGA, NO_OF_KVM,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,POWER,COOLING_IN_BTU,DEFAULT_PORT_GROUP,ORDER_NO)
VALUES (8,'N9K-9396PX','Nexus 9396PX Switch','#87CEEC', 3,0,0,0,'Leaf',10.2,2,3.5,17.5,22.5,2,930,'A-S',232,791.58,'[{"name": "1/10G","speed": "10G", "numOfPorts": 48, "type": "SFP+"},
  {"name": "40G",   "speed": "40G",  "numOfPorts": 12,   "type": "QSFP+"}]',1);
INSERT INTO DEVICE_TYPE
(ID, NAME,DESCRIPTION,color,FANS,NO_OF_USB, NO_OF_VGA, NO_OF_KVM,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,POWER,COOLING_IN_BTU,DEFAULT_PORT_GROUP,ORDER_NO)
VALUES (9,'N9K-9396TX','Nexus 9396TX Switch','#87CEEC', 3,0,0,0,'Leaf',10.2,2,3.5,17.5,22.5,2,930,'A-S',427,1456.92,'[{"name": "1/10G", "speed": "10G","numOfPorts": 48,"type": "RJ45"},
        {"name": "40G","speed": "40G",  "numOfPorts": 12, "type": "QSFP+"}]',1);
INSERT INTO DEVICE_TYPE
(ID, NAME,DESCRIPTION,color,FANS,NO_OF_USB, NO_OF_VGA, NO_OF_KVM,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,POWER,COOLING_IN_BTU,DEFAULT_PORT_GROUP,ORDER_NO)
VALUES (10,'N9K-93128TX','Nexus 93128TX Switch','#87CEEC', 3,0,0,0,'Leaf',14.8,3,5.3,17.5,22.5,2,930,'A-S',542,1849.3,'[{"name": "1/10G","speed": "10G","numOfPorts": 96, "type": "RJ45"},
 {"name": "40G",  "speed": "40G",  "numOfPorts": 8,   "type": "QSFP+"}]',1);
INSERT INTO DEVICE_TYPE
(ID, NAME,DESCRIPTION,color,FANS,NO_OF_USB, NO_OF_VGA, NO_OF_KVM,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,POWER,COOLING_IN_BTU,DEFAULT_PORT_GROUP,ORDER_NO)
VALUES (11,'N9K-9336PQ','Nexus 9336PQ Spine','#87CEEC', 3,0,0,0,'Spine',15.6,2,3.5,17.5,22.5,2,1200,'A-S',400,1364.8,'[{"name": "Data1","speed": "40G","numOfPorts":36,"type": "QSFP+"}]',2);
INSERT INTO DEVICE_TYPE
(ID, NAME,DESCRIPTION,color,FANS,NO_OF_USB, NO_OF_VGA, NO_OF_KVM,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,POWER,COOLING_IN_BTU,DEFAULT_PORT_GROUP,ORDER_NO)
VALUES (12,'N9K-9516','Nexus 9516 Spine','#87CEEC', 3,0,0,0,'Spine',0,16,1.7,16.9,28.5,3,650,'INDIVIDUAL',330,1125.96,'[{"name": "Data1","speed": "40G", "numOfPorts":576, "type": "QSFP+" }]',2);
INSERT INTO DEVICE_TYPE
(ID, NAME,DESCRIPTION,color,FANS,NO_OF_USB, NO_OF_VGA, NO_OF_KVM,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,POWER,COOLING_IN_BTU,DEFAULT_PORT_GROUP,ORDER_NO)
VALUES (13,'N9K-9508', 'Nexus 9508 Spine','#87CEEC',3,0,0,0,'Spine',0,8,1.7,16.9,28.5,3,650,'INDIVIDUAL',250,852,'[{"name": "Data1","speed": "40G", "numOfPorts":288, "type": "QSFP+" }]',2);
INSERT INTO DEVICE_TYPE
(ID, NAME,DESCRIPTION,color,FANS,NO_OF_USB, NO_OF_VGA, NO_OF_KVM,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,POWER,COOLING_IN_BTU,DEFAULT_PORT_GROUP,ORDER_NO)
VALUES (14,'N9K-9504','Nexus 9504 Spine','#87CEEC', 3,0,0,0,'Spine',0,4,1.7,16.9,28.5,3,650,'INDIVIDUAL',137,467.44,'[{"name": "Data1","speed": "40G", "numOfPorts":144, "type": "QSFP+" }]',2);

INSERT INTO DEVICE_TYPE
(ID, NAME,DESCRIPTION,color,FANS,NO_OF_USB, NO_OF_VGA, NO_OF_KVM,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,POWER,COOLING_IN_BTU,DEFAULT_PORT_GROUP,ORDER_NO)
VALUES (15,'UCS 6248UP','UCS 6248UP Fabric Interconnect','#6DA5BC', 2,0,0,0,'Fabric Interconnect',15.87,1,1.7,17.3,29.5,2,750,'INDIVIDUAL',350, 1998,'[{"name":"Data1","speed":"10G","numOfPorts":2,"type":"SFP+","redundancyModel": "A-A", "placement":"2-TOR"}]',4);
INSERT INTO DEVICE_TYPE
(ID, NAME,DESCRIPTION,color,FANS,NO_OF_USB, NO_OF_VGA, NO_OF_KVM,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,POWER,COOLING_IN_BTU,DEFAULT_PORT_GROUP,ORDER_NO)
VALUES (16,'UCS 6296UP','UCS 6296UP Fabric Interconnect','#6DA5BC', 4,0,0,0,'Fabric Interconnect',22.68,2,3.47,17.3,30,2,750,'INDIVIDUAL',750,3163,'[{"name":"Data1","speed":"10G","numOfPorts":2,"type":"SFP+","redundancyModel": "A-A", "placement":"2-TOR"}]',4);
INSERT INTO DEVICE_TYPE
(ID, NAME,DESCRIPTION,color,FANS,NO_OF_USB, NO_OF_VGA, NO_OF_KVM,TYPE,WEIGHT,NUM_OF_RUS,HEIGHT,WIDTH,DEPTH,NO_OF_INPUTS,POWER_RATING_PER_INPUT,REDUNDANCY_MODEL,POWER,COOLING_IN_BTU,DEFAULT_PORT_GROUP,ORDER_NO)
VALUES (17,'ASA 5585-X','ASA 5585-X with firePOWER Services','#AADDF0', 0,2,0,0,'Firewall',28.2,2,3.47,19.0,26.5,2,770,'A-S',370, 3960,'[{"name": "Data1","speed":"1G","numOfPorts":8,"type":"SFP+","redundancyModel": "A-A", "placement":"2-TOR"},
{"name": "Data2","speed":"10G","numOfPorts":2,"type":"SFP+","redundancyModel": "A-A", "placement":"2-TOR"}]',5);
