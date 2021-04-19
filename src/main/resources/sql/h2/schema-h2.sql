create schema if not exists testing;
create table testing.users
(
    id        bigserial  not null,
    "name"      varchar(25) not null,
    last_name varchar(25) not null,
    doc_type  varchar(3)  not null,
    dni       varchar(20) not null,
    age       integer     not null,
    career    varchar(30) not null
);