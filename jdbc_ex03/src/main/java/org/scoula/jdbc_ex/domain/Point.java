package org.scoula.jdbc_ex.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor //모든 필드 받는 생성자
public class Point {

    private int point_id;
    private String user_id;
    private int point;
    private String reason;
    private LocalDateTime created_at;
}
