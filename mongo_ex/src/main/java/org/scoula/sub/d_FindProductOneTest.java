package org.scoula.sub;


import com.mongodb.client.MongoCollection;
import org.scoula.sub.common.Database;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

public class d_FindProductOneTest {

    public static void main(String[] args) {

        MongoCollection<Document> collection = Database.getCollection("products");

        Document product = collection.find(eq("_id", new ObjectId("65f000000000000000000025"))).first();

        System.out.println(product);

        Database.close();
    }
}