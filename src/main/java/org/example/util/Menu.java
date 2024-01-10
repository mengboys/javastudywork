package org.example.util;

import org.example.Spring.AnnotationApplicationContext;
import org.example.Spring.ApplicationContext;
import org.example.entity.Enrollment;
import org.example.service.*;

import java.util.Scanner;
//菜单界面
public class Menu {
    private Scanner input = null;
    ApplicationContext applicationContext = new AnnotationApplicationContext("org.example");
    UserService userService = (UserService) applicationContext.getBean(UserService.class);
    ProxyFactory userFactory = new ProxyFactory(userService, "UserProxy"); // 添加描述或名称
    UserService userProxy = (UserService) userFactory.getProxy();

    StudentService studentService = (StudentService) applicationContext.getBean(StudentService.class);
    ProxyFactory studentFactory = new ProxyFactory(studentService, "StudentProxy"); // 添加描述或名称
    StudentService studentProxy = (StudentService) studentFactory.getProxy();

    CourseService courseService = (CourseService) applicationContext.getBean(CourseService.class);
    ProxyFactory courseFactory = new ProxyFactory(courseService, "CourseProxy"); // 添加描述或名称
    CourseService courseProxy = (CourseService) courseFactory.getProxy();

    EnrollmentService enrollmentService = (EnrollmentService) applicationContext.getBean(EnrollmentService.class);
    ProxyFactory enrollmentFactory = new ProxyFactory(enrollmentService, "EnrollmentProxy"); // 添加描述或名称
    EnrollmentService enrollmentProxy = (EnrollmentService) enrollmentFactory.getProxy();
    SchoolClassService schoolClassService = (SchoolClassService) applicationContext.getBean(SchoolClassService.class);
    ProxyFactory schoolClassFactory = new ProxyFactory(schoolClassService, "SchoolClassProxy"); // 添加描述或名称
    SchoolClassService schoolClassProxy = (SchoolClassService) schoolClassFactory.getProxy();

    CourseReviewService CourseReviewService = (CourseReviewService) applicationContext.getBean(CourseReviewService.class);
    ProxyFactory CourseReviewFactory = new ProxyFactory(CourseReviewService, "CourseReviewProxy"); // 添加描述或名称
    CourseReviewService courseReviewProxy = (CourseReviewService) CourseReviewFactory.getProxy();
    private int id;

    public Menu() {
        boolean isContinue = true;
        this.input = new Scanner(System.in);
        while (isContinue) {
            isContinue = this.LoginMenu();
        }
    }

