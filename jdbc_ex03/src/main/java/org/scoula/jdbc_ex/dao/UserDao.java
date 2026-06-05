package org.scoula.jdbc_ex.dao;

import org.scoula.jdbc_ex.domain.UserVO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    int create(UserVO user);
    List<UserVO> getList() throws SQLException;
    Optional<UserVO> get(String id) throws SQLException;
    int update(String id , String name , String role) throws SQLException;
    int delete(String id) throws SQLException;
    List<UserVO> getShowListWithPoint() throws SQLException;
    Optional<UserVO> getShowMaxPointUser() throws SQLException;
}
