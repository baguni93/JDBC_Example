package org.scoula.sub;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.scoula.sub.common.Database;
import org.bson.Document;

import java.util.Iterator;

import static com.mongodb.client.model.Filters.eq;


public class f_FindReviewByUserTest {

    public static void main(String[] args) {

        MongoCollection<Document> collection = Database.getCollection("reviews");

        FindIterable<Document> reviews = collection.find(eq("userId", 1L));

        Iterator<Document> itr = reviews.iterator();

        while (itr.hasNext()) {

            Document review = itr.next();

            System.out.println("==============");
            System.out.println("_id : " + review.getObjectId("_id"));

            System.out.println("userId : " + review.get("userId"));

            System.out.println("productId : " + review.get("product_id"));

            System.out.println("rating : " + review.get("rating"));

            System.out.println("comment : " + review.get("comment"));
        }

        Database.close();
    }
}