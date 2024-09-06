package com.wavemaker.leavemgmt.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {
    private static final String serviceLink = "jdbc:mysql://127.0.0.1:3306/LEAVE_MGMT";  // Corrected JDBC URL
    private static final String userName = "root";
    private static final String password = "mysql@123";
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL JDBC Driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load JDBC driver.");
        }
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(serviceLink, userName, password);
    }
}
