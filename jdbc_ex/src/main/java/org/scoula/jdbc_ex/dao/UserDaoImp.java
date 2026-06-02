package org.scoula.jdbc_ex.dao;

import org.scoula.jdbc_ex.common.JDBCUtil;
import org.scoula.jdbc_ex.domain.UserVO;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImp implements UserDao {

    private String USER_INSERT = "insert into users values(?,?,?,?)";
    private String USER_LIST =  "select * from users";
    private String USER_GET = "select * from users where id = ?";
    private String USER_UPDATE = "update users set name = ?, role = ? where id = ?";
    private String USER_DELETE = "delete from users where id = ?";

    @Override
    public int create(UserVO user) throws SQLException {

        try(
                Connection connection = JDBCUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(USER_INSERT);

        ){
            ps.setString(1, user.getId());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getRole());
            return ps.executeUpdate();
        }

    }

    @Override
    public List<UserVO> getList() throws SQLException {
        List<UserVO> userList = new ArrayList<>();

        try (
                Connection connection = JDBCUtil.getConnection();
                Statement ps = connection.createStatement();
                ResultSet rs = ps.executeQuery(USER_LIST);
        ){
            while (rs.next()){
                userList.add(map(rs));
            }
        }

        return userList;
    }

    @Override
    public Optional<UserVO> get(String id) throws SQLException {

        try(
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(USER_GET);
        )
        {
                ps.setString(1, id);
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        return Optional.of(map(rs));
                    }
                    else{
                        return Optional.empty();
                    }
                }
        }
    }

    @Override
    public int update(UserVO user) throws SQLException {

        try(
                Connection connection = JDBCUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(USER_UPDATE);
        ){
            ps.setString(1, "수정");
            ps.setString(2, "test");
            ps.setString(3, user.getId());

            return ps.executeUpdate();
        }
    }

    @Override
    public int delete(String id) throws SQLException {
        try(
                Connection connection = JDBCUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(USER_DELETE);
        ){
            ps.setString(1, id);
            return ps.executeUpdate();
        }
    }


    private UserVO map(ResultSet rs ) throws SQLException {

        UserVO user = new UserVO();
        user.setId(rs.getString(1));
        user.setPassword(rs.getString(2));
        user.setName(rs.getString(3));
        user.setRole(rs.getString(4));

        return user;
    }
}
