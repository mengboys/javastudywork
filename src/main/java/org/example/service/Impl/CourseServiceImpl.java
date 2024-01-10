package org.example.service.Impl;

import org.example.Spring.Bean_DreamBo;
import org.example.Spring.Di_DreamBo;
import org.example.dao.CourseDao;
import org.example.dao.EnrollmentDao;
import org.example.dao.UserDao;
import org.example.entity.Course;
import org.example.entity.Enrollment;
import org.example.service.CourseService;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Bean_DreamBo
public class CourseServiceImpl implements CourseService {
    @Di_DreamBo
    private CourseDao courseDao;
    @Di_DreamBo
    private UserDao userDao;
    @Di_DreamBo
    private EnrollmentDao enrollmentDao;

    private final Scanner input = new Scanner(System.in);

    public void selectAll() {
        List<Enrollment> enrollments = enrollmentDao.selectAll();
        List<Course> courses = courseDao.selectAll();
        for (Course course : courses) {
            int i = 0;
            for (Enrollment e : enrollments) {
                if (e.getCourse_id() == course.getId()) {
                    i++;
                }
            }
            System.out.println("课程：" + course.getCourseName() + "\t容量：" + course.getMaxCapacity() + " \t已有 " + i);
        }
    }

    @Override
    public void createCourse(int id) {
        Course course = new Course();
        Scanner input = new Scanner(System.in);
        System.out.println("请输入课程名：");
        course.setCourseName(input.next());
        System.out.println("请输入课程描述：");
        course.setDescription(input.next());

        List<Integer> prerequisiteCourses = new ArrayList<>();
        boolean addMoreCourses = true;

        while (addMoreCourses) {
            System.out.println("请输入先修课程ID（输入0退出）：");
            int prerequisiteCourseId = input.nextInt();

            if (prerequisiteCourseId == 0) {
                addMoreCourses = false;
            } else {
                prerequisiteCourses.add(prerequisiteCourseId);
            }
        }
        // 将 List 转换为逗号分隔的字符串
        String prerequisiteCoursesString = prerequisiteCourses.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        course.setPrerequisiteId(prerequisiteCoursesString);

        course.setTeacherId(id);

        System.out.println("输入课程最大学生容量：");
        course.setMaxCapacity(this.input.nextInt());

        boolean i = true;
        while (i) {
            Date startDate = null;
            boolean isValidInput = false;

            while (!isValidInput) {
                System.out.println("请输入课程开始日期（YYYY-MM-DD）：");
                String startDateString = input.next();

                try {
                    // 将输入的字符串转换为 LocalDate 对象
                    LocalDate parsedDate = LocalDate.parse(startDateString);
                    // 将 LocalDate 对象转换为 java.sql.Date 对象
                    startDate = java.sql.Date.valueOf(parsedDate);
                    System.out.println("输入的开始日期为：" + startDate);
                    isValidInput = true; // 如果日期格式有效，跳出循环
                } catch (DateTimeParseException e) {
                    System.out.println("无效的日期格式，请按照YYYY-MM-DD格式输入日期。");
                }
            }

            Date endDate = null;
            isValidInput = false;

            while (!isValidInput) {
                System.out.println("请输入课程结束日期（YYYY-MM-DD）：");
                String endDateString = input.next();

                try {
                    // 将输入的字符串转换为 LocalDate 对象
                    LocalDate parsedDate = LocalDate.parse(endDateString);
                    // 将 LocalDate 对象转换为 java.sql.Date 对象
                    endDate = java.sql.Date.valueOf(parsedDate);
                    System.out.println("输入的开始日期为：" + endDate);
                    isValidInput = true; // 如果日期格式有效，跳出循环
                } catch (DateTimeParseException e) {
                    System.out.println("无效的日期格式，请按照YYYY-MM-DD格式输入日期。");
                }
            }

            if (startDate.toLocalDate().isBefore(endDate.toLocalDate())) {
                course.setStartTime(startDate);
                course.setEndTime(endDate);
                courseDao.insert(course);
                System.out.println("插入成功");
                i = false;
            } else {
                System.out.println("开始日期在结束日期之后出现！");
            }
        }
    }

    @Override
    public void currentCourseInfo(int id) {
        List<Course> courses = courseDao.selectByTeacherId(id);
        java.util.Date currentTime = new java.util.Date(System.currentTimeMillis());
        java.util.Date sevenMonthBeforeCurrentTime = new java.util.Date(currentTime.getTime() - 30 * 24 * 60 * 60 * 7 * 1000L); // 提前七个月
        System.out.println("当前上课信息");
        for (Course c : courses) {
            java.util.Date courseStartTime = c.getStartTime(); // 获取课程的开始时间
            if (courseStartTime.after(sevenMonthBeforeCurrentTime)) {
                System.out.println(c.toString());
            }
        }
    }

    @Override
    public void courseInfo(int id) {
        List<Course> courses = courseDao.selectByTeacherId(id);
        System.out.println("历史上课信息");
        for (Course c : courses) {
            System.out.println(c.toString());

        }
    }
}
