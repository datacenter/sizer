--liquibase formatted sql



--changeset runOnChange Release23:1

UPDATE POLICY set NAME='Policy Template 1' WHERE ID=1;
UPDATE POLICY set NAME='Policy Template 2' WHERE ID=2;