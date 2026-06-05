package org.scoula.sub.common;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class Database {

    /*
     * MongoDB 클라이언트 인스턴스 (싱글톤)
     * 애플리케이션 전체에서 공유하는 단일 연결 객체
     */
    private static MongoClient client;

    /*
     * MongoDB 데이터베이스 인스턴스 (싱글톤)
     * 데이터베이스에 대한 접근 객체
     */
    private static MongoDatabase database;


    static {


        // MongoDB 연결 문자열 생성 (로컬 서버, IP 주소 명시)
        // localhost의 27017 포트에 있는 MongoDB 서버에 연결
        ConnectionString connectionString = new ConnectionString("mongodb://127.0.0.1:27017");
        // MongoClient 인스턴스 생성
        client = MongoClients.create(connectionString);

        /* ================ POJO ================ */
        // POJO 코덱 프로바이더 생성 - 자동 매핑 활성화
		       // POJO 자동 변환 설정
        // MongoDB Document <-> Java 객체 자동 변환을 가능하게 함
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder()
                .automatic(true).build();

        // 코덱 레지스트리 생성
        CodecRegistry pojoCodecRegistry = fromRegistries(
                getDefaultCodecRegistry(),
                fromProviders(pojoCodecProvider)
        );
        // 데이터베이스에 POJO 코덱 레지스트리 적용
		// 데이터베이스 선택
        // withCodecRegistry를 붙여야 Java 객체로 자동 매핑 가능
        database = client.getDatabase("shop").withCodecRegistry(pojoCodecRegistry);
        /* ================ POJO ================ */

    }

    // MongoDB 연결 종료
    // 프로그램 끝날 때 호출
    public static void close() {
        if (client != null) {
            client.close();
        }
    }


    // 데이터베이스 객체 반환
    public static MongoDatabase getDatabase() {
        return database;
    }


    // Document 타입 컬렉션 반환
    // POJO를 쓰지 않고 org.bson.Document로 직접 다룰 때 사용
    public static MongoCollection<Document> getCollection(String colName) {
        MongoCollection<Document> collection = database.getCollection(colName);
        return collection;
    }




    /* ================ POJO ================ */

    // POJO 타입 컬렉션 반환
    // class를 넘기면 MongoDB 문서를 객체로 자동 변환
    public static <T> MongoCollection<T> getCollection(String colName, Class<T> clazz) {
        MongoCollection<T> collection = database.getCollection(colName, clazz);
        return collection;
    }

}
