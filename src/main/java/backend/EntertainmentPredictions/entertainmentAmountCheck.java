package backend.EntertainmentPredictions;


import backend.Controller;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import static backend.GetUserInfo.connectToMongoDB;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class entertainmentAmountCheck {

    private static final String DB_NAME = "Envisionary";
    private static final String COLLECTION_NAME = "EntertainmentLimit";
    private static final String id = Controller.userId;

    public static boolean makeOrBreak(){
        boolean value = true;
        Document mongoInfo = getLimitDoc();
        try{
            EntertainmentLimit limit = parseLimitDoc(mongoInfo);
            value = availability(limit);
            return value;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return false;
        }

    }


    private static EntertainmentLimit parseLimitDoc(Document limitDoc) {
        EntertainmentLimit limit = new EntertainmentLimit();
        limit.setCounter(limitDoc.getInteger("counter"));
        limit.setActive(limitDoc.getBoolean("active"));
        limit.setDate(limitDoc.getString("date"));
        return limit;
    }

    private static Document getLimitDoc(){
        MongoClient client = connectToMongoDB();
        MongoDatabase database = client.getDatabase(DB_NAME);
        MongoCollection<Document> collection = database.getCollection("EntertainmentLimit");
        Document limit = new Document("active", true);
        return collection.find(limit).first();
    }

    private static boolean availability(EntertainmentLimit limit){
        if(limit.getDate().equalsIgnoreCase("null")){
            UpdateDoc(limit, 0);
            return true;
        }
        if(ZonedDateTime.parse(limit.getDate()).isAfter(ZonedDateTime.parse(limit.getDate()).plusMonths(1))){
            UpdateDoc(limit, 0);
            return true;
        }
        if(limit.getCounter() >= 100){
            UpdateDoc(limit, 1);
            return false;
        }
        UpdateDoc(limit, 2);
        return true;
    }

    private static void UpdateDoc(EntertainmentLimit limit, int identifier){
        EntertainmentLimit newLimit = new EntertainmentLimit();
        MongoClient client = connectToMongoDB();
        MongoDatabase database = client.getDatabase(DB_NAME);
        MongoCollection<Document> collection = database.getCollection("EntertainmentLimit");
        Document oldLimit = new Document("userID", id);
        switch (identifier){
            // reset
            case 0 -> {
                newLimit.setDate(ZonedDateTime.now().toString());
                newLimit.setActive(true);
                newLimit.setCounter(1);
            }
            // too many predictions
            case 1 -> {
                newLimit.setActive(true);
                newLimit.setDate(limit.getDate());
                newLimit.setCounter(limit.getCounter());
            }
            // good to go
            case 2 ->{
                newLimit.setCounter(limit.getCounter() + 1);
                newLimit.setDate(limit.getDate());
                newLimit.setActive(true);
            }
            // prediction trigger true->false
            case 3->{
                newLimit.setActive(!limit.getActive());
                newLimit.setDate(limit.getDate());
                newLimit.setCounter(limit.getCounter());
            }
        }
        collection.updateOne(Filters.eq("active", true), Updates.combine(
                    Updates.set("active", newLimit.getActive()),
                            Updates.set("date", newLimit.getDate()),
                            Updates.set("counter", newLimit.getCounter())
        ));
    }

    public static void EntertainmentTrigger(){
        EntertainmentLimit limit = parseLimitDoc(getLimitDoc());
        UpdateDoc(limit, 3);
    }

}
