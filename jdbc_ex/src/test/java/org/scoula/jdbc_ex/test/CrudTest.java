package org.scoula.jdbc_ex.test;

import org.junit.jupiter.api.*;
import org.scoula.jdbc_ex.common.JDBCUtil;
import org.scoula.jdbc_ex.domain.UserVO;

import java.sql.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrudTest {

    Connection connection = JDBCUtil.getConnection();

    @AfterAll
    public static void closeConnection()
    {
        JDBCUtil.close();
    }
//
//    @Test
//    @DisplayName("insert user")
//    @Order(1)
//    public void insertUser() throws SQLException {
//
//        String sql = "insert into users values(?,?,?,?)";
//
//        try(PreparedStatement pstmt = connection.prepareStatement(sql))
//        {
//            pstmt.setString(1,"user16");
//            pstmt.setString(2,"user13");
//            pstmt.setString(3,"user14");
//            pstmt.setString(4,"user15");
//
//            int count = pstmt.executeUpdate();
//
//            Assertions.assertEquals(1, count);
//        }
//
//    }

    @Test
    @DisplayName("select user")
    @Order(2)
    public void selectUserAll() throws SQLException {

        String sql = "select * from users";

        try(Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next())
            {
                String username = rs.getString("name");
                //System.out.println(username);
            }
        }
    }

//    @Test
//    @DisplayName("select by userId")
//    @Order(3)
//    public void selectUserById () throws SQLException {
//
//        String userid = "user12";
//
//        String sql = "select * from users where id = ?";
//        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
//
//            pstmt.setString(1,userid);
//
//            try(ResultSet rs = pstmt.executeQuery()){
//                if(rs.next())
//                {
//                    String username = rs.getString("name");
//                    System.out.println(username);
//                }
//                else{
//                    throw new RuntimeException();
//                }
//            }
//        }
//    }

//
//    @Test
//    @DisplayName("update user")
//    @Order(4)
//    public void updateUser() throws SQLException {
//
//        String userid =  "user12";
//
//        String sql =  "update users set name = ? where id = ?";
//
//        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
//
//
//            pstmt.setString(1,"updateTest");
//            pstmt.setString(2,userid);
//
//
//            int count = pstmt.executeUpdate();
//
//            Assertions.assertEquals(1, count);
//        }
//
//
//    }

    @Test
    @DisplayName("delete user")
    @Order(5)
    public void deleteUser() throws SQLException {

        String userid =  "user12";

        String sql = "delete from users where id = ?";

        try(PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setString(1,userid);

            int count = pstmt.executeUpdate();
            Assertions.assertEquals(1, count);

        }

    }


}
