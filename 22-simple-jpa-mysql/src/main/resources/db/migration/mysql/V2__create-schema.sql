drop table if exists hibernate_sequence;
drop table if exists people;
create table hibernate_sequence (next_val bigint) engine=MyISAM;
insert into hibernate_sequence values ( 1 );
create table people (id bigint not null, age integer, date_of_birth date, created datetime, updated datetime, name varchar(255), primary key (id)) engine=MyISAM;