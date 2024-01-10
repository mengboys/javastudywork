package org.example.service.Impl;

import org.example.Spring.Bean_DreamBo;
import org.example.Spring.Di_DreamBo;
import org.example.dao.CourseDao;
import org.example.dao.SchoolClassDao;
import org.example.dao.StudentDao;
import org.example.dao.UserDao;
import org.example.entity.Student;
import org.example.service.StudentService;

import java.text.DecimalFormat;
import java.util.*;

@Bean_DreamBo
public class StudenSeviceImpl implements StudentService {
    @Di_DreamBo
    private StudentDao studentDao;
    @Di_DreamBo
    private UserDao userDao;
    @Di_DreamBo
    private CourseDao courseDao;
    @Di_DreamBo
    private SchoolClassDao schoolClassDao;

    private Scanner input = new Scanner(System.in);

    @Override
    public void selectAll() {
        List<Student> students = studentDao.selectAll();
        System.out.println("            学生信息展示              ");
        System.out.println("            1.按班级排序              ");
        System.out.println("            2.按学分绩排序            ");
        System.out.println("\n\n请选择具体的操作：");
        switch (input.nextInt()) {
            case 1: {
                Collections.sort(students, Comparator.comparingInt(Student::getSchoolClassId));
                break;
            }
            case 2: {
                Collections.sort(students, Comparator.comparingDouble(Student::getGpa).reversed());
                break;
            }
            default:
                System.out.println("请选择有效的操作！");
                return;
        }
        for (Student s : students) {
            System.out.println(s.toString());
        }
    }

    @Override
    public void GPA2() {
        List<Student> students = studentDao.selectAll();
//        List<Student> studentsBelowGPA2 = new ArrayList<>();
//        如果要列表可以这样返回
        System.out.println("GPA低于2.0的学生列表：");
        for (Student student : students) {
            if (student.getGpa() < 2.0) {
//                studentsBelowGPA2.add(student);
                System.out.println(student.toString());
            }
        }
    }

    @Override
    public void averageGpa() {
        List<Student> students = studentDao.selectAll();

        // 使用Map来按班级分组学生
        Map<Integer, List<Student>> studentsByClass = new HashMap<>();

        // 将学生按班级放入Map中
        for (Student student : students) {
            int classId = student.getSchoolClassId();
            if (!studentsByClass.containsKey(classId)) {
                studentsByClass.put(classId, new ArrayList<>());
            }
            studentsByClass.get(classId).add(student);
        }

        System.out.println("每个班级的平均GPA：");
        for (Map.Entry<Integer, List<Student>> entry : studentsByClass.entrySet()) {
            int classId = entry.getKey();
            List<Student> studentsInClass = entry.getValue();

            double totalGpa = 0.0;
            for (Student student : studentsInClass) {
                totalGpa += student.getGpa();
            }

            // 计算平均GPA
            DecimalFormat df = new DecimalFormat("#.##");
            double averageGpa = totalGpa / studentsInClass.size();
            System.out.println("班级 " + schoolClassDao.selectById(classId).getClassName() + " 的平均GPA：" + df.format(averageGpa));
        }
    }

    @Override
    public void maxGPA() {
        List<Student> students = studentDao.selectAll();

        // 使用Map来存储每个班级最高GPA的学生
        Map<Integer, Student> maxGpaStudentsByClass = new HashMap<>();

        for (Student student : students) {
            int classId = student.getSchoolClassId();
            if (!maxGpaStudentsByClass.containsKey(classId) || maxGpaStudentsByClass.get(classId).getGpa() < student.getGpa()) {
                maxGpaStudentsByClass.put(classId, student);
            }
        }

        System.out.println("每个班级最高GPA的学生信息：");
        for (Map.Entry<Integer, Student> entry : maxGpaStudentsByClass.entrySet()) {
            int classId = entry.getKey();
            Student maxGpaStudent = entry.getValue();
            System.out.println("班级 " + classId + " 最高GPA的学生信息：" + maxGpaStudent.toString());
        }
    }


}

