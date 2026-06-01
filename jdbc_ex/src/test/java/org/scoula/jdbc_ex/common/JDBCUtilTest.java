package org.scoula.jdbc_ex.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JDBCUtilTest {

    @Test
    @DisplayName("db test connect")
    public void testConnection() throws SQLException {

        try(Connection connection = JDBCUtil.getConnection())
        {
            System.out.println("Connected to database successfully");
        }

    }
}