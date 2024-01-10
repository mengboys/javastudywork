package org.example.service;

public interface EnrollmentService {
    void insert(int id);
    void gradeUpdate(int id);
    void gradeAll();
    void gradeByStudentID();
    void averageGrade();
    void failGrade();
    void gradeUpdateByCourse(int id);
    void selectByUserId(int id);
    void selectGrade(int id);
    void enrollmentInfo();
}
