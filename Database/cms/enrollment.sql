create table enrollment
(
    id        int auto_increment
        primary key,
    user_id   int           not null,
    course_id int           not null,
    date      date          null,
    grade     int default 0 null,
    constraint unique_user_course
        unique (user_id, course_id),
    constraint enrollment_course_id_fk
        foreign key (course_id) references course (id),
    constraint enrollment_user_id_fk
        foreign key (user_id) references user (id)
)
    comment '选课信息';

