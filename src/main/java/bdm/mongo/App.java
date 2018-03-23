package bdm.mongo;

import java.util.Arrays;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;


import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.orderBy;

public class App{
    public static void main( String[] args ){
    	@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("test");

        MongoCollection<Document> collection = database.getCollection("restaurants");
        Document myDoc = collection.find().first();
        System.out.println(myDoc.toJson());
        
        Block<Document> printBlock = new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
        };

        collection.aggregate(
                Arrays.asList(
                        Aggregates.unwind("$grades"),
                        Aggregates.group(
                                "$cuisine",
                                Accumulators.avg("avgScore", "$grades.score")
                        ),
                        Aggregates.sort(orderBy(ascending("_id")))
                )
        ).forEach(printBlock);
    
    }
}
