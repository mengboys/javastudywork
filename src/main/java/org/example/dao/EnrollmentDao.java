package org.example.dao;

import org.example.entity.Enrollment;

import java.util.List;

public interface EnrollmentDao {
    List<Enrollment> selectCourseId(int courseId);
    List<Enrollment> selectUserId(int userId);
    boolean selectId(int userId,int courseId);
    Enrollment selectByID(int userId,int courseId);
    List<Enrollment> selectAll();
    boolean insert(Enrollment enrollment);
    boolean update(Enrollment enrollment);
//    Boolean deleteCourseId(int id);
}
