package org.scoula.sub.app;

import com.mongodb.client.*;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.Date;
import java.util.Scanner;

public class ChatMessageApp {

    public static void main(String[] args) {

        String uri = "mongodb://localhost:27017";

        try (
                MongoClient mongoClient = MongoClients.create(uri);
                Scanner sc = new Scanner(System.in)
        ) {
            MongoDatabase db = mongoClient.getDatabase("chat_db");
            MongoCollection<Document> messages = db.getCollection("chat_messages");

            // 채팅방별 최근 메시지 조회를 빠르게 하기 위한 복합 인덱스
            messages.createIndex(Indexes.compoundIndex(
                    Indexes.ascending("roomId"),
                    Indexes.descending("createdAt")
            ));

            while (true) {
                System.out.println("\n=== 채팅 메시지 실습 ===");
                System.out.println("1. 메시지 저장");
                System.out.println("2. 채팅방 메시지 조회");
                System.out.println("3. 최근 메시지 5개 조회");
                System.out.println("0. 종료");
                System.out.print("선택: ");

                String menu = sc.nextLine();

                if (menu.equals("1")) {
                    System.out.print("채팅방 ID: ");
                    String roomId = sc.nextLine();

                    System.out.print("보낸 사람: ");
                    String sender = sc.nextLine();

                    System.out.print("메시지: ");
                    String content = sc.nextLine();

                    Document message = new Document()
                            .append("roomId", roomId)
                            .append("sender", sender)
                            .append("message", content)
                            .append("isRead", false)
                            .append("createdAt", new Date());

                    messages.insertOne(message);

                    System.out.println("메시지가 저장되었습니다.");
                }

                else if (menu.equals("2")) {
                    System.out.print("조회할 채팅방 ID: ");
                    String roomId = sc.nextLine();

                    FindIterable<Document> result = messages.find(
                            new Document("roomId", roomId)
                    ).sort(Sorts.ascending("createdAt"));

                    for (Document doc : result) {
                        printMessage(doc);
                    }
                }

                else if (menu.equals("3")) {
                    System.out.print("조회할 채팅방 ID: ");
                    String roomId = sc.nextLine();

                    FindIterable<Document> result = messages.find(
                            new Document("roomId", roomId)
                    )
                    .sort(Sorts.descending("createdAt"))
                    .limit(5);

                    for (Document doc : result) {
                        printMessage(doc);
                    }
                }

                else if (menu.equals("0")) {
                    System.out.println("종료합니다.");
                    break;
                }

                else {
                    System.out.println("잘못된 메뉴입니다.");
                }
            }
        }
    }

    private static void printMessage(Document doc) {
        System.out.println("-------------------------");
        System.out.println("roomId  : " + doc.getString("roomId"));
        System.out.println("sender  : " + doc.getString("sender"));
        System.out.println("message : " + doc.getString("message"));
        System.out.println("isRead  : " + doc.getBoolean("isRead"));
        System.out.println("createdAt : " + doc.getDate("createdAt"));
    }
}