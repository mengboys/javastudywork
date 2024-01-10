create table user
(
    id       int auto_increment
        primary key,
    role     int          default 0   not null comment '角色类型',
    username varchar(25)  default ''  not null,
    password varchar(255) default ''  not null,
    name_    varchar(25)  default ''  not null,
    ID_card  varchar(18)  default '0' not null
)
    comment '储存用户信息';

