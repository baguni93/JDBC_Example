import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.*;

import static com.mongodb.client.model.Filters.eq;


public class UpdateOneTest {

    private static final Logger log =  LoggerFactory.getLogger(UpdateOneTest.class);

    public static void main(String[] args) {

        var collection = Database.getCollection("users");

        Bson query = eq("_id", new ObjectId("6a22284d99d2e878864a35db"));
        Bson update = Updates.combine(
                Updates.set("name", "modify name"),
                Updates.currentTimestamp("lastUpdate")
        );

        var result =  collection.updateOne(query, update);
        log.info("result : {}" ,  result.getModifiedCount());
        Database.close();

//        db.users.updateOne(
//                { _id: ObjectId("6a22284d99d2e878864a35db") },
//        {
//            $set: {
//                name: "modify name"
//            },
//            $currentDate: {
//                lastUpdate: {
//                    $type: "timestamp"
//                }
//            }
//        }
//)
    }

}
