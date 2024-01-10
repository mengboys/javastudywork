package org.example.service.Impl;

import org.example.Spring.Bean_DreamBo;
import org.example.Spring.Di_DreamBo;
import org.example.dao.CourseDao;
import org.example.dao.CourseReviewDao;
import org.example.dao.EnrollmentDao;
import org.example.dao.StudentDao;
import org.example.entity.Course;
import org.example.entity.CourseReview;
import org.example.entity.Enrollment;
import org.example.service.EnrollmentService;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Bean_DreamBo
public class EnrollmentServiceImpl implements EnrollmentService {
    @Di_DreamBo
    private EnrollmentDao enrollmentDao;
    @Di_DreamBo
    private CourseDao courseDao;
    @Di_DreamBo
    private StudentDao studentDao;
    @Di_DreamBo
    private CourseReviewDao courseReviewDao;

    private Scanner input = new Scanner(System.in);

    public void insert(int id) {
        Enrollment enrollment = new Enrollment();
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
        System.out.println("输入要选择的课程");
        int courseId = input.nextInt();
        Course course = courseDao.selectById(courseId);

        Date currentTime = new Date(System.currentTimeMillis());
        Date courseStartTime = course.getStartTime(); // 获取课程的开始时间

        // 检查选课是否已满
        if (enrollments.size() >= course.getMaxCapacity()) {
            System.out.println("选课已满，选课失败");
            return;
        }

        // 检查选课时间
        Date oneMonthBeforeCourseStart = new Date(courseStartTime.getTime() - 30 * 24 * 60 * 60 * 1000L); // 提前一个月
        if (currentTime.before(oneMonthBeforeCourseStart) || currentTime.after(courseStartTime)) {
            System.out.println("不在选课时间内，选课失败");
            return;
        }

        // 解析先修课程ID列表
        String prerequisiteCourseIds = course.getPrerequisiteId();
        if (prerequisiteCourseIds != null && !prerequisiteCourseIds.isEmpty()) {
            List<Integer> prerequisiteIds = Arrays.asList(prerequisiteCourseIds.split(",")) // 假设先修课程ID以逗号分隔
                    .stream()
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            // 检查先修课程是否完成且成绩大于60
            for (Integer prerequisiteId : prerequisiteIds) {
                boolean hasPassed = enrollmentDao.selectId(id, prerequisiteId);
                if (!hasPassed) {
                    System.out.println("未完成先修课程或成绩不够，选课失败");
                    return;
                }
            }
        }

        // 插入选课记录
        enrollment.setCourse_id(courseId);
        enrollment.setUser_id(id);
        if (enrollmentDao.insert(enrollment)) {
            System.out.println("选课成功");
        } else {
            System.out.println("选课失败");
        }
    }

    public void gradeUpdate(int id) {
        List<Enrollment> enrollments = enrollmentDao.selectUserId(id);
        Enrollment enrollment = new Enrollment();
        for (Enrollment e : enrollments) {
            System.out.println(e.toString());
        }
        System.out.println("输入要更新的选课记录：");
        enrollment = enrollmentDao.selectByID(id,this.input.nextInt());
        if (enrollment.getId() == 0) {
            System.out.println("未找到对应的选课记录");
        } else {

            System.out.println("找到选课记录：" + enrollment.toString());
            boolean flag = true;
            System.out.println("请输入新的成绩：");
            int newGrade = 0;
            while (flag) {
                newGrade = input.nextInt();
                if (newGrade >= 100 || newGrade <= 0) {
                    System.out.println("输入的成绩大于100或小于0");
                    flag = true;
                    System.out.println("请重新输入：");
                } else {
                    enrollment.setGrade(newGrade);
                    flag = false;
                }
            }
            enrollmentDao.update(enrollment);
        }
        System.out.println();
    }

    @Override
    public void gradeAll() {
        System.out.println("请输入要查询的课程的id");
        List<Enrollment> enrollments = enrollmentDao.selectCourseId(this.input.nextInt());
        for (Enrollment e : enrollments) {
            System.out.println("课程 " + courseDao.selectById(e.getCourse_id()).getCourseName() + "\t的学生 " + studentDao.selectById(e.getUser_id()).getName() + "\t成绩是 " + e.getGrade());
        }
    }

    @Override
    public void gradeByStudentID() {
        System.out.println("请输入要查询的学生的id");
        List<Enrollment> enrollments = enrollmentDao.selectUserId(this.input.nextInt());
        for (Enrollment e : enrollments) {
            System.out.println("学生 " + studentDao.selectById(e.getUser_id()).getName() + "\t的课程 " + courseDao.selectById(e.getCourse_id()).getCourseName() + "\t的成绩" + e.getGrade());
        }
    }

