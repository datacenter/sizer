--liquibase formatted sql



--changeset runOnChange Release21:1


ALTER TABLE "devices" DROP COLUMN "domain";
ALTER TABLE "devices" ADD "is_spine_termination_done" Boolean;