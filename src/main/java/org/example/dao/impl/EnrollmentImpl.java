package org.example.dao.impl;

import org.example.Spring.Bean_DreamBo;
import org.example.dao.EnrollmentDao;
import org.example.entity.Enrollment;
import org.example.util.ConnectionFactory;
import org.example.util.ResourceUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Bean_DreamBo
public class EnrollmentImpl implements EnrollmentDao {

    @Override
    public boolean insert(Enrollment enrollment) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.getConnection();
            String checkSql = "SELECT COUNT(*) FROM enrollment WHERE user_id = ? AND course_id = ?";
            ps = con.prepareStatement(checkSql);
            ps.setInt(1, enrollment.getUser_id());
            ps.setInt(2, enrollment.getCourse_id());
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("已存在相同的选课信息");
                return false;
            }

            String sql = "INSERT INTO enrollment VALUES(NULL,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, enrollment.getUser_id());
            ps.setInt(2, enrollment.getCourse_id());

            LocalDateTime currentTime = LocalDateTime.now();
            java.sql.Date date = java.sql.Date.valueOf(currentTime.toLocalDate());

            ps.setDate(3, date); // 设置日期参数
            ps.setInt(4, enrollment.getGrade());
            ps.executeUpdate();
        } catch (SQLException var8) {
            if (var8 instanceof SQLIntegrityConstraintViolationException) {
                var8.printStackTrace();
                return false;
            } else {
                var8.printStackTrace();
                return false;
            }
        } finally {
            ResourceUtil.close(ps, con);
        }
        return true;
    }

    @Override
    public boolean update(Enrollment enrollment) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConnectionFactory.getConnection();
            String sql = "UPDATE enrollment SET grade = ? WHERE id = ?";
            ps = con.prepareStatement(sql);
            // 设置要更新的字段值
            ps.setInt(1, enrollment.getGrade());

            // 假设你要根据enrollment的id来更新记录
            ps.setInt(2, enrollment.getId());
            int rowsAffected = ps.executeUpdate();

            // 检查更新是否成功
            if (rowsAffected > 0) {
                System.out.println("更新成功!");
                return true;
            } else {
                System.out.println("更新失败：未找到匹配的记录");
                return false;
            }
        } catch (SQLException var8) {
            var8.printStackTrace();
        } finally {
            ResourceUtil.close(ps, con);
        }
        return false;
    }

    public List<Enrollment> selectUserId(int userId) {
        List<Enrollment> enrollments = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM enrollment WHERE user_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                // 从结果集中获取数据并创建Enrollment对象
                Enrollment enrollment = new Enrollment();
                enrollment.setId(rs.getInt("id"));
                enrollment.setUser_id(rs.getInt("user_id"));
                enrollment.setCourse_id(rs.getInt("course_id"));
                enrollment.setDate(rs.getDate("date"));
                enrollment.setGrade(rs.getInt("grade"));

                // 将Enrollment对象添加到列表中
                enrollments.add(enrollment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResourceUtil.close(rs, ps, con);
        }

        return enrollments;
    }

    @Override
    public List<Enrollment> selectAll() {
        List<Enrollment> enrollments = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM enrollment";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                // 从结果集中获取数据并创建Enrollment对象
                Enrollment enrollment = new Enrollment();
                enrollment.setId(rs.getInt("id"));
                enrollment.setUser_id(rs.getInt("user_id"));
                enrollment.setCourse_id(rs.getInt("course_id"));
                enrollment.setDate(rs.getDate("date"));
                enrollment.setGrade(rs.getInt("grade"));

                // 将Enrollment对象添加到列表中
                enrollments.add(enrollment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResourceUtil.close(rs, ps, con);
        }

        return enrollments;
    }

    @Override
    public List<Enrollment> selectCourseId(int courseId) {
        List<Enrollment> enrollments = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM enrollment WHERE course_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, courseId);
            rs = ps.executeQuery();

            while (rs.next()) {
                // 从结果集中获取数据并创建Enrollment对象
                Enrollment enrollment = new Enrollment();
                enrollment.setId(rs.getInt("id"));
                enrollment.setUser_id(rs.getInt("user_id"));
                enrollment.setCourse_id(rs.getInt("course_id"));
                enrollment.setDate(rs.getDate("date"));
                enrollment.setGrade(rs.getInt("grade"));

                // 将Enrollment对象添加到列表中
                enrollments.add(enrollment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResourceUtil.close(rs, ps, con);
        }

        return enrollments;
    }

    @Override
    public boolean selectId(int userId, int courseId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Enrollment enrollment = new Enrollment();
        try {
            con = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM enrollment WHERE user_id = ? and course_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, courseId);
            rs = ps.executeQuery();

            while (rs.next()) {
                // 从结果集中获取数据并创建Enrollment对象
                enrollment.setId(rs.getInt("id"));
                enrollment.setUser_id(rs.getInt("user_id"));
                enrollment.setCourse_id(rs.getInt("course_id"));
                enrollment.setDate(rs.getDate("date"));
                enrollment.setGrade(rs.getInt("grade"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResourceUtil.close(rs, ps, con);
        }
        if (enrollment.getGrade() < 60) {
            return false;
        } else return true;
    }
    @Override
    public Enrollment selectByID(int userId,int courseId){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Enrollment enrollment = new Enrollment();
        try {
            con = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM enrollment WHERE user_id = ? and course_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, courseId);
            rs = ps.executeQuery();

            while (rs.next()) {
                // 从结果集中获取数据并创建Enrollment对象
                enrollment.setId(rs.getInt("id"));
                enrollment.setUser_id(rs.getInt("user_id"));
                enrollment.setCourse_id(rs.getInt("course_id"));
                enrollment.setDate(rs.getDate("date"));
                enrollment.setGrade(rs.getInt("grade"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResourceUtil.close(rs, ps, con);
        }
        return enrollment;
    }
}
