package org.example.dao;

import org.example.entity.Student;

import java.util.List;

public interface StudentDao {
    List<Student> selectAll();

    Student selectById(int id);

    List<Student> selectClassId(int id);
    void updateById(Student student);
//    void delete(int id);
//    void insert(Student student);

}
