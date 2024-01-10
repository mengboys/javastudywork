package org.example.dao.impl;

import org.example.Spring.Bean_DreamBo;
import org.example.dao.CourseReviewDao;
import org.example.entity.CourseReview;
import org.example.util.ConnectionFactory;
import org.example.util.ResourceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Bean_DreamBo
public class CourseReviewImpl implements CourseReviewDao {
    @Override
    public void insert(CourseReview classReview) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConnectionFactory.getConnection();
            String sql = "INSERT INTO course_review (user_id, courser_id, rating, comment, date) VALUES (?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);

            ps.setInt(1, classReview.getUserId());
            ps.setInt(2, classReview.getCourseId());
            ps.setInt(3, classReview.getRating());
            ps.setString(4, classReview.getComment());

            LocalDateTime currentTime = LocalDateTime.now();
            java.sql.Date date = java.sql.Date.valueOf(currentTime.toLocalDate());

            ps.setDate(5, date);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResourceUtil.close(ps, con);
        }
    }

    @Override
    public CourseReview selectById(int userId, int courseId) {
        Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    CourseReview courseReview = null; // Initialize as null initially

    try {
        con = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM course_review WHERE user_id = ? AND courser_id = ?";
        ps = con.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setInt(2, courseId);
        rs = ps.executeQuery();

        if (rs.next()) {
            // If the result set is not empty, populate the CourseReview object
            courseReview = new CourseReview();
            courseReview.setId(rs.getInt("id"));
            courseReview.setUserId(rs.getInt("user_id"));
            courseReview.setCourseId(rs.getInt("courser_id"));
            courseReview.setRating(rs.getInt("rating"));
            courseReview.setComment(rs.getString("comment"));
            courseReview.setDate(rs.getDate("date"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle exceptions as needed
    } finally {
        ResourceUtil.close(rs, ps, con);
    }
    return courseReview;
    }

    @Override
    public void update(CourseReview classReview) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConnectionFactory.getConnection();
            String sql = "UPDATE course_review SET rating=?, comment=?, date=? WHERE id=?";
            ps = con.prepareStatement(sql);

            ps.setInt(1, classReview.getRating());
            ps.setString(2, classReview.getComment());
            LocalDateTime currentTime = LocalDateTime.now();
            java.sql.Date date = java.sql.Date.valueOf(currentTime.toLocalDate());
            ps.setDate(3, date);
            ps.setInt(4, classReview.getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Course review updated successfully for ID: " + classReview.getId());
            } else {
                System.out.println("No course review found with ID: " + classReview.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResourceUtil.close(ps, con);
        }
    }
}
