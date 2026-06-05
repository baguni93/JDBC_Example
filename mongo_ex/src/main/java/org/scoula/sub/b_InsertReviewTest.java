package org.scoula.sub;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import org.scoula.sub.common.Database;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class b_InsertReviewTest {
    public static void main(String[] args) {

        MongoCollection<Document> products = Database.getCollection("products");
        MongoCollection<Document> reviewCollection = Database.getCollection("reviews");

        reviewCollection.deleteMany(new Document());


        reviewCollection.createIndex(
                Indexes.compoundIndex(
                        Indexes.ascending("userId"),
                        Indexes.ascending("product_id")
                ),
                new IndexOptions().unique(true)
        );

        try {
            // insertOne 실습// 프러덕트생성하고 id 생성 된거 가져와서
            Long userId = 1L;
            ObjectId oneProductId = new ObjectId("6a1cd9899b86f839e2afc0d1");

            Document review = new Document()
                    .append("userId", userId)
                    .append("product_id", oneProductId)
                    .append("rating", 5)
                    .append("comment", "좋은 상품입니다.")
                    .append("helpful_votes", 0)
                    .append("createdAt", new Date());

            InsertOneResult oneResult = reviewCollection.insertOne(review);
            System.out.println("insertOne 리뷰 등록 성공: " + oneResult.getInsertedId());

            // insertMany 실습
            ObjectId manyProductId = new ObjectId("65f000000000000000000025");

            List<Document> reviewDocs = new ArrayList<>();

            for (long i = 1; i <= 100; i++) {
                reviewDocs.add(
                        new Document()
                                .append("userId", i)
                                .append("product_id", manyProductId)
                                .append("rating", ThreadLocalRandom.current().nextInt(1, 6))
                                .append("comment", "리뷰 " + i)
                                .append("helpful_votes", ThreadLocalRandom.current().nextInt(0, 100))
                                .append("createdAt", new Date())
                );
            }

            InsertManyResult manyResult = reviewCollection.insertMany(reviewDocs);
            System.out.println("insertMany 리뷰 등록 성공: " + manyResult.getInsertedIds().size() + "건");

        } catch (MongoWriteException e) {
            if (e.getError().getCode() == 11000) {
                System.out.println("이미 이 상품에 리뷰를 작성했습니다.");
            } else {
                throw e;
            }
        } finally {
            Database.close();
        }
    }
}
//https://www.mongodb.com/ko-kr/docs/manual/reference/error-codes
//코드	이름	의미
//11000	DuplicateKey	Unique Index 중복
//13	Unauthorized	권한 없음
//18	AuthenticationFailed	로그인 실패
//26	NamespaceNotFound	컬렉션 없음