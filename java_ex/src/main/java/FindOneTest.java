import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mongodb.client.model.Filters.eq;

public class FindOneTest {
    private static final Logger log =  LoggerFactory.getLogger(FindOneTest.class);


    public static void main(String[] args) {


        var collection = Database.getCollection("users");

        Bson query = eq("_id" , new ObjectId("6a22284d99d2e878864a35db") );

        var doc = collection.find(query);

        log.info("FindOneResult : {}", doc);

        Database.close();

    }
}
