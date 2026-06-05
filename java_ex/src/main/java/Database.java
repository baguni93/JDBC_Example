import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Database {

    static MongoClient client;
    static MongoDatabase database;

    static {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
        client = MongoClients.create(connectionString);
        database = client.getDatabase("tutorial");
    }

    public static void close(){
        client.close();
    }

    public static MongoDatabase getDatabase(){
        return database;
    }

    public static MongoCollection<Document> getCollection(String colName){
        return database.getCollection(colName);
    }

}
