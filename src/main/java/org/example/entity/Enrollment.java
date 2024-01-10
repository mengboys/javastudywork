package org.example.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Enrollment {
    private int id;
    private int user_id;
    private int course_id;
    private Date date;
    private int grade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", course_id=" + course_id +
                ", date=" + date +
                ", grade=" + grade +
                '}';
    }
}
