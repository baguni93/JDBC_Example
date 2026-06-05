package org.scoula.sub.app.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {

    private ObjectId id;

    // RDB에서 받은 사용자 와 연결
    private Long userId;

    private ObjectId product_id;

    private int rating;

    private String comment;

    private int helpful_votes;

//    생성 시점 -> createdAt만 설정
//    수정 전까지 -> updatedAt은 null
//    수정 시 -> updatedAt 값 설정
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;
}