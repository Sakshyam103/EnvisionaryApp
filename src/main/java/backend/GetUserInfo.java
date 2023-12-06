package backend;

import backend.UserInfo.EnvisionaryUser;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class GetUserInfo {

    public static MongoCollection<Document> envisionaryUsersCollection;

    public static Document getTheDoc(){
        MongoClient client = connectToMongoDB();
        MongoDatabase database = client.getDatabase(System.getenv("MONGO_DATABASE"));
        envisionaryUsersCollection = database.getCollection("EnvisionaryUsers");
        Bson userFilter = Filters.eq("userID", Controller.userId);
        Document userInfo = new Document();
        try (MongoCursor<Document> cursor = envisionaryUsersCollection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                userInfo = cursor.next();
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + Controller.userId + " in MongoDB due to an error: " + me);
        }
        return userInfo;
    }

    private static MongoClient connectToMongoDB() {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
        ConnectionString mongoUri = new ConnectionString("mongodb+srv://BrandonLaPointe:L5AMM7CiM1F2qjz@envisionarycluster.19uobkz.mongodb.net/?retryWrites=true&w=majority");
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyConnectionString(mongoUri).build();
        return MongoClients.create(settings);
    }
}
