package org.scoula.sub;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.scoula.sub.common.Database;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

public class g_FindProductReviewTest {

    public static void main(String[] args) {

        MongoCollection<Document> products = Database.getCollection("products");

        MongoCollection<Document> reviews = Database.getCollection("reviews");

        ObjectId productId = new ObjectId("65f000000000000000000025");

        Document product = products.find(eq("_id", productId)).first();

        if (product == null) {
            System.out.println("상품이 없습니다.");
            Database.close();
            return;
        }

        System.out.println("상품명 : " + product.getString("name"));

        System.out.println("SKU : " + product.getString("sku"));

        System.out.println("========= 리뷰 목록 =========");

        FindIterable<Document> reviewList = reviews.find(eq("product_id", productId));

        for (Document review : reviewList) {
            System.out.println("----------------");
            System.out.println("userId : " + review.get("userId"));
            System.out.println("rating : " + review.get("rating"));
            System.out.println("comment : " + review.get("comment"));
        }

        Database.close();
    }
}