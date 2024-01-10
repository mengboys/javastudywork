package org.example.dao.impl;

import org.example.dao.UserDao;
import org.example.Spring.Bean_DreamBo;
import org.example.entity.User;
import org.example.util.ConnectionFactory;
import org.example.util.ResourceUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Bean_DreamBo
public class UserDaoImpl implements UserDao {
    private User user;

    @Override
    public void print() {
        System.out.println("Hello World!" + "Dao层执行结束");
    }

    @Override
    public User selectById(int id) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        user = new User();
        try {
            con = ConnectionFactory.getConnection();
            String sql = "select * from user where id=?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id); // 设置参数值
            rs = pst.executeQuery(); // 执行查询
            if (rs.next()) {

                user.setId(rs.getInt("id"));
                user.setRole(rs.getInt("role"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name_"));
                user.setIdCard(rs.getString("ID_card"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceUtil.close(pst, con);
        }
        return user;
    }

    public void insert(User user) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConnectionFactory.getConnection();
            String sql = "insert into user values(NULL,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, user.getRole());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getName());
            ps.setString(5, user.getIdCard());
            ps.executeUpdate();
        } catch (SQLException var8) {
            var8.printStackTrace();
        } finally {
            ResourceUtil.close(ps, con);
        }
    }

    @Override
    public void update(User user) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConnectionFactory.getConnection();
            String sql = "UPDATE user SET role = ?, username = ?, password = ?, name_ = ?, ID_card = ? WHERE id = ?";
            ps = con.prepareStatement(sql);

            ps.setInt(1, user.getRole());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getName());
            ps.setString(5, user.getIdCard());
            ps.setInt(6, user.getId());
            ps.executeUpdate();
        } catch (SQLException var8) {
            var8.printStackTrace();
        } finally {
            ResourceUtil.close(ps, con);
        }
    }

    @Override
    public List<User> selectAll() {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            con = ConnectionFactory.getConnection();
            String sql = "select * from student";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery(); // 执行查询

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setRole(rs.getInt("role"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name_"));
                user.setIdCard(rs.getString("ID_card"));

                users.add(user); // 将学生对象添加到列表
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceUtil.close(pst, con);
        }
        return users;
    }

    @Override
    public void delete(int id) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConnectionFactory.getConnection();
            String sql = "DELETE FROM user WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("成功删除用户 ID: " + id);
            } else {
                System.out.println("未找到 ID 为 " + id + " 的用户");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 根据需要处理异常
        } finally {
            ResourceUtil.close(ps, con);
        }
    }

}
