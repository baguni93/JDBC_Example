import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsertOneTest {
    private static final Logger log =
            LoggerFactory.getLogger(InsertOneTest.class);

    public static void main(String[] args) {
        var collection = Database.getCollection("numbers");

        Document document = new Document();
        document.append("num", 9999);

        InsertOneResult result = collection.insertOne(document);

        log.info("InsertOneResult : {}" , result.getInsertedId());

        Database.close();
    }
}
