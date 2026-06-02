package org.scoula.jdbc_ex.dao;

import org.junit.jupiter.api.*;
import org.scoula.jdbc_ex.common.JDBCUtil;
import org.scoula.jdbc_ex.domain.UserVO;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDaoImpTest {

    UserDaoImp dao = new UserDaoImp();


    @AfterAll
    static void tearDown() {
        JDBCUtil.close();
    }

    //@Test
    void create() throws SQLException {
        UserVO user = new UserVO("app", "1234", "app" ,"admin" );
        int count = dao.create(user);
        Assertions.assertEquals(1, count);
    }

    //@Test
    void getList() throws SQLException {

       var list =  dao.getList();

       for( var value : list ) {
           System.out.println(value.getId());
       }
    }

    //@Test
    void get() throws SQLException {

        var userOpt =  dao.get("app");

        userOpt.ifPresent(user -> {
            System.out.println(user.getName());
        });;

    }

    //@Test
    void update() throws SQLException {
        UserVO user = new UserVO("app", "1234", "app" ,"admin" );
        var count =  dao.update(user);
        Assertions.assertEquals(1, count);
    }

    @Test
    @Order(1)
    void delete() throws SQLException {
        var count =  dao.delete("app");
        Assertions.assertEquals(1, count);

    }
}