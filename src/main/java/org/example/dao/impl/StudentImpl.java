package org.example.dao.impl;

import org.example.Spring.Bean_DreamBo;
import org.example.dao.StudentDao;
import org.example.entity.Student;
import org.example.util.ConnectionFactory;
import org.example.util.ResourceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Bean_DreamBo
public class StudentImpl implements StudentDao {
    @Override
    public List<Student> selectAll() {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Student> students = new ArrayList<>();

        try {
            con = ConnectionFactory.getConnection();
            String sql = "select * from student";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery(); // 执行查询

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setSchoolClassId(rs.getInt("class_id"));
                student.setAddress(rs.getString("address"));
                student.setIntroduce(rs.getString("introduce"));
                student.setGpa(rs.getFloat("gpa"));
                student.setName(rs.getString("name_"));

                students.add(student); // 将学生对象添加到列表
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceUtil.close(pst, con);
        }
        return students;
    }

    @Override
    public Student selectById(int id) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Student student = new Student(); // 假设有一个 Student 类用于表示学生信息
        try {
            con = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM student WHERE id=?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id); // 设置参数值为要查询的学生的 ID
            rs = pst.executeQuery(); // 执行查询

            if (rs.next()) {
                student.setId(rs.getInt("id"));
                student.setSchoolClassId(rs.getInt("class_id"));
                student.setAddress(rs.getString("address"));
                student.setIntroduce(rs.getString("introduce"));
                student.setGpa(rs.getFloat("gpa"));
                student.setName(rs.getString("name_"));
                // 设置其他学生信息字段，如果有的话
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceUtil.close(pst, con);
        }
        return student;
    }

    @Override
    public List<Student> selectClassId(int id) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Student> students = new ArrayList<>();

        try {
            con = ConnectionFactory.getConnection();
            String sql = "select * from student where class_id=?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery(); // 执行查询

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setSchoolClassId(rs.getInt("class_id"));
                student.setAddress(rs.getString("address"));
                student.setIntroduce(rs.getString("introduce"));
                student.setGpa(rs.getFloat("gpa"));
                student.setName(rs.getString("name_"));

                students.add(student); // 将学生对象添加到列表
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceUtil.close(pst, con);
        }
        return students;
    }

    @Override
    public void updateById(Student student) {
        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = ConnectionFactory.getConnection();
            String sql = "UPDATE student SET address=?, introduce=?, gpa=?, name_=? WHERE id=?";
            pst = con.prepareStatement(sql);

            pst.setString(1, student.getAddress());
            pst.setString(2, student.getIntroduce());
            pst.setFloat(3, student.getGpa());
            pst.setString(4, student.getName());
            pst.setInt(5, student.getId());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("已成功更新id为：" + student.getId()+" 的学生的信息");
            } else {
                System.out.println("未找到学生ID：" + student.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceUtil.close(pst, con);
        }
    }
}
