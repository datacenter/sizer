--liquibase formatted sql



--changeset runOnChange Release30:1

CREATE SEQUENCE plugin_seq;
CREATE TABLE plugin(
id int NOT NULL DEFAULT NEXTVAL ('plugin_seq'),
name text DEFAULT NULL,
version text DEFAULT NULL,
description text DEFAULT NULL,
created_time timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
last_updated_time timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
path text DEFAULT NULL,
PRIMARY KEY(id)
);

CREATE SEQUENCE model_seq;
CREATE TABLE model(
id int NOT NULL DEFAULT NEXTVAL ('model_seq'),
name text DEFAULT NULL,
plugin_id int,
PRIMARY KEY(id),
FOREIGN KEY (plugin_id)REFERENCES plugin(id)
);

CREATE SEQUENCE device_seq;
CREATE TABLE device(
id int  NOT NULL DEFAULT NEXTVAL ('device_seq'),
name text DEFAULT NULL,
ip_address text DEFAULT NULL,
owner text NOT NULL,
username text DEFAULT NULL,
password text DEFAULT NULL,
type text DEFAULT NULL,
importedOn timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
importedStatus int Default 1 NOT NULL,
model_id int,
PRIMARY KEY(id),
FOREIGN KEY (model_id) REFERENCES model(id)
);

CREATE SEQUENCE user_seq;
CREATE TABLE users (
  id int NOT NULL DEFAULT NEXTVAL ('user_seq'),
  username text DEFAULT NULL,
  password text DEFAULT NULL,
  email text DEFAULT NULL,
  authentication text DEFAULT NULL,
  role text DEFAULT NULL,
  created_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_updated_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);



ALTER TABLE "aci_project" ADD "device_id" Integer;
ALTER TABLE "aci_project" ADD FOREIGN KEY (device_id) REFERENCES device(id);
ALTER TABLE "aci_project" ADD CONSTRAINT constr_ID UNIQUE (id, name);
ALTER TABLE "aci_project" ADD "users_id" Integer;
ALTER TABLE "aci_project" ADD FOREIGN KEY (users_id) REFERENCES users(id);


CREATE SEQUENCE audit_info_seq;

CREATE TABLE audit_info(
id int NOT NULL DEFAULT NEXTVAL ('audit_info_seq'),
source_device int ,
importedOn text NOT NULL DEFAULT TO_CHAR(CURRENT_DATE,'YYYY-MM-DD'),
auditedOn timestamp(0) NULL,
auditStatus text DEFAULT NULL,
PRIMARY KEY(id),
FOREIGN KEY (source_device)
REFERENCES device(id)
);

CREATE SEQUENCE contract_seq;

CREATE TABLE contract(
id int NOT NULL DEFAULT NEXTVAL ('contract_seq'),
name text DEFAULT NULL,
producerId int DEFAULT NULL,
consumerId int DEFAULT NULL,
auditId int,
PRIMARY KEY(id),
FOREIGN KEY (auditId)
   REFERENCES audit_info(id)
    ON DELETE SET NULL
   ON UPDATE CASCADE
);

CREATE SEQUENCE subject_seq;

CREATE TABLE subject(
id int NOT NULL DEFAULT NEXTVAL ('subject_seq'),
name text DEFAULT NULL,
description text DEFAULT NULL,
contractId int,
PRIMARY KEY(id),
FOREIGN KEY (contractId)
   REFERENCES contract(id)
    ON DELETE SET NULL
   ON UPDATE CASCADE
);

CREATE SEQUENCE filter_seq;

CREATE TABLE filter(
id int NOT NULL DEFAULT NEXTVAL ('filter_seq'),
name text DEFAULT NULL,
description text DEFAULT NULL,
subjectId int,
auditId int,
PRIMARY KEY(id),
FOREIGN KEY (subjectId)
   REFERENCES subject(id)
   ON DELETE SET NULL
   ON UPDATE CASCADE,
FOREIGN KEY (auditId)
   REFERENCES audit_info(id)
   ON DELETE SET NULL
   ON UPDATE CASCADE
);

CREATE SEQUENCE filter_entry_seq;

CREATE TABLE filter_entry(
id int NOT NULL DEFAULT NEXTVAL ('filter_entry_seq'),
name text DEFAULT NULL,
etherType text DEFAULT NULL,
ipProtocol text DEFAULT NULL,
srcPort text DEFAULT NULL,
destPort text DEFAULT NULL,
filterId int,
auditId int,
PRIMARY KEY(id),
FOREIGN KEY (filterId)
   REFERENCES filter(id)
    ON DELETE SET NULL
   ON UPDATE CASCADE,
 FOREIGN KEY (auditId)
   REFERENCES audit_info(id)
   ON DELETE SET NULL
   ON UPDATE CASCADE
);


