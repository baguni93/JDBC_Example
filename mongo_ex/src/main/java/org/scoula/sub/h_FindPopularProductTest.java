package org.scoula.sub;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.scoula.sub.common.Database;
import org.bson.Document;

import static com.mongodb.client.model.Sorts.descending;

public class h_FindPopularProductTest {

    public static void main(String[] args) {

        MongoCollection<Document> products =
                Database.getCollection("products");

        // helpful_votes가 높은 상품 TOP 5 조회
        FindIterable<Document> result =
                products.find()
                        .sort(descending("helpful_votes"))
                        .limit(5);

        System.out.println("===== 인기 상품 TOP 5 =====");

        for (Document product : result) {
            System.out.println("----------------------");
            System.out.println("상품명 : "
                    + product.getString("name"));
            System.out.println("SKU : "
                    + product.getString("sku"));
            System.out.println("helpful_votes : "
                    + product.get("helpful_votes"));
        }

        Database.close();
    }
}