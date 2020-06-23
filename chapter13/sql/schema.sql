drop database if exists STOREDB;
create database STOREDB;
use STOREDB;

create table CUSTOMERS (
  ID bigint not null auto_increment primary key,
  NAME varchar(16) not null,
  AGE INT,
  ADDRESS varchar(255)
);

insert into CUSTOMERS(ID,NAME,AGE,ADDRESS) values(1, 'С��',23, '����');
insert into CUSTOMERS(ID,NAME,AGE,ADDRESS) values(2,'С��',29, '���');
insert into CUSTOMERS(ID,NAME,AGE,ADDRESS) values(3,'С��',33, 'ɽ��');

