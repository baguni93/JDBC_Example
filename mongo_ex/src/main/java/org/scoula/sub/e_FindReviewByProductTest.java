package org.scoula.sub;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.scoula.sub.common.Database;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

public class e_FindReviewByProductTest {

    public static void main(String[] args) {

        MongoCollection<Document> reviews = Database.getCollection("reviews");

        ObjectId productId = new ObjectId("65f000000000000000000025");

        FindIterable<Document> result = reviews.find(eq("product_id", productId));

        for (Document review : result) {
            System.out.println("----------------");
            System.out.println("userId : " + review.get("userId"));
            System.out.println("rating : " + review.get("rating"));
            System.out.println("comment : " + review.get("comment"));
        }

        Database.close();
    }
}