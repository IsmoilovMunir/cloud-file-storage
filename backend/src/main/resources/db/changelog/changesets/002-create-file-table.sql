--liquibase formatted sql

--changeset munir:2
create table file(
    id bigserial primary key,
    user_id bigint,
    original_name varchar,
    minio_path varchar not null,
    size bigint,
    created_at timestamp,
    foreign key (user_id) references users(id)
);