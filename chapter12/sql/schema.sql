drop database if exists STOREDB;
create database STOREDB;
use STOREDB;

create table CUSTOMERS (
  ID bigint not null auto_increment primary key,
  NAME varchar(16) not null,
  AGE INT,
  ADDRESS varchar(255)
);

create table ORDERS (
  ID bigint not null auto_increment primary key,
  ORDER_NUMBER varchar(16) not null,
  PRICE FLOAT,
  CUSTOMER_ID bigint,
  foreign key(CUSTOMER_ID) references CUSTOMERS(ID)
);

create table ACCOUNTS (
   ID bigint not null,
   NAME varchar(15),
   BALANCE decimal(10,2),
   primary key (ID) 
)  engine=INNODB;


insert into CUSTOMERS(ID,NAME,AGE,ADDRESS) values(1, '小张',23, '北京');
insert into CUSTOMERS(ID,NAME,AGE,ADDRESS) values(2,'小红',29, '天津');
insert into CUSTOMERS(ID,NAME,AGE,ADDRESS) values(3,'小丁',33, '山东');

insert into ORDERS(ID,ORDER_NUMBER,PRICE,CUSTOMER_ID) values(1, '小张_001',100.12, 1);
insert into ORDERS(ID,ORDER_NUMBER,PRICE,CUSTOMER_ID) values(2, '小张_002',200.32, 1);
insert into ORDERS(ID,ORDER_NUMBER,PRICE,CUSTOMER_ID) values(3, '小红_001',88.44, 2);
