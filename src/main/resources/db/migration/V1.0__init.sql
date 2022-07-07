create schema if not exists otus;

create sequence otus.seq_user;

create table otus.users
(
    id        numeric not null primary key,
    username  varchar(256),
    firstname varchar(256),
    lastname  varchar(256),
    email     varchar(256),
    phone     varchar(256)
);


