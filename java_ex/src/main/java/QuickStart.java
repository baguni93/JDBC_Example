import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class QuickStart {
    private static final Logger log =
            LoggerFactory.getLogger(QuickStart.class);

    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017/";

        log.info("MongoDB 연결 시도");

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            log.info("MongoDB 연결 성공");

            MongoDatabase database = mongoClient.getDatabase("tutorial");
            MongoCollection<Document> collection = database.getCollection("numbers");
            Document doc = collection.find(eq("num", 3)).first();
            if (doc != null) {
                log.info("조회 결과: {}", doc.toJson());
            } else {
                log.warn("조회 결과 없음");
            }
        }
        catch (Exception e)
        {
            log.error("예외 발생", e);
        }
    }


}