    @Override
    public void averageGrade() {
        List<Enrollment> enrollments = enrollmentDao.selectAll();

        // 使用Map来按课程分组选课信息
        Map<Integer, List<Integer>> gradesByCourse = new HashMap<>();
        Map<Integer, Integer> countByCourse = new HashMap<>();

        for (Enrollment enrollment : enrollments) {
            int courseId = enrollment.getCourse_id();
            int grade = enrollment.getGrade();

            if (!gradesByCourse.containsKey(courseId)) {
                gradesByCourse.put(courseId, new ArrayList<>());
                countByCourse.put(courseId, 0);
            }

            gradesByCourse.get(courseId).add(grade);
            countByCourse.put(courseId, countByCourse.get(courseId) + 1);
        }

        System.out.println("每门课程的平均成绩：");
        DecimalFormat df = new DecimalFormat("#.##"); // 格式化为小数点后两位
        for (Map.Entry<Integer, List<Integer>> entry : gradesByCourse.entrySet()) {
            int courseId = entry.getKey();
            List<Integer> grades = entry.getValue();

            int sum = 0;
            for (int grade : grades) {
                sum += grade;
            }

            int count = countByCourse.get(courseId);
            double averageGrade = (double) sum / count;

            // 格式化为小数点后两位并输出
            System.out.println("课程 " + courseDao.selectById(courseId).getCourseName() + " 的平均成绩：" + df.format(averageGrade));
        }
    }

    @Override
    public void failGrade() {
        List<Enrollment> enrollments = enrollmentDao.selectAll();

        // 使用Map来记录每门课程不及格的人数
        Map<Integer, Integer> failedCountByCourse = new HashMap<>();

        for (Enrollment enrollment : enrollments) {
            int courseId = enrollment.getCourse_id();
            int grade = enrollment.getGrade();

            if (grade < 60) {
                failedCountByCourse.put(courseId, failedCountByCourse.getOrDefault(courseId, 0) + 1);
            }
        }

        System.out.println("每门课程不及格（成绩低于60）的人数数量：");
        for (Map.Entry<Integer, Integer> entry : failedCountByCourse.entrySet()) {
            int courseId = entry.getKey();
            int failedCount = entry.getValue();
            System.out.println("课程 " + courseDao.selectById(courseId).getCourseName() + " 不及格的人数数量：" + failedCount);
        }
    }

    @Override
    public void gradeUpdateByCourse(int id) {
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
        System.out.println("请选择要上传成绩的课程id:");
        int courseId = this.input.nextInt();
        List<Enrollment> enrollments = enrollmentDao.selectCourseId(courseId);
        for (Enrollment e : enrollments) {
            System.out.println(e.toString());
        }
        System.out.println("要更新的学生id：");
        Enrollment enrollment = enrollmentDao.selectByID(this.input.nextInt(), courseId);
        System.out.println("找到选课记录：" + enrollment.toString());
        boolean flag = true;
        System.out.println("请输入新的成绩：");
        int newGrade = 0;
        while (flag) {
            newGrade = input.nextInt();
            if (newGrade >= 100 || newGrade <= 0) {
                System.out.println("输入的成绩大于100或小于0");
                flag = true;
                System.out.println("请重新输入：");
            } else {
                enrollment.setGrade(newGrade);
                flag = false;
            }
        }
        enrollmentDao.update(enrollment);
    }

    @Override
    public void selectByUserId(int id) {
        List<Enrollment> enrollments = enrollmentDao.selectUserId(id);
        for (Enrollment e : enrollments) {
            System.out.println("课程：" + courseDao.selectById(e.getCourse_id()).getCourseName() + "\t选课时间：" + e.getDate());
        }
    }

    @Override
    public void selectGrade(int id) {
        List<Enrollment> enrollments = enrollmentDao.selectUserId(id);
        for (Enrollment e : enrollments) {
            if (e.getGrade() != 0)
                System.out.println("课程：" + courseDao.selectById(e.getCourse_id()).getCourseName() + "\t成绩：" + e.getGrade());
            else System.out.println("课程：" + courseDao.selectById(e.getCourse_id()).getCourseName() + "\t课程未结束");
        }
    }

    @Override
    public void enrollmentInfo() {
        System.out.println("     现有选课信息");
        List<Enrollment> enrollments = enrollmentDao.selectAll();
        List<Course> courses = courseDao.selectAll();

        for (Course course : courses) {
            int i = 0; // 选课人数
            float j = 0; // 评分总和
            int countUnrated = 0; // 未完成评分的人数

            for (Enrollment e : enrollments) {
                if (e.getCourse_id() == course.getId()) {
                    i++;

                    CourseReview courseReview = courseReviewDao.selectById(e.getUser_id(), course.getId());

                    // 检查评分是否完成
                    if (courseReview != null && courseReview.getRating() != 0) {
                        j += courseReview.getRating();
                    } else {
                        countUnrated++;
                    }
                }
            }

            if (i > 0) {
                float averageRating = (i - countUnrated) > 0 ? j / (i - countUnrated) : 0;

                if (countUnrated > 0) {
                    System.out.println("课程：" + course.getCourseName() + " \t容量：" + course.getMaxCapacity() + " \t已有 " + i + " 人\t尚有 " + countUnrated + " 人未评分");
                } else {
                    System.out.println("课程：" + course.getCourseName() + " \t容量：" + course.getMaxCapacity() + " \t已有 " + i + " 人\t平均评分：" + averageRating);
                }
            }
        }
    }
}
