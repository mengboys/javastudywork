create table student
(
    id        int                    not null
        primary key,
    class_id  int                    not null,
    address   varchar(255)           null,
    introduce varchar(255)           null,
    gpa       float       default 0  null,
    name_     varchar(25) default '' not null,
    constraint student_school_class_id_fk
        foreign key (class_id) references school_class (id),
    constraint student_user_id_fk
        foreign key (id) references user (id)
)
    comment '学生的具体信息';

