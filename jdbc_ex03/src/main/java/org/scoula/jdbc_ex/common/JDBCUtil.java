package org.scoula.jdbc_ex.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {

    // static Connection: 프로그램 실행 동안 딱 1개의 연결만 유지한다.
    private static String url;
    private static String id;
    private static String password;

    static {
        try {
            Properties properties = new Properties();
            properties.load(
                    JDBCUtil.class.getResourceAsStream("/application.properties")
            );

            String driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            id = properties.getProperty("id");
            password = properties.getProperty("password");

            Class.forName(driver);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException  {
        return DriverManager.getConnection(url, id, password);
    }

//    public static void close() {
//        try {
//            if (conn != null) {
//                conn.close();
//                conn = null;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