    public boolean LoginMenu() {
        System.out.println("\n\n            学生信息管理                 ");
        System.out.println("            1.用户登录                  ");
        System.out.println("            2.修改密码                  ");
        System.out.println("            3、退出程序                 ");
        System.out.println("\n\n请选择具体的操作：");
        switch (this.input.nextInt()) {
            case 1: {
                boolean flag = true;
                System.out.println("请输入账号：");
                this.id = this.input.nextInt();
                switch (userProxy.Login(id)) {
                    case 0: {
                        while (flag) {
                            flag = this.StudntMenu();
                        }
                        break;
                    }
                    case 1: {
                        while (flag) {
                            flag = this.TeacherMenu();
                        }
                    }
                    case 2: {
                        while (flag) {
                            flag = this.CounselorMenu();
                        }
                    }
                    case 3: {
                        while (flag) {
                            flag = this.RootMenuDB();
                        }
                        break;
                    }
                    case -1: {
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
            case 2: {
                if (userProxy.ChangePassword()) return true;
                else return true;
            }
            case 3: {
                return false;
            }
            default: {
                return true;
            }
        }
        return false;
    }

    public boolean StudntMenu() {
        System.out.println("\n\n            学生信息管理(学生端)      ");
        System.out.println("            1.学生选课                  ");
        System.out.println("            2.选课信息展示              ");
        System.out.println("            3.选课历史                 ");
        System.out.println("            4.选课成绩                 ");
        System.out.println("            5.选课评价                 ");
        System.out.println("            6.退出程序                 ");
        System.out.println("\n\n请选择具体的操作：");
        switch (this.input.nextInt()) {
            case 1: {
                enrollmentProxy.insert(this.id);
                return true;
            }
            case 2: {
                courseProxy.selectAll();
                return true;
            }
            case 3: {
                enrollmentProxy.selectByUserId(id);
                return true;
            }
            case 4: {
                enrollmentProxy.selectGrade(id);
                return true;
            }
            case 5: {
                courseReviewProxy.reviewById(id);
                return true;
            }
            case 6: {
                return false;
            }
            default: {
                return true;
            }
        }
    }

    public boolean TeacherMenu() {
        System.out.println("\n\n            学生信息管理(教师端)      ");
        System.out.println("            1.课程信息创建              ");
        System.out.println("            2.上课信息                  ");
        System.out.println("            3.历史上课信息                  ");
        System.out.println("            4.录入成绩                  ");
        System.out.println("            5.退出程序                 ");
        System.out.println("\n\n请选择具体的操作：");
        switch (this.input.nextInt()) {
            case 1: {
                courseProxy.createCourse(id);
                return true;
            }
            case 2: {
                courseProxy.currentCourseInfo(id);
                return true;
            }
            case 3: {
                courseProxy.courseInfo(id);
                return true;
            }
            case 4: {
                enrollmentProxy.gradeUpdateByCourse(id);
                return true;
            }
            case 5: {
                return false;
            }
            default: {
                return true;
            }
        }
    }

    public boolean CounselorMenu() {
        System.out.println("\n\n            学生信息管理(辅导员端)         ");
        System.out.println("            1.当前班级学生信息              ");
        System.out.println("            2.管理学生                  ");
        System.out.println("            3.退出程序                 ");
        System.out.println("\n\n请选择具体的操作：");
        switch (this.input.nextInt()) {
            case 1: {
                schoolClassProxy.viewClass(id);
                return true;
            }
            case 2: {
                schoolClassProxy.manageClass(id);
                return true;
            }
            case 3: {
                return false;
            }
            default: {
                return true;
            }
        }
    }


    public boolean RootMenu() {//java版
        System.out.println("\n\n            学生信息管理(root端)         ");
        System.out.println("            1.学生信息展示              ");
        System.out.println("            2.课程信息展示                  ");
        System.out.println("            3.录入成绩                  ");
        System.out.println("            4.GPA相关                  ");
        System.out.println("            5.成绩相关                  ");
        System.out.println("            6.退出程序                 ");
        System.out.println("\n\n请选择具体的操作：");
        switch (this.input.nextInt()) {
            case 1: {
                studentProxy.selectAll();
                return true;
            }
            case 2: {
                courseProxy.selectAll();
                return true;
            }
            case 3: {
                System.out.println("输入要更新的学生的id");
                enrollmentProxy.gradeUpdate(input.nextInt());
                return true;
            }
            case 4: {
                boolean i = true;
                while (i) {
                    i = this.GpaMenu();
                }
            }
            case 5: {
                boolean i = true;
                while (i) {
                    i = this.gradeMenu();
                }
            }
            case 6: {
                return false;
            }
            default: {
                return true;
            }
        }
    }


    public boolean GpaMenu() {
        System.out.println("\n\n            学生信息管理(GPA)         ");
        System.out.println("            1.GPA低于2.0的学生列表         ");
        System.out.println("            1.各班级平均GPA               ");
        System.out.println("            3.各班级GPA最高的学生          ");
        System.out.println("            4.退出程序                 ");
        System.out.println("\n\n请选择具体的操作：");
        switch (this.input.nextInt()) {
            case 1: {
                studentProxy.GPA2();
                return true;
            }
            case 2: {
                studentProxy.averageGpa();
                return true;
            }
            case 3: {
                studentProxy.maxGPA();
                return true;
            }
            case 4: {
                return false;
            }
            default: {
                return true;
            }
        }
    }

    public boolean gradeMenu() {
        System.out.println("\n\n            学生信息管理(grade)  ");
        System.out.println("            1.课程全体成绩           ");
        System.out.println("            1.学生成绩单             ");
        System.out.println("            3.课程平均成绩           ");
        System.out.println("            4.课程不及格人数          ");
        System.out.println("            5.退出程序               ");
        System.out.println("\n\n请选择具体的操作：");
        switch (this.input.nextInt()) {
            case 1: {
                enrollmentProxy.gradeAll();
                return true;
            }
            case 2: {
                enrollmentProxy.gradeByStudentID();
                return true;
            }
            case 3: {
                enrollmentProxy.averageGrade();
                return true;
            }
            case 4: {
                enrollmentProxy.failGrade();
                return true;
            }
            case 5: {
                return false;
            }
            default: {
                return true;
            }
        }
    }

    public boolean RootMenuDB() {
        System.out.println("\n\n            学生信息管理(root端)     ");
        System.out.println("            1.权限管理                  ");
        System.out.println("            2.人员管理                  ");
        System.out.println("            3.选课信息                  ");
        System.out.println("            4.退出程序                 ");
        System.out.println("\n\n请选择具体的操作：");
        switch (this.input.nextInt()) {
            case 1: {
                userProxy.roleChange();
                return true;
            }
            case 2: {
                boolean f = true;
                System.out.println("\n\n            人员管理(root端)     ");
                System.out.println("            1.增加人员                  ");
                System.out.println("            2.删除人员                  ");
                System.out.println("            3.返回                  ");
                System.out.println("\n\n请选择具体的操作：");
                while (f) {
                    switch (this.input.nextInt()) {
                        case 1: {
                            userProxy.insert();
                            f = false;
                            break;
                        }
                        case 2: {
                            userProxy.delete();
                            f = false;
                            break;
                        }
                        case 3: {
                            f = false;
                            break;
                        }
                        default: {
                            f = true;
                            break;
                        }
                    }
                }
                return true;
            }
            case 3: {
                enrollmentProxy.enrollmentInfo();
                return true;
            }
            case 4: {
                return false;
            }
            default: {
                return true;
            }
        }
    }
}
