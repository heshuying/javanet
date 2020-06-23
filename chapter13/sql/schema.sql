drop database if exists STOREDB;
create database STOREDB;
use STOREDB;

create table CUSTOMERS (
  ID bigint not null auto_increment primary key,
  NAME varchar(16) not null,
  AGE INT,
  ADDRESS varchar(255)
);

insert into CUSTOMERS(ID,NAME,AGE,ADDRESS) values(1, '小张',23, '北京');
insert into CUSTOMERS(ID,NAME,AGE,ADDRESS) values(2,'小红',29, '天津');
insert into CUSTOMERS(ID,NAME,AGE,ADDRESS) values(3,'小丁',33, '山东');

