package org.example.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ResourceUtil {
    public ResourceUtil() {
    }

    public static void close(ResultSet rs) {
        try {
            rs.close();
        } catch (Exception var2) {
        }

    }

    public static void close(Statement st) {
        try {
            st.close();
        } catch (Exception var2) {
        }

    }

    public static void close(PreparedStatement pst) {
        try {
            pst.close();
        } catch (Exception var2) {
        }

    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (Exception var2) {
        }

    }

    public static void close(Statement st, Connection con) {
        close(st);
        close(con);
    }

    public static void close(ResultSet rs, Statement st, Connection con) {
        close(rs);
        close(st, con);
    }

    public static void close(PreparedStatement pst, Connection con) {
        close(pst);
        close(con);
    }

    public static void close(ResultSet rs, PreparedStatement pst, Connection con) {
        close(rs);
        close(pst, con);
    }
}
