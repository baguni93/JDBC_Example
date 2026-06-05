package org.scoula.sub;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.scoula.sub.common.Database;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class k_UpdateReviewTest {

    public static void main(String[] args) {

        MongoCollection<Document> reviews = Database.getCollection("reviews");

        Long userId = 1L;

        // 기존 f_FindReviewByUserTest 로 확인
//        reviewId :
//        rating : 5
//        comment : 리뷰 1
        ObjectId reviewId = new ObjectId("6a221fda200598950dd8e064");

        Bson query = eq("_id", reviewId);

        Bson update = Updates.combine(
                Updates.set("rating", 4),
                Updates.set("comment", "수정된 리뷰입니다."),
                Updates.currentTimestamp("updatedAt")
        );

        UpdateResult result = reviews.updateOne(query, update);

        System.out.println("수정된 리뷰 수 : " + result.getModifiedCount());
    }
}