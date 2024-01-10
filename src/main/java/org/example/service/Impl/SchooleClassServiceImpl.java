package org.example.service.Impl;

import org.example.Spring.Bean_DreamBo;
import org.example.Spring.Di_DreamBo;
import org.example.dao.SchoolClassDao;
import org.example.dao.StudentDao;
import org.example.entity.SchoolClass;
import org.example.entity.Student;
import org.example.service.SchoolClassService;

import java.util.List;
import java.util.Scanner;

@Bean_DreamBo
public class SchooleClassServiceImpl implements SchoolClassService {
    @Di_DreamBo
    private SchoolClassDao schoolClassDao;

    @Di_DreamBo
    private StudentDao studentDao;
    private Scanner input = new Scanner(System.in);

    @Override
    public void viewClass(int id) {
        List<SchoolClass> schoolClasses = schoolClassDao.selectByCounsellorId(id);
        System.out.println("您管理的班级：");
        for (SchoolClass s : schoolClasses) {
            System.out.println(s.toString());
        }
        System.out.println("要查看的班级的id：");
        SchoolClass schoolClass = schoolClassDao.selectById(this.input.nextInt());
        List<Student> students = studentDao.selectClassId(schoolClass.getId());
        System.out.println("班级学生信息：");
        for (Student s : students) {
            System.out.println(s.toString());
        }
    }

    @Override
    public void manageClass(int id) {
        List<SchoolClass> schoolClasses = schoolClassDao.selectByCounsellorId(id);
        System.out.println("您管理的班级：");
        for (SchoolClass s : schoolClasses) {
            System.out.println(s.toString());
        }
        System.out.println("要查看的班级的id：");
        int classId = this.input.nextInt();
        SchoolClass schoolClass = schoolClassDao.selectById(classId);
        System.out.println("选择你的操作");
        System.out.println("1.增加学生");
        System.out.println("2.删除学生");
        switch (this.input.nextInt()) {
            case 1: {
                System.out.println("添加学生的Id：");
                Student student = studentDao.selectById(this.input.nextInt());
                student.setSchoolClassId(schoolClass.getId());
                studentDao.updateById(student);
                break;
            }
            case 2: {
                List<Student> students = studentDao.selectClassId(schoolClass.getId());
                System.out.println("班级学生信息：");
                for (Student s : students) {
                    System.out.println(s.toString());
                }
                System.out.println("要删除的学生的id：");
                Student student = studentDao.selectById(this.input.nextInt());
                student.setSchoolClassId(0);
                studentDao.updateById(student);
                break;
            }
            default:{
                System.out.println("输入错误将返回上一菜单！");
            }
        }
    }
}
