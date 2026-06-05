import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mongodb.client.model.Filters.*;

public class UpdateManyTest {

    private static final Logger log = LoggerFactory.getLogger(UpdateManyTest.class);

    public static void main(String[] args) {

        var col = Database.getCollection("users");

        Bson query = gt("age", 16);

        Bson update = Updates.combine(
                Updates.set("name" , "modify name"),
                Updates.currentTimestamp("lastUpdated")
        );

        var result = col.updateMany(query, update);

        log.info("result: {}", result.getModifiedCount());

        Database.close();

    }

}
