package org.example.service.Impl;

import org.example.Spring.Bean_DreamBo;
import org.example.Spring.Di_DreamBo;
import org.example.dao.UserDao;
import org.example.entity.User;
import org.example.service.UserService;

import java.util.Objects;
import java.util.Scanner;

@Bean_DreamBo
public class UserServiceImpl implements UserService {
    @Di_DreamBo
    private UserDao userDao;
    private Scanner input = new Scanner(System.in);

    @Override
    public void selectById(int id) {
        System.out.println(userDao.selectById(1));

    }

    @Override
    public void out() {
        userDao.print();
        System.out.println("Service层执行结束!");
    }

    @Override
    public void insert() {
        User user = new User();
        int role;
        do {
            System.out.println("输入用户的权限（0-学生 1-教师 2-辅导员 3-管理员）：");
            role = this.input.nextInt();

            if (role < 0 || role > 3) {
                System.out.println("输入的值不在范围内，请重新输入：");
            } else {
                user.setRole(role);
                break; // 退出循环
            }
        } while (true);
        user.setRole(role);
        System.out.println("输入用户的姓名：");
        String name = this.input.next();
        user.setName(name);
        user.setUsername(name);
        System.out.println("输入用户的身份证号");
        String IDCard = this.input.next();
        while (IDCard.length() != 18) {
            System.out.println("输入的值不对！请重新输入：");
            IDCard = this.input.next();
        }
        user.setIdCard(IDCard);
        System.out.println("输入初始密码：");
        user.setPassword(this.input.next());
        userDao.insert(user);
        System.out.println("录入完成");
    }

    @Override
    public int Login(int id) {
        User user = new User();
        user.setId(id);
        User user1 = userDao.selectById(user.getId());
        if (user1 != null) {
            System.out.println("请输入密码：");
            user.setPassword(input.next());
            input.nextLine();
            if (Objects.equals(user1.getPassword(), user.getPassword())) {
                System.out.println("登录成功");
                System.out.println("欢迎" + user1.getUsername());
                return user1.getRole();
            }
        } else {
            System.out.println("无此用户");
            return -1;
        }
        return -1;
    }

    @Override
    public boolean ChangePassword() {
        User user = new User();
        System.out.println("请输入账号：");
        user.setId(input.nextInt());
        User user1 = userDao.selectById(user.getId());
        if (user1 != null) {
            System.out.println("身份证号：");
            String idCard = input.next();
            System.out.println(idCard);
            System.out.println("真实姓名");
            String name = input.next();
            System.out.println(name);
            if (Objects.equals(user1.getName(), name) && Objects.equals(user1.getIdCard(), idCard)) {
                System.out.println("验证成功\n请输入新的密码：");
                String mima1 = input.next();
                System.out.println("请再次输入密码：");
                String mima2 = input.next();
                if (Objects.equals(mima1, mima2)) {
                    user1.setPassword(mima1);
                    userDao.update(user1);
                    System.out.println("密码已修改");
                    return true;
                } else {
                    System.out.println("两次输入密码不同");
                    return false;
                }
            } else {
                System.out.println("身份证号或真实姓名错误！");
                return false;
            }
        } else {
            System.out.println("无此用户");
            return false;
        }
    }

    @Override
    public void delete() {
        System.out.println("输入要删除的用户id:");
        int id = this.input.nextInt();
        User user = userDao.selectById(id);
        if (user.getPassword() != null) {
            System.out.println("确认删除(1-确认 0-取消)");
            int j = this.input.nextInt();
            if (j == 1) {
                userDao.selectById(id);
            }
        } else {
            System.out.println("查无此人");
        }
    }

    @Override
    public void roleChange() {
        System.out.println("请输入要修改权限的用户ID：");
        int id = this.input.nextInt();
        User user = userDao.selectById(id);

        if (user.getPassword() != null) {
            int role;
            do {
                System.out.println("输入用户的权限（0-学生 1-教师 2-辅导员 3-管理员）：");
                role = this.input.nextInt();

                if (role < 0 || role > 3) {
                    System.out.println("输入的值不在范围内，请重新输入：");
                } else {
                    user.setRole(role);
                    break; // 退出循环
                }
            } while (true);
            userDao.update(user);
            System.out.println("权限修改完成");
        } else {
            System.out.println("未找到该用户");
        }
    }
}
