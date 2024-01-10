package org.example.dao;

import org.example.entity.Course;

import java.util.List;

public interface CourseDao {
    Course selectById(int id);
    List<Course> selectAll();
    List<Course> selectByTeacherId(int id);
    void insert(Course course);
//    void delete(int id);
//    void update(Course course);
}
