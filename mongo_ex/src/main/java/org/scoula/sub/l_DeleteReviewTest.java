package org.scoula.sub;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import org.scoula.sub.common.Database;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class l_DeleteReviewTest {

    public static void main(String[] args) {

        MongoCollection<Document> reviews = Database.getCollection("reviews");

        Long userId = 1L;

        ObjectId reviewId = new ObjectId("6a221fda200598950dd8e064");

        DeleteResult result = reviews.deleteOne(eq("_id", reviewId));


        System.out.println("삭제된 리뷰 수 : " + result.getDeletedCount());

        Database.close();
    }
}