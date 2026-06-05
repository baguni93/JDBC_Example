import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mongodb.client.model.Filters.*;


public class DeleteManyTest {

    private static  final Logger log = LoggerFactory.getLogger(DeleteManyTest.class);
    public static void main(String[] args) {

        var col = Database.getCollection("users");

        Bson query = eq("name" , "modify name");

        var result =  col.deleteMany(query);

        log.info("result: {}", result.getDeletedCount());

        Database.close();

    }
}