CREATE VIEW public.repo_objects AS
    (SELECT 
        ae.id,
       'contract' AS "type",
        ae.name,
        d.id AS deviceId,
        d.name AS "source_device",
        au.importedOn,
        au.auditedOn,
       '1.0' AS "objectVersion",
        au.auditStatus
        
    FROM
        audit_info au, contract ae, device d      
      WHERE ae.auditId=au.id and d.id=au.source_device
     ) UNION
     (SELECT 
        af.id,
       'filter' AS type,
        af.name,
        d.id AS deviceId,
        d.name AS "source_device",
        au.importedOn,
        au.auditedOn,
       '1.0' AS "objectVersion",
        au.auditStatus
        
    FROM
        audit_info au, filter af, device d 
            
       WHERE af.auditId=au.id and d.id=au.source_device)  UNION (SELECT 
        fe.id,
        'filter entry' AS type,
        fe.name,
        d.id AS deviceId,
        d.name AS "source_device",
        au.importedOn,
        au.auditedOn,
       '1.0' AS "objectVersion",
        au.auditStatus
        
    FROM
        audit_info au,  filter_entry fe, device d
            
        WHERE fe.auditId=au.id and  d.id=au.source_device);


INSERT INTO users (username,password,email,authentication,role) VALUES ('admin','admin','admin@acme.com','Local Authentication','ROLE_ADMIN');
INSERT INTO users (username,password,email,authentication,role) VALUES ('user','user','user@acme.com','Local Authentication','ROLE_USER');

INSERT INTO plugin(name,version,description,path) VALUES ('palo_plugin','1.0','Palo Alto Networks','/home/appadmin/sizer/plugins/paloAlto/');

INSERT INTO model(name,plugin_id) VALUES ('PA-500',1);
INSERT INTO model(name,plugin_id) VALUES ('PA-5000',1);
INSERT INTO model(name,plugin_id) VALUES ('PA-3000',1);
INSERT INTO model(name,plugin_id) VALUES ('PA-7000',1);
INSERT INTO model(name) VALUES ('ACI');


INSERT INTO device (name,ip_address,username,password,importedStatus,model_id,owner) VALUES ('System Default','$$','uname','pwd',3,1,'admin');

INSERT INTO audit_info(source_device) VALUES (1);

INSERT INTO contract ( name,producerid,consumerid,auditid) VALUES ( 'Allow Web Traffic',0,0,1);

INSERT INTO subject (name,description,contractid) VALUES ('Allow Web traffic','Default contract installed with the application',1);
 
INSERT INTO filter ( name,description,subjectid, auditid) VALUES ( 'Allow Web Traffic','Default Filter installed with the application',1,1);

INSERT INTO filter_entry ( name,ethertype,ipprotocol,srcport,filterid, auditid) VALUES ('Allow Web Traffic','ip','tcp',80,1,1);
INSERT INTO filter_entry ( name,ethertype,ipprotocol,srcport,filterid, auditid) VALUES ('Allow Secure Web Traffic','ip','tcp',443,1,1);


INSERT INTO contract ( name,producerid,consumerid,auditid) VALUES ( 'Allow Database Traffic
',0,0,1);

INSERT INTO subject (name,description,contractid) VALUES ('Allow database traffic
','Default contract installed with the application',2);
 
INSERT INTO filter ( name,description,subjectid, auditid) VALUES ( 'Allow Database Traffic','Default Filter installed with the application',2,1);

INSERT INTO filter_entry ( name,ethertype,ipprotocol,srcport,filterid, auditid) VALUES ('Allow Database Traffic','ip','tcp',3306,2,1);

INSERT INTO contract ( name,producerid,consumerid,auditid) VALUES ( 'Allow App Traffic
',0,0,1);

INSERT INTO subject (name,description,contractid) VALUES ('Allow App traffic
','Default contract installed with the application',3);
 
INSERT INTO filter ( name,description,subjectid, auditid) VALUES ( 'Allow App Traffic','Default Filter installed with the application',3,1);

INSERT INTO filter_entry ( name,ethertype,ipprotocol,srcport,filterid, auditid) VALUES ('Allow App Traffic','ip','tcp',23,3,1);

INSERT INTO filter_entry ( name,ethertype,ipprotocol,srcport,filterid, auditid) VALUES ('Allow App Traffic','ip','tcp',3389,3,1);
