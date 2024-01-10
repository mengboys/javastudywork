create table course_review
(
    id         int auto_increment
        primary key,
    user_id    int default 0 not null,
    courser_id int default 0 not null,
    rating     int default 0 not null,
    comment    varchar(255)  null,
    date       date          null,
    constraint course_review_user_id_courser_id_uindex
        unique (user_id, courser_id),
    constraint class_review_course_id_fk
        foreign key (courser_id) references course (id),
    constraint class_review_user_id_fk
        foreign key (user_id) references user (id)
)
    comment '课程评论';

