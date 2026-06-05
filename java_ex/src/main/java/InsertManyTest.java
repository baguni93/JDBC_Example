import com.mongodb.client.result.InsertManyResult;
import org.bson.Document;
import org.slf4j.*;


import java.util.ArrayList;
import java.util.List;

public class InsertManyTest {

    private static final Logger log = LoggerFactory.getLogger(InsertManyTest.class);

    public static void main(String[] args) {
        var collection = Database.getCollection("numbers");

        List<Document> documents = new ArrayList<>();

        Document document = new Document();
        Document document1 = new Document();
        Document document2 = new Document();
        Document document3 = new Document();

        documents.add(document);
        documents.add(document1);
        documents.add(document2);
        documents.add(document3);

        InsertManyResult result =  collection.insertMany(documents);

        log.info("result insertMany  : {} " , result);
        Database.close();
    }
}
