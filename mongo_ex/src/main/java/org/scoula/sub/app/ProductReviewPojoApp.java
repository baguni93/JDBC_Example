package org.scoula.sub.app;


import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.scoula.sub.common.Database;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.scoula.sub.app.domain.Product;
import org.scoula.sub.app.domain.Review;


import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.descending;

public class ProductReviewPojoApp {

    public static void main(String[] args) {

        MongoCollection<Product> products = Database.getCollection("products", Product.class);

        MongoCollection<Review> reviews = Database.getCollection("reviews", Review.class);


        ObjectId productId = new ObjectId("65f000000000000000000010");

        Long userId = 1L;

        insertReview(reviews, userId, productId);
        selectAllProducts(products);
        selectProduct(products, productId);
        selectReviewByProduct(reviews, productId);
        selectReviewByUser(reviews, userId);
        selectPopularProducts(products);
        updateReview(reviews, userId, productId);
        deleteReview(reviews, userId, productId);

        Database.close();
    }


    private static void insertReview(MongoCollection<Review> reviews, Long userId, ObjectId productId) {

        //한 유저는 같은 상품에 리뷰를 1개만 작성 가능
//        reviews.createIndex(
//                Indexes.compoundIndex(
//                        Indexes.ascending("userId"),
//                        Indexes.ascending("product_id")
//                ),
//                new IndexOptions().unique(true)
//        );

        Review review = Review.builder()
                .userId(userId)
                .product_id(productId)
                .rating(5)
                .comment("POJO 리뷰 테스트")
                .build();


        try {
            InsertOneResult result = reviews.insertOne(review);

            System.out.println("리뷰 등록 성공 : " + result.getInsertedId());

        } catch (MongoWriteException e) {

            if (e.getError().getCode() == 11000) {
                System.out.println("이미 이 상품에 리뷰를 작성했습니다.");
            } else {
                throw e;
            }
        }
    }

    private static void selectAllProducts(MongoCollection<Product> products)
    {
        System.out.println("===== 상품 전체 조회 =====");
//FindIterable<Product>
        List<Product> result = products.find().into(new ArrayList<>());

        for (Product product : result) {
            System.out.println(product);
        }
    }

    private static void selectProduct(MongoCollection<Product> products, ObjectId productId) {
        Product product = products.find(eq("_id", productId)).first();

        if (product == null) {
            System.out.println("상품이 없습니다.");
            return;
        }

        System.out.println("===== 상품 1개 조회 =====");
        System.out.println(product);
    }

    private static void selectReviewByProduct(MongoCollection<Review> reviews, ObjectId productId) {
        System.out.println("===== 특정 상품 리뷰 조회 =====");

        FindIterable<Review> result = reviews.find(eq("product_id", productId));

        for (Review review : result) {
            System.out.println(review);
        }
    }

    private static void selectReviewByUser(MongoCollection<Review> reviews, Long userId) {
        System.out.println("===== 특정 사용자 리뷰 조회 =====");

        FindIterable<Review> result = reviews.find(eq("userId", userId));

        for (Review review : result) {
            System.out.println(review);
        }
    }

    private static void selectPopularProducts(MongoCollection<Product> products
    ) {
        System.out.println("===== 인기 상품 TOP 5 =====");

        FindIterable<Product> result =
                products.find()
                        .sort(descending("helpful_votes"))
                        .limit(5);

        for (Product product : result) {
            System.out.println(product.getName() + " / votes : " + product.getHelpful_votes());
        }
    }

    private static void updateReview(MongoCollection<Review> reviews, Long userId, ObjectId productId) {
        Bson query = and(
                eq("userId", userId),
                eq("product_id", productId)
        );

        Bson update = Updates.combine(
                Updates.set("rating", 4),
                Updates.set("comment", "POJO 리뷰 수정"),
                Updates.currentTimestamp("updatedAt")
        );

        UpdateResult result = reviews.updateOne(query, update);

        System.out.println("수정된 리뷰 수 : " + result.getModifiedCount());
    }

    private static void deleteReview(
            MongoCollection<Review> reviews,
            Long userId,
            ObjectId productId
    ) {
        DeleteResult result =
                reviews.deleteOne(
                        and(
                                eq("userId", userId),
                                eq("product_id", productId)
                        )
                );

        System.out.println("삭제된 리뷰 수 : " + result.getDeletedCount());
    }
}