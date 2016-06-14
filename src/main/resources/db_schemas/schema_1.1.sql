--liquibase formatted sql



--changeset runOnChange maplelabs:1


CREATE TABLE aci_user
(
	id serial NOT NULL,
	userid text,
	user_profile text,
	CONSTRAINT aci_user_pkey PRIMARY KEY(userId) 
);


--rollback
