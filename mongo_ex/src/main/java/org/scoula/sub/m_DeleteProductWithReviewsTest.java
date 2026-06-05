package org.scoula.sub;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import org.scoula.sub.common.Database;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

public class m_DeleteProductWithReviewsTest {

    public static void main(String[] args) {

        MongoCollection<Document> products = Database.getCollection("products");

        MongoCollection<Document> reviews = Database.getCollection("reviews");

        ObjectId productId = new ObjectId("65f000000000000000000025");

        // 1. 먼저 해당 상품의 리뷰 삭제
        DeleteResult reviewResult = reviews.deleteMany(eq("product_id", productId));

        // 2. 상품 삭제
        DeleteResult productResult = products.deleteOne(eq("_id", productId));

        System.out.println("삭제된 리뷰 수 : " + reviewResult.getDeletedCount());

        System.out.println("삭제된 상품 수 : " + productResult.getDeletedCount());

        Database.close();
    }
}