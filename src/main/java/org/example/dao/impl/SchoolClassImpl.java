package org.example.dao.impl;

import org.example.Spring.Bean_DreamBo;
import org.example.dao.SchoolClassDao;
import org.example.entity.SchoolClass;
import org.example.util.ConnectionFactory;
import org.example.util.ResourceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Bean_DreamBo
public class SchoolClassImpl implements SchoolClassDao {
    @Override
    public SchoolClass selectById(int id) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        SchoolClass schoolClass = new SchoolClass();
        try {
            con = ConnectionFactory.getConnection();
            String sql = "select * from school_class where id=?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id); // 设置参数值
            rs = pst.executeQuery(); // 执行查询

            if (rs.next()) {
                schoolClass.setId(rs.getInt("id"));
                schoolClass.setClassName((rs.getString("class_name"))); // 设置班级名称
                schoolClass.setCounsellorId(rs.getInt("counsellor_id")); // 设置辅导员ID
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceUtil.close(pst, con);
        }
        return schoolClass;
    }

    @Override
    public List<SchoolClass> selectByCounsellorId(int id) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<SchoolClass> schoolClasses = new ArrayList<>();
        SchoolClass schoolClass = new SchoolClass();
        try {
            con = ConnectionFactory.getConnection();
            String sql = "select * from school_class where counsellor_id=?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id); // 设置参数值
            rs = pst.executeQuery(); // 执行查询

            if (rs.next()) {
                schoolClass.setId(rs.getInt("id"));
                schoolClass.setClassName((rs.getString("class_name"))); // 设置班级名称
                schoolClass.setCounsellorId(rs.getInt("counsellor_id")); // 设置辅导员ID
                schoolClasses.add(schoolClass);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceUtil.close(pst, con);
        }
        return schoolClasses;
    }
}
