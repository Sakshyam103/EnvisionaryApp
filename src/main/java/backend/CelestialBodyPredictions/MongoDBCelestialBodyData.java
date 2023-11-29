package backend.CelestialBodyPredictions;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// CelestialBodyPredictions.MongoDBCelestialBodyData class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Connects to MongoDB for interaction with Envisionary CelestialBodyPredictions.CelestialBody data.
//
public class MongoDBCelestialBodyData {
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Name of the database and collection to use.
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    private static final String DB_NAME = "Envisionary";
    private static String COLLECTION_NAME = "CelestialBodyData";

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // connectToMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
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

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // insertIndividualDocument
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void insertIndividualDocument(CelestialBody celestialBody) {
        // Connect to MongoDB FootballMatchData collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<CelestialBody> collection = database.getCollection(COLLECTION_NAME, CelestialBody.class);

        // Try inserting FootballMatchPredictions.FootballMatch document
        try {
            collection.insertOne(celestialBody);
            System.out.println("Inserted document.\n");
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to insert Celestial Body Data into MongoDB due to an error: " + me);
            // System.exit(1);
        }
        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // insertMultipleDocuments
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void insertMultipleDocuments(ArrayList<CelestialBody> celestialBodyList) {
        // Connect to MongoDB CelestialBodyData collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<CelestialBody> collection = database.getCollection(COLLECTION_NAME, CelestialBody.class);

        // Try inserting multiple CelestialBodyList documents
        try {
            collection.insertMany(celestialBodyList);
            System.out.println("Inserted multiple documents.\n");
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to insert Celestial Body Data into MongoDB due to an error: " + me);
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
        // Connect to MongoDB CelestialBodyData collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<CelestialBody> collection = database.getCollection(COLLECTION_NAME, CelestialBody.class);

        // Try retrieving the collection of CelestialBodyData
        try (MongoCursor<CelestialBody> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                CelestialBody currentCelestialBody = cursor.next();
                System.out.println(currentCelestialBody + "\n");
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find any Celestial Body Data in MongoDB due to an error: " + me);
        }
        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveCollection
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static ArrayList<CelestialBody> retrieveCollection() {
        // Connect to MongoDB CelestialBodyData collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<CelestialBody> collection = database.getCollection(COLLECTION_NAME, CelestialBody.class);

        // Initialize an ArrayList to store the retrieved data
        ArrayList<CelestialBody> celestialBodyList = new ArrayList<>();

        // Try retrieving the collection of CelestialBodyData
        try (MongoCursor<CelestialBody> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                // Add the retrieved CelestialBodyPredictions.CelestialBody to the list
                celestialBodyList.add(cursor.next());
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find any Celestial Body Data in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();

        // Return the ArrayList of CelestialBodyPredictions.CelestialBody
        return celestialBodyList;
    }

    public static void updateDocuments(ArrayList<CelestialBody> celestialBodyList) {
        // Connect to MongoDB CelestialBodyData collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<CelestialBody> collection = database.getCollection(COLLECTION_NAME, CelestialBody.class);

        // Iterate through the list of CelestialBodyPredictions.CelestialBody and update each document
        for (CelestialBody currentCelestialBody : celestialBodyList) {
            // Set filter for the current CelestialBodyPredictions.CelestialBody
            Bson filter = Filters.eq("celestialBodyType", currentCelestialBody.getCelestialBodyType());

            // Create update document with new values
            Bson update = Updates.combine(
                    Updates.set("celestialBodyType", currentCelestialBody.getCelestialBodyType()),
                    Updates.set("knownCount", currentCelestialBody.getKnownCount()),
                    Updates.set("updatedDate", currentCelestialBody.getUpdatedDate())
            );

            // Use upsert option to create a new document if no match is found
            UpdateOptions options = new UpdateOptions().upsert(true);

            // Try to update the current CelestialBodyPredictions.CelestialBody
            try {
                // Use the setter method to update the celestialBodyType field
                currentCelestialBody.setCelestialBodyType(currentCelestialBody.getCelestialBodyType());

                UpdateResult updateResult = collection.updateOne(filter, update, options);
                if (updateResult.getModifiedCount() > 0) {
                    System.out.println("Successfully updated CelestialBodyPredictions.CelestialBody: " + currentCelestialBody.getCelestialBodyType());
                } else {
                    System.out.println("No changes made for CelestialBodyPredictions.CelestialBody: " + currentCelestialBody.getCelestialBodyType());
                }
            } catch (MongoException me) {
                System.err.println("ERROR - Unable to update CelestialBodyPredictions.CelestialBody: " + currentCelestialBody.getCelestialBodyType() + " due to an error: " + me);
            }
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // deleteAllDocuments
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void deleteAllDocuments() {
        // Connect to MongoDB CelestialBodyData collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<CelestialBody> collection = database.getCollection(COLLECTION_NAME, CelestialBody.class);

        // Try to remove all documents
        try {
            DeleteResult deleteResult = collection.deleteMany(Filters.exists("_id")); // Match all documents
            System.out.printf("\nDeleted %d documents.\n", deleteResult.getDeletedCount());
        } catch (MongoException me) {
            System.err.println("Unable to delete any Celestial Body Data due to an error: " + me);
        }
        // Close the connection when done working with the client
        mongoClient.close();
    }

    public static void main(String[] args) {
        // TODO: CRUD CREATE
        //insertMultipleDocuments(CelestialBodyPredictions.CelestialBodiesAPI.getSortedData(CelestialBodyPredictions.CelestialBodiesAPI.getSpaceData()));

        // TODO: CRUD READ
        //retrieveCollectionAndDisplay();

        //ArrayList<CelestialBodyPredictions.CelestialBody> celestialBodies = CelestialBodyPredictions.MongoDBCelestialBodyData.retrieveCollection();
        //System.out.println(celestialBodies);

        // TODO: CRUD UPDATE
        updateDocuments(CelestialBodiesAPI.getSortedData(CelestialBodiesAPI.getSpaceData()));

        retrieveCollectionAndDisplay();

        // TODO CRUD DELETE
        //deleteAllDocuments();
    }
}
