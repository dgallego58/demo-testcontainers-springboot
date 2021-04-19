create schema if not exists demo;

create type demo.doc_type as enum (
    'CC', 'NIT', 'DNI', 'PP', 'CE'
    );
CREATE CAST (varchar AS demo.doc_type) WITH INOUT AS IMPLICIT;

create table if not exists demo.users
(
    id        bigserial   not null,
    name      varchar(30) not null,
    last_name varchar(20) not null,
    doc_type  demo.doc_type default 'CC',
    dni       varchar(45) not null,
    age       integer,
    career    varchar(50),
    primary key (id)
);
create sequence if not exists demo.user_seq start 1;
create index users_name_idx on demo.users (name);