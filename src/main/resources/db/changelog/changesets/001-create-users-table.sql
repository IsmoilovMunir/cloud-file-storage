--liquibase formatted sql

--changeset munir:1

create table users (
    id bigserial primary key ,
    username varchar not null,
    password varchar not null
);