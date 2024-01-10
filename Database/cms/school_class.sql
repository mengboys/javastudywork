create table school_class
(
    id            int auto_increment
        primary key,
    class_name    varchar(25) default '' not null,
    counsellor_id int         default 0  not null,
    constraint school_class_user_id_fk
        foreign key (counsellor_id) references user (id)
)
    comment '行政班';

