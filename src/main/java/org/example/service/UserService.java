package org.example.service;

import org.example.entity.User;

public interface UserService {
    void selectById(int id);

    void out();

    void insert();
    void delete();

    int Login(int id);

    boolean ChangePassword();
    void roleChange();
}
