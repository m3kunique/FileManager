--liquibase formatted sql

--changeset m3kM:1
--comment first migration
CREATE TABLE Customer(
                         id SERIAL PRIMARY KEY,
                         login VARCHAR(64) UNIQUE NOT NULL,
                         password VARCHAR(64) NOT NULL
);
--rollback truncate table demo;

