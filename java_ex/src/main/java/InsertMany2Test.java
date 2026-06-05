import com.mongodb.client.result.InsertManyResult;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InsertMany2Test {

    private static final Logger log = LoggerFactory.getLogger(InsertMany2Test.class);


    public static void main(String[] args) {
        var collection = Database.getCollection("users");
        List<Document> insertList = new ArrayList<>();
        for(int i = 10; i < 21; i++) {
            Document document = new Document();
            document.append("name", "user_" + i);
            document.append("age", i);
            document.append("created", new Date() );
            insertList.add(document);
        }

        InsertManyResult result = collection.insertMany(insertList);
        log.info("InsertManyResult : {}", result.getInsertedIds());
        Database.close();
    }
}

