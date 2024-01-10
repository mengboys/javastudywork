package org.example.dao.impl;

import org.example.Spring.Bean_DreamBo;
import org.example.dao.CourseDao;
import org.example.entity.Course;
import org.example.util.ConnectionFactory;
import org.example.util.ResourceUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Bean_DreamBo
public class CourseImpl implements CourseDao {
    @Override
    public List<Course> selectAll() {
        Connection con;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Course> courses = new ArrayList<>();

        try {
            con = ConnectionFactory.getConnection();
            String sql = "select * from course";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery(); // 执行查询

            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setCourseName((rs.getString("course_name")));
                course.setDescription((rs.getString("course_description")));
                course.setPrerequisiteId(rs.getString("prerequisite_course_id"));
                course.setMaxCapacity(rs.getInt("max_capacity"));
                course.setStartTime(rs.getDate("start_time"));
                course.setEndTime(rs.getDate("end_time"));

                courses.add(course); // 将学生对象添加到列表
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    @Override
    public Course selectById(int id) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Course course = new Course();
        try {
            con = ConnectionFactory.getConnection();
            String sql = "select * from course where id=?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id); // 设置参数值
            rs = pst.executeQuery(); // 执行查询

            if (rs.next()) {
                course.setId(rs.getInt("id"));

                course.setCourseName((rs.getString("course_name")));
                course.setDescription((rs.getString("course_description")));
                course.setPrerequisiteId(rs.getString("prerequisite_course_id"));
                course.setMaxCapacity(rs.getInt("max_capacity"));
                course.setStartTime(rs.getDate("start_time"));
                course.setEndTime(rs.getDate("end_time"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceUtil.close(pst, con);
        }
        return course;
    }

    @Override
    public void insert(Course course) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConnectionFactory.getConnection();
            String sql = "INSERT INTO course (course_name, course_description, prerequisite_course_id, teacher_id, max_capacity, start_time, end_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getDescription());
            ps.setString(3, course.getPrerequisiteId());
            ps.setInt(4, course.getTeacherId());
            ps.setInt(5, course.getMaxCapacity());

            // 转换 LocalDateTime 为 java.sql.Date
            ps.setDate(6, (course.getStartTime() != null) ? java.sql.Date.valueOf(course.getStartTime().toLocalDate()) : null);
            ps.setDate(7, (course.getEndTime() != null) ? java.sql.Date.valueOf(course.getEndTime().toLocalDate()) : null);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResourceUtil.close(ps, con);
        }
    }

    @Override
    public List<Course> selectByTeacherId(int id) {
        Connection con;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Course> courses = new ArrayList<>();

        try {
            con = ConnectionFactory.getConnection();
            String sql = "select * from course where teacher_id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery(); // 执行查询

            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setCourseName((rs.getString("course_name")));
                course.setDescription((rs.getString("course_description")));
                course.setPrerequisiteId(rs.getString("prerequisite_course_id"));
                course.setMaxCapacity(rs.getInt("max_capacity"));
                course.setStartTime(rs.getDate("start_time"));
                course.setEndTime(rs.getDate("end_time"));

                courses.add(course); // 将学生对象添加到列表
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }
}
