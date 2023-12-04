package backend.OverallStatistics;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDBOverallDescriptiveStatistics {
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Name of the database and collection to use.
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    private static final String DB_NAME = "Envisionary";
    private static String COLLECTION_NAME = "OverallStatistics.OverallDescriptiveStatistics";

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
    public static void insertIndividualDocument(OverallDescriptiveStatistics overallDescriptiveStatistics) {
        // Connect to MongoDB FootballMatchData collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<OverallDescriptiveStatistics> collection = database.getCollection(COLLECTION_NAME, OverallDescriptiveStatistics.class);

        // Try inserting FootballMatchPredictions.FootballMatch document
        try {
            collection.insertOne(overallDescriptiveStatistics);
            System.out.println("Inserted document.\n");
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to insert Overall Descriptive Statistics Data into MongoDB due to an error: " + me);
            // System.exit(1);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveCollectionAndDisplay
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void retrieveCollectionAndDisplay() {
        // Connect to MongoDB OverallStatistics.OverallDescriptiveStatistics collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<OverallDescriptiveStatistics> collection = database.getCollection(COLLECTION_NAME, OverallDescriptiveStatistics.class);

        // Try retrieving the collection of OverallStatistics.OverallDescriptiveStatistics
        try (MongoCursor<OverallDescriptiveStatistics> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                OverallDescriptiveStatistics currentOverallDescriptiveStatistics = cursor.next();
                currentOverallDescriptiveStatistics.printOverallDescriptiveStatistics();
                System.out.println(); // Add a line break for separation
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find Overall Descriptive Statistics Data in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveCollection
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static OverallDescriptiveStatistics retrieveCollection() {
        // Connect to MongoDB OverallStatistics.OverallDescriptiveStatistics collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<OverallDescriptiveStatistics> collection = database.getCollection(COLLECTION_NAME, OverallDescriptiveStatistics.class);

        // Initialize a new OverallStatistics.OverallDescriptiveStatistics object to store the retrieved data
        OverallDescriptiveStatistics overallDescriptiveStatistics = null;

        // Try retrieving the collection of OverallStatistics.OverallDescriptiveStatistics
        try (MongoCursor<OverallDescriptiveStatistics> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                // Add the retrieved OverallStatistics.OverallDescriptiveStatistics to the list
                overallDescriptiveStatistics = cursor.next();
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find Overall Descriptive Statistics Data in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();

        // Return the ArrayList of OverallStatistics.OverallDescriptiveStatistics
        return overallDescriptiveStatistics;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateDocuments
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void updateDocument(OverallDescriptiveStatistics overallDescriptiveStatistics) {
        // Connect to MongoDB OverallStatistics.OverallDescriptiveStatistics collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<OverallDescriptiveStatistics> collection = database.getCollection(COLLECTION_NAME, OverallDescriptiveStatistics.class);

        // Use a field that always exists in your document for the filter
        Bson filter = Filters.exists("overallPredictionCount");

        // Create update document with new values
        Bson update = Updates.combine(
                Updates.set("overallPredictionCount", overallDescriptiveStatistics.getOverallPredictionCount()),
                Updates.set("overallCorrectPredictions", overallDescriptiveStatistics.getOverallCorrectPredictions()),
                Updates.set("overallIncorrectPredictions", overallDescriptiveStatistics.getOverallIncorrectPredictions()),
                Updates.set("overallPercentCorrect", overallDescriptiveStatistics.getOverallPercentCorrect()),
                Updates.set("overallPercentIncorrect", overallDescriptiveStatistics.getOverallPercentIncorrect()),
                Updates.set("minPercentCorrect", overallDescriptiveStatistics.getMinPercentCorrect()),
                Updates.set("maxPercentCorrect", overallDescriptiveStatistics.getMaxPercentCorrect()),
                Updates.set("meanCorrectPercentage", overallDescriptiveStatistics.getMeanCorrectPercentage()),
                Updates.set("medianCorrectPercentage", overallDescriptiveStatistics.getMedianCorrectPercentage()),
                Updates.set("modeCorrectPercentage", overallDescriptiveStatistics.getModeCorrectPercentage()),
                Updates.set("standardDeviation", overallDescriptiveStatistics.getStandardDeviation())
                //,Updates.set("updatedDate", ZonedDateTime.now()) // Add and update an "updatedDate" field with the current ZonedDateTime to keep track of updates
        );

        // Try to update the current OverallStatistics.OverallDescriptiveStatistics
        try {
            UpdateResult updateResult = collection.updateOne(filter, update);
            if (updateResult.getModifiedCount() > 0) {
                System.out.println("Successfully updated OverallStatistics.OverallDescriptiveStatistics");
            } else {
                System.out.println("No changes made for OverallStatistics.OverallDescriptiveStatistics");
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to update OverallStatistics.OverallDescriptiveStatistics due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // deleteAllDocuments
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void deleteAllDocuments() {
        // Connect to MongoDB OverallStatistics.OverallDescriptiveStatistics collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<OverallDescriptiveStatistics> collection = database.getCollection(COLLECTION_NAME, OverallDescriptiveStatistics.class);

        // Try to remove all documents
        try {
            DeleteResult deleteResult = collection.deleteMany(Filters.exists("_id")); // Match all documents
            System.out.printf("\nDeleted %d documents.\n", deleteResult.getDeletedCount());
        } catch (MongoException me) {
            System.err.println("Unable to delete Overall Descriptive Statistics Data due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    public static void main(String[] args) {
        // TODO: CRUD CREATE
        //insertIndividualDocument(OverallStatistics.OverallDescriptiveStatisticsUpdater.loadOverallDescriptiveStatistics());

        // TODO: CRUD READ
        //retrieveCollectionAndDisplay();

        //OverallStatistics.OverallDescriptiveStatistics overallDescriptiveStatistics = OverallStatistics.MongoDBOverallDescriptiveStatistics.retrieveCollection();
        //overallDescriptiveStatistics.printOverallDescriptiveStatistics();

        // TODO: CRUD UPDATE
        //updateDocument(OverallStatistics.OverallDescriptiveStatisticsUpdater.loadOverallDescriptiveStatistics());

        // TODO CRUD DELETE
        //deleteAllDocuments();
    }
}
