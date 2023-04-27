DROP TABLE IF EXISTS t_user;
create table t_user (
    ID bigint auto_increment not null,
    NAME varchar(100) not null,
    LASTNAME varchar(100) not null
);
insert into t_user(ID,NAME,LASTNAME) values(-3,'Alice','lastname');
insert into t_user(ID,NAME,LASTNAME) values(-2,'Alice','lastname');
insert into t_user(ID,NAME,LASTNAME) values(-1,'Victor','lastname');