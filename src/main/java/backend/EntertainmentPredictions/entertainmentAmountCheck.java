package backend.EntertainmentPredictions;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static backend.EntertainmentPredictions.EntertainmentLimit.*;

public class entertainmentAmountCheck {

    private static final String DB_NAME = "Envisionary";
    private static String COLLECTION_NAME = "EntertainmentLimit";

    private static MongoClient connectToMongoDB() {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
        ConnectionString mongoUri = new ConnectionString("mongodb+srv://MONGO_USER:" + System.getenv("MONGO_PASSWORD") + "@envisionarycluster.19uobkz.mongodb.net/?retryWrites=true&w=majority");
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyConnectionString(mongoUri).build();
        return MongoClients.create(settings);
    }

    public static boolean amountMade(String userID){
        MongoClient client = connectToMongoDB();
        MongoDatabase database = client.getDatabase();
    }



    public static void getLimit(){

    }

    public static void updateDoc(EntertainmentLimit limit){

    }
}
