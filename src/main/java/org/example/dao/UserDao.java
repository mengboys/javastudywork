package org.example.dao;

import org.example.entity.User;

import java.util.List;

public interface UserDao {
    void print();
    void insert(User user);
    void update(User user);
    void delete(int id);
//    User selectByName(String UserName);
    User selectById(int id);
    List<User> selectAll();
}
