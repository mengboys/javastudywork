package org.example.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//连接数据据库
public class ConnectionFactory {
    private static Properties props = new Properties();

    static {
        try {
            props.load(new FileInputStream("database.properties"));
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public ConnectionFactory() {
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/cms";
            String username = "root";
            String password = "******";
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("failed to register driver.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("failed to execute sql.");
            e.printStackTrace();
        }
        return con;
    }

    public static void main(String[] args) {
        Connection con = getConnection();
        System.out.println(con);
    }
}
