package backend.WeatherPredictions;

import backend.CelestialBodyPredictions.CelestialBodiesAPI;
import backend.CelestialBodyPredictions.CelestialBody;
import backend.CelestialBodyPredictions.MongoDBCelestialBodyData;
import backend.OverallStatistics.OverallDescriptiveStatistics;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDBWeatherData {
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Name of the database and collection to use.
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    private static final String DB_NAME = "Envisionary";
    private static String COLLECTION_NAME = "WeatherData";

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // connectToMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    private static MongoClient connectToMongoDB() {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
        ConnectionString mongoUri = new ConnectionString("mongodb+srv://" + System.getenv("MONGO_USER") + ":" + System.getenv("MONGO_DB_PASSWORD") + "@" + System.getenv("MONGO_CLUSTER") + ".19uobkz.mongodb.net/?retryWrites=true&w=majority");
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyConnectionString(mongoUri).build();
        return MongoClients.create(settings);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // insertIndividualDocument
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void insertIndividualDocument(DailyForecast todaysWeather) {
        // Connect to MongoDB FootballMatchData collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<DailyForecast> collection = database.getCollection(COLLECTION_NAME, DailyForecast.class);

        // Try inserting a document
        try {
            collection.insertOne(todaysWeather);
            System.out.println("Inserted document.\n");
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to insert Today's Weather Data into MongoDB due to an error: " + me);
            // System.exit(1);
        }
        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveCollection
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static DailyForecast retrieveCollection() {
        // Connect to MongoDB OverallStatistics.OverallDescriptiveStatistics collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<DailyForecast> collection = database.getCollection(COLLECTION_NAME, DailyForecast.class);

        // Initialize a new DailyForecast object to store the retrieved data
        DailyForecast todaysWeather = null;

        // Try retrieving the collection of OverallStatistics.OverallDescriptiveStatistics
        try (MongoCursor<DailyForecast> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                // Add the retrieved OverallStatistics.OverallDescriptiveStatistics to the list
                todaysWeather = cursor.next();
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find Today's Weather Data in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();

        // Return the ArrayList of OverallStatistics.OverallDescriptiveStatistics
        return todaysWeather;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateDocument
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void updateDocument(DailyForecast todaysWeather) {
        // Connect to MongoDB CelestialBodyData collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<DailyForecast> collection = database.getCollection(COLLECTION_NAME, DailyForecast.class);

        // Try updating the document
        try {
            // Create a filter based on the date (assuming dt is unique for each day)
            Bson filter = Filters.eq("");

            // Replace the existing document if it exists, otherwise insert a new one
            UpdateOptions updateOptions = new UpdateOptions().upsert(true);
            collection.replaceOne(filter, todaysWeather);

            System.out.println("Inserted/Updated document.\n");
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to insert/update Today's Weather Data into MongoDB due to an error: " + me);
            // Handle the error appropriately
        } finally {
            // Close the connection when done working with the client
            mongoClient.close();
        }
    }
}
