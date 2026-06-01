package org.scoula.jdbc_ex.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// ctrl + shift + t
public class JDBCUtil {

    private static Connection conn = null;

    private static String driver;
    private static String url;
    private static String id;
    private static String password;

    static {
        try {
            Properties properties = new Properties();

            properties.load(
                    JDBCUtil.class.getResourceAsStream("/application.properties")
            );

            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            id = properties.getProperty("id");
            password = properties.getProperty("password");

            Class.forName(driver);

            conn = DriverManager.getConnection(url, id, password);

        } catch (Exception e) {
            throw new RuntimeException("DB 초기 연결 실패", e);
        }
    }

    public static Connection getConnection() {

        try {

            // 연결이 없거나 이미 닫힌 경우 재연결
            if (conn == null || conn.isClosed()) {

                conn = DriverManager.getConnection(
                        url,
                        id,
                        password
                );
            }

            return conn;

        } catch (SQLException e) {
            throw new RuntimeException("DB 재연결 실패", e);
        }
    }

    public static void close() {

        try {

            if (conn != null && !conn.isClosed()) {
                conn.close();
            }

            conn = null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}