--liquibase formatted sql



--changeset runOnChange Release10:1


CREATE TABLE aci_project
(
  id serial NOT NULL,
  userId text,
  name text ,
  type text,
  customer_name text,
  sales_contact text,
  opportunity text,
  account text,
  logical_requirement text,
  logical_requirement_summary text,
  logical_result_summary text,
  physical_requirement text,
  physical_requirement_summary text,
  physical_result_summary text,
  created_by text,
  description text,
  created_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_updated_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  unique (userId, name),
  CONSTRAINT project_pkey PRIMARY KEY (id)
);


--rollback
