package org.scoula.sub;

import com.mongodb.client.MongoCollection;
import org.scoula.sub.common.Database;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class i_ReviewStatisticsTest {

    public static void main(String[] args) {

        MongoCollection<Document> reviews = Database.getCollection("reviews");

        ObjectId productId = new ObjectId("65f000000000000000000025");

        // 리뷰 통계는 평균, 최대, 최소 계산이 필요하므로 List에 담음
        List<Document> reviewList = reviews.find(eq("product_id", productId)).into(new ArrayList<>());

        if (reviewList.isEmpty()) {
            System.out.println("리뷰가 없습니다.");
            Database.close();
            return;
        }

        int count = reviewList.size();
        int sum = 0;
        int max = 0;
        int min = 5;

        for (Document review : reviewList) {
            int rating = review.getInteger("rating");

            sum += rating;

            if (rating > max) {
                max = rating;
            }

            if (rating < min) {
                min = rating;
            }
        }

        double avg = (double) sum / count;

        System.out.println("===== 리뷰 통계 =====");
        System.out.println("상품 ID : " + productId);
        System.out.println("리뷰 수 : " + count);
        System.out.println("평균 평점 : " + avg);
        System.out.println("최고 평점 : " + max);
        System.out.println("최저 평점 : " + min);

        Database.close();
    }
}