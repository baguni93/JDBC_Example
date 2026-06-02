package org.scoula.jdbc_ex.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor //모든 필드 받는 생성자
public class UserVO {
    private String id;
    private String password;
    private String name;
    private String role;


}
