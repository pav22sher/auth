--liquibase formatted sql

--changeset Scherbakov Pavel:create.sql

create table users
(
    username varchar(255) not null unique
            primary key,
    password varchar(255) not null,
    enabled boolean not null default true
);

create table authorities
(
    username varchar(255) not null references users(username),
    authority varchar(255) not null,
    unique (username, authority)
)