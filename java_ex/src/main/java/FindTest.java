import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindTest {

    private static  final Logger log = LoggerFactory.getLogger(FindTest.class);

    public static void main(String[] args) {
        var collection = Database.getCollection("users");

        FindIterable<Document> documents = collection.find();

        for (Document document : documents) {

            log.info("findResultRow : {}", document);

        }

        Database.close();
    }

}
