package bdm.mongo;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class App{
    public static void main( String[] args ){
    	@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("test");

        MongoCollection<Document> collection = database.getCollection("restaurants");
        Document myDoc = collection.find().first();
        System.out.println(myDoc.toJson());
    }
}
