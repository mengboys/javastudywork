package org.example.service.Impl;

import org.example.Spring.Bean_DreamBo;
import org.example.Spring.Di_DreamBo;
import org.example.dao.CourseDao;
import org.example.dao.CourseReviewDao;
import org.example.dao.EnrollmentDao;
import org.example.entity.CourseReview;
import org.example.entity.Enrollment;
import org.example.service.CourseReviewService;

import java.util.List;
import java.util.Scanner;

@Bean_DreamBo
public class CourseReviewServiceImpl implements CourseReviewService {
    @Di_DreamBo
    private CourseReviewDao courseReviewDao;
    @Di_DreamBo
    private EnrollmentDao enrollmentDao;
    @Di_DreamBo
    private CourseDao courseDao;
    private final Scanner input = new Scanner(System.in);

    @Override
    public void reviewById(int id) {
        List<Enrollment> enrollments = enrollmentDao.selectUserId(id);
        System.out.println("您的选课信息");
        for (Enrollment e : enrollments) {
            System.out.println("课程："+courseDao.selectById(e.getCourse_id()).getCourseName()+"\tid:"+e.getCourse_id());
        }
        System.out.println("请输入要评价的课程的id:");
        Enrollment enrollment = enrollmentDao.selectByID(id, this.input.nextInt());
        while (enrollment == null) {
            System.out.println("输入错误的课程id号！请重新输入：");
            enrollment = enrollmentDao.selectByID(id, this.input.nextInt());
        }
        CourseReview courseReview1 = courseReviewDao.selectById(id, enrollment.getCourse_id());
        if (courseReview1 == null) {
            CourseReview courseReview = new CourseReview();
            courseReview.setUserId(id);
            courseReview.setCourseId(enrollment.getCourse_id());
            System.out.println("输入你的评分（0-10）：");
            int rating = this.input.nextInt();
            while (rating <= 0 || rating > 10) {
                System.out.println("输入的评分不在范围内,请重新输入！");
                rating = this.input.nextInt();
            }
            courseReview.setRating(rating);
            System.out.println("请输入评语：");
            String comment = this.input.next();
            courseReview.setComment(comment);
            courseReviewDao.insert(courseReview);
        } else {
            System.out.println("已存在选课评价！");
            System.out.println("是否选择更新(输入1更新，0退出)：");
            if (this.input.nextInt() == 1) {
                CourseReview courseReview = new CourseReview();
                courseReview.setUserId(id);
                courseReview.setCourseId(enrollment.getCourse_id());
                System.out.println("输入你的评分（0-10）：");
                int rating = this.input.nextInt();
                while (rating <= 0 || rating > 10) {
                    System.out.println("输入的评分不在范围内,请重新输入！");
                    rating = this.input.nextInt();
                }
                courseReview.setRating(rating);
                System.out.println("请输入评语：");
                String comment = this.input.next();
                courseReview.setComment(comment);
                courseReview.setId(courseReview1.getId());
                courseReviewDao.update(courseReview);
            } else {
                return;
            }
        }

    }
}
