package com.healthdesk.util;

import javax.servlet.ServletContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbUtil {
    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;

    private DbUtil() {
    }

    public static void init(ServletContext context) {
        dbUrl = context.getInitParameter("dbUrl");
        dbUser = context.getInitParameter("dbUser");
        dbPassword = context.getInitParameter("dbPassword");
    }

    public static Connection getConnection() throws SQLException {
        if (dbUrl == null) {
            throw new SQLException("Database is not initialized. Ensure app startup completed.");
        }
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
