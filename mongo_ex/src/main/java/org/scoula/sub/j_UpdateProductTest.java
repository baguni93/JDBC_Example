package org.scoula.sub;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.scoula.sub.common.Database;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

public class j_UpdateProductTest {

    public static void main(String[] args) {

        MongoCollection<Document> products = Database.getCollection("products");

        ObjectId productId = new ObjectId("65f000000000000000000025");

        UpdateResult result =
                products.updateOne(
                        eq("_id", productId),
                        Updates.combine(
                                Updates.set("name", "Updated Product Name"),
                                Updates.currentTimestamp("updatedAt")
                        )
                );

        System.out.println("수정된 상품 수 : " + result.getModifiedCount());

        Database.close();
    }
}