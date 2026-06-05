package org.scoula.jdbc_ex.dao;

import org.scoula.jdbc_ex.common.JDBCUtil;
import org.scoula.jdbc_ex.domain.Point;
import org.scoula.jdbc_ex.domain.UserVO;

import java.sql.*;
import java.util.*;

public class UserDaoImpl implements UserDao {

    private String USER_INSERT = "insert into users values(?,?,?,?)";
    private String USER_LIST = "select * from users as u left join points as p on u.id = p.user_id";
    private String USER_GET = "select * from users as u left join points as p on u.id = p.user_id where id = ?";
    private String USER_UPDATE = "update users set name = ?, role = ? where id = ?";
    private String USER_DELETE = "delete from users where id = ?";
    private String USER_ListWithPoint = """
            select a.*, b.*
            from users a
            inner join points b
            on a.id = b.user_id""";
    private String USER_ListWithMaxPoint = """
            select a.*,sum(b.point) as points
            from users a
            inner join points b
            on a.id = b.user_id
            group by a.id
            order by points desc
            limit 1""";
    @Override
    public int create(UserVO user) {

        try (
                Connection connection = JDBCUtil.getConnection();
                PreparedStatement ps = connection.prepareCall(USER_INSERT);
        ) {
            ps.setString(1, user.getId());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getRole());

            return ps.executeUpdate();
        } catch (Exception e) {

            e.printStackTrace();

            return 0;
        }
    }

    @Override
    public List<UserVO> getList() throws SQLException {

        Map<String, UserVO> userMap = new LinkedHashMap<>();

        try (
                Connection connection = JDBCUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(USER_LIST);
                ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {

                String userId = rs.getString("id");

                UserVO user = userMap.get(userId);

                if (user == null) {

                    user = new UserVO(
                            rs.getString("id"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("role"),
                            new ArrayList<>()
                    );

                    userMap.put(userId, user);
                }

                int pointId = rs.getInt("point_id");

                if (!rs.wasNull()) {

                    user.getPoints().add(
                            new Point(
                                    pointId,
                                    rs.getString("user_id"),
                                    rs.getInt("point"),
                                    rs.getString("reason"),
                                    rs.getTimestamp("created_at").toLocalDateTime()
                            )
                    );
                }
            }
        }

        return new ArrayList<>(userMap.values());
    }

    @Override
    public Optional<UserVO> get(String id) throws SQLException {

//        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
//            pstmt.setString(1, id);
//
//            try(ResultSet rs = pstmt.executeQuery()){
//                if(rs.next()){
//                    user = new UserVO();
//                    user.setId(rs.getString("id"));
//                    user.setPassword(rs.getString("password"));
//                    user.setName(rs.getString("name"));
//                    user.setRole(rs.getString("role"));
//
//                    List<PointVO> points = new ArrayList<>();
//
//                    do{
//                        PointVO pvo = new PointVO(
//                                rs.getInt("point_id"),
//                                rs.getString("id"),
//                                rs.getInt("point"));
//                        points.add(pvo);
//                    }while(rs.next());
//
//
//                    user.setPoints(points);
//                }
//            }
//
//        }catch (Exception e){
//
//            System.out.println("에러정보 "+ e.getMessage());
//
//        }


        Optional<UserVO> userOpt = Optional.empty();

        try (
                Connection connection = JDBCUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(USER_GET);
        ) {

            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {


                if (rs.next()) {


                    UserVO user = new UserVO(
                            rs.getString("id"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("role"),
                            new ArrayList<>()
                    );


                    int pointId = rs.getInt("point_id");

                    if (!rs.wasNull()) {

                        user.getPoints().add(
                                new Point(
                                        pointId,
                                        rs.getString("user_id"),
                                        rs.getInt("point"),
                                        rs.getString("reason"),
                                        rs.getTimestamp("created_at").toLocalDateTime()

                                )
                        );
                    }

                    userOpt =  Optional.of(user);

                }

                return userOpt;
            }
        }
    }

    @Override
    public int update(String id, String name, String role) throws SQLException {

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(USER_UPDATE);
        ) {
            ps.setString(1, name);
            ps.setString(2, role);
            ps.setString(3, id);

            return ps.executeUpdate();

        }

    }

    @Override
    public int delete(String id) throws SQLException {

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(USER_DELETE);
        ) {
            ps.setString(1, id);

            return ps.executeUpdate();
        }

    }

    @Override
    public List<UserVO> getShowListWithPoint() throws SQLException {

        Map<String, UserVO> userMap = new HashMap<>();

        try (
                Connection connection = JDBCUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(USER_ListWithPoint);
                ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {

                UserVO user = userMap.get(rs.getString("id"));

                if(user == null)
                {
                    user = new UserVO(
                            rs.getString("id"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("role"),
                            new ArrayList<>()
                    );

                    userMap.put(rs.getString("id"), user);

                }

                user.getPoints().add(
                        new Point(
                                rs.getInt("point_id"),
                                rs.getString("user_id"),
                                rs.getInt("point"),
                                rs.getString("reason"),
                                rs.getTimestamp("created_at").toLocalDateTime()
                        )
                );


            }

            return new ArrayList<>(userMap.values());
        }

    }

    @Override
    public Optional<UserVO> getShowMaxPointUser() throws SQLException {
        Optional<UserVO> userOpt = Optional.empty();

        try (
                Connection connection = JDBCUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(USER_ListWithMaxPoint);
                ResultSet rs = ps.executeQuery();
        ) {
            if (rs.next()) {

                UserVO user = new UserVO(
                        rs.getString("id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("role"),
                        new ArrayList<>()
                );

                user.getPoints().add(
                        new Point(
                                0,
                                "",
                                rs.getInt("points"),
                                "",
                                null
                        )
                );

                userOpt =  Optional.of(user);


            }
            return  userOpt;
        }
    }
}
