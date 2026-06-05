package org.scoula.sub;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.scoula.sub.common.Database;
import org.bson.Document;

public class c_FindProductAllTest {

    public static void main(String[] args) {

        MongoCollection<Document> collection = Database.getCollection("products");

        FindIterable<Document> products = collection.find();

        System.out.println("===== 상품 전체 조회 =====");

        for (Document product : products) {

            System.out.println("----------------------");

            System.out.println("_id : " + product.getObjectId("_id"));

            System.out.println("name : " + product.getString("name"));

            System.out.println("sku : " + product.getString("sku"));

            System.out.println("slug : " + product.getString("slug"));

            System.out.println("helpful_votes : " + product.get("helpful_votes"));
        }

        Database.close();
    }
}