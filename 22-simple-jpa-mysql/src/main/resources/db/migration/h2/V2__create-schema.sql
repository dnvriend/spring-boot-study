drop table people if exists;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;
create table people (id bigint not null, age integer, date_of_birth date, created timestamp, updated timestamp, name varchar(255), primary key (id));