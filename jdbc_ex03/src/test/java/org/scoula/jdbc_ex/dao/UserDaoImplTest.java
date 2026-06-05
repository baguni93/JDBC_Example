package org.scoula.jdbc_ex.dao;

import org.junit.jupiter.api.*;
import org.scoula.jdbc_ex.common.JDBCUtil;
import org.scoula.jdbc_ex.domain.Point;
import org.scoula.jdbc_ex.domain.UserVO;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDaoImplTest {

    static UserDaoImpl dao;

    @BeforeAll
    static void setUp(){
         dao = new UserDaoImpl();
    }


    //@Test
    //@Order(1)
    void create() {

        UserVO userVO = new UserVO(
                "user99",
                "user99",
                "user99",
                "user99",
                new Point()
        );
        int count = dao.create(userVO);

        assertThat(count).isEqualTo(1);
    }

    //@Test
    //@Order(1)
    void getList() throws SQLException {

      var userList =  dao.getList();

      for(var user : userList){
          System.out.println(user.toString());
      }

    }

    //@Test
    //@Order(2)
    void get() throws SQLException {

      var userOpt = dao.get("user99");

      userOpt.ifPresent(user -> System.out.println(user.toString()));
    }

    @Test
    @Order(3)
    void update() throws SQLException {

        var userOpt = dao.get("user99");

        if(userOpt.isPresent()){
            int count =  dao.update("user99" , "메롱", "test");
            assertThat(count).isEqualTo(1);
        }

        userOpt = dao.get("user99");
        userOpt.ifPresent(user -> System.out.println(user.toString()));

    }

    @Test
    void delete() {

    }
}