package com.khamitcevich.model.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class JdbcUtils {

    private JdbcUtils() {

    }

    public static void rollbackQuietly (Connection conn) {
        if(conn != null) {
            try {
                conn.rollback();
            }
            catch (SQLException e) {
                //NOP
            }
        }
    }

    public static void closeQuietly (ResultSet rs) {
        if(rs != null) {
            try {
                rs.close();
            }
            catch (SQLException e) {
                //NOP
            }
        }
    }

    public static void closeQuietly (Statement st) {
        if(st != null) {
            try {
                st.close();
            }
            catch (SQLException e) {
                //NOP
            }
        }
    }

    public static void closeQuietly (Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            }
            catch (SQLException e) {
                //NOP
            }
        }
    }
}
