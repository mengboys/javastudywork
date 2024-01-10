package org.example.dao;

import org.example.entity.SchoolClass;

import java.util.List;

public interface SchoolClassDao {
    //    SchoolClass selectAll();
    SchoolClass selectById(int id);

    List<SchoolClass> selectByCounsellorId(int id);
//    void insert(SchoolClass schoolClass);
//    void update(SchoolClass schoolClass);
//    void delete(int id);
}
