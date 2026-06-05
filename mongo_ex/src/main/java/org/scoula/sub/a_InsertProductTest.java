package org.scoula.sub;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.scoula.sub.common.Database;

public class a_InsertProductTest {

    public static void main(String[] args) {

        MongoCollection<Document> products = Database.getCollection("products");


        try {
            products.createIndex(
                    Indexes.ascending("sku"),
                    new IndexOptions().unique(true));

            products.createIndex(
                    Indexes.ascending("slug"),
                    new IndexOptions().unique(true));

        } catch (DuplicateKeyException e) {
            System.out.println("기존 데이터에 중복 slug 또는 sku가 있어 unique 인덱스를 생성할 수 없습니다.");
        }


        Document product = new Document()
                .append("sku", "200000")
                .append("slug", "mouse-2000000")
                .append("name", "Wireless Mouse")
                .append("helpful_votes", 0);

        try {

            InsertOneResult result = products.insertOne(product);
            System.out.println(result);

            System.out.println("상품 등록 성공 : " + result.getInsertedId());

        } catch (MongoWriteException e) {

            if (e.getError().getCode() == 11000) {

                System.out.println("중복된 SKU 또는 SLUG 입니다.");

            } else {
                throw e;
            }
        }

        Database.close();
    }
}