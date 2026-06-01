package org.scoula.jdbc_ex.test;

import org.junit.jupiter.api.*;
import org.scoula.jdbc_ex.common.JDBCUtil;

import java.sql.Connection;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrudTest {

    Connection connection = JDBCUtil.getConnection();

    @AfterAll
    public static void closeConnection()
    {
        JDBCUtil.close();
    }

    @Test
    @DisplayName("insert user")
    @Order(1)
    public void insertUser(){

    }


}
