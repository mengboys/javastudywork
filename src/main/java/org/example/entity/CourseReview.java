package org.example.entity;

import java.sql.Date;

public class CourseReview {
    private int id;
    private int userId;
    private int courseId;
    private int rating;
    private String comment;
    private Date date;

    @Override
    public String toString() {
        return "ClassReview{" +
                "id=" + id +
                ", userId=" + userId +
                ", courseId=" + courseId +
                ", rating=" + rating +
                ", commont='" + comment + '\'' +
                ", date=" + date +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
