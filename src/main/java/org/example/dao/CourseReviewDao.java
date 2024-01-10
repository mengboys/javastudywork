package org.example.dao;

import org.example.entity.CourseReview;

public interface CourseReviewDao {
//    ClassReview selecUserId(int userId);
//    ClassReview selectCourseId(int courseId);
    void insert(CourseReview classReview);
    CourseReview selectById(int userId,int CourseId);
//    void delete(int id);
    void update(CourseReview classReview);
}
