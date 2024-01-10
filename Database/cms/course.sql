create table course
(
    id                     int auto_increment
        primary key,
    course_name            varchar(25)  default '' not null,
    course_description     varchar(255) default '' null,
    prerequisite_course_id varchar(255)            null,
    teacher_id             int                     not null,
    max_capacity           int          default 0  not null,
    start_time             date                    null,
    end_time               date                    null,
    constraint course_user_id_fk
        foreign key (teacher_id) references user (id)
)
    comment '课程信息';

create index course_course_id_fk
    on course (prerequisite_course_id);

