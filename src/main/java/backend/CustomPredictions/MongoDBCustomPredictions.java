package backend.CustomPredictions;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDBCustomPredictions {
    //**********************************************************************
    // Name of the database and collection to use.
    //
    private static final String DB_NAME = "Envisionary";
    private static String COLLECTION_NAME = "CustomPredictions";

    private static MongoClient connectToMongoDB() {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
        ConnectionString mongoUri = new ConnectionString("mongodb+srv://BrandonLaPointe:" + System.getenv("MONGO_DB_PASSWORD") + "@envisionarycluster.19uobkz.mongodb.net/?retryWrites=true&w=majority");
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyConnectionString(mongoUri).build();
        return MongoClients.create(settings);
    }

    public static void insertIndividualDocument(CustomPrediction customPrediction) {
        // Connect to MongoDB CustomPredictions collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<CustomPrediction> collection = database.getCollection(COLLECTION_NAME, CustomPrediction.class);

        // Try inserting CustomPrediction document
        try {
            collection.insertOne(customPrediction);
            System.out.println("Inserted document.\n");
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to insert Custom Predictions into MongoDB due to an error: " + me);
            // System.exit(1);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    public static void insertMultipleDocuments(ArrayList<CustomPrediction> customPredictions) {
        // Connect to MongoDB CustomPredictions collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<CustomPrediction> collection = database.getCollection(COLLECTION_NAME, CustomPrediction.class);

        // Try inserting multiple CustomPrediction documents
        try {
            InsertManyResult result = collection.insertMany(customPredictions);
            System.out.println("Inserted " + result.getInsertedIds().size() + " documents.\n");
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to insert Custom Predictions into MongoDB due to an error: " + me);
            // System.exit(1);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    public static void retrieveCollection() {
        // Connect to MongoDB CustomPredictions collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<CustomPrediction> collection = database.getCollection(COLLECTION_NAME, CustomPrediction.class);

        // Try retreiving the collection of CustomPredictions
        try (MongoCursor<CustomPrediction> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                CustomPrediction currentCustomPrediction = cursor.next();
                System.out.printf("%s : %s\n", currentCustomPrediction.getPrediction().getPredictionMadeDate(), currentCustomPrediction.getPrediction().getPredictionContent());
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find Custom Predictions in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    public static void retrieveCollectionForUser(String userID) {
        // Connect to MongoDB CustomPredictions collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<CustomPrediction> collection = database.getCollection(COLLECTION_NAME, CustomPrediction.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("prediction.userID", userID);

        // Try retrieving the collection of CustomPredictions for the user
        try (MongoCursor<CustomPrediction> cursor = collection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                CustomPrediction currentCustomPrediction = cursor.next();
                System.out.printf("%s : %s\n", currentCustomPrediction.getPrediction().getPredictionMadeDate(), currentCustomPrediction.getPrediction().getPredictionContent());
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find Custom Predictions for user " + userID + " in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    public static void findDocument(String fieldName, String value) {
        // Connect to MongoDB CustomPredictions collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<CustomPrediction> collection = database.getCollection(COLLECTION_NAME, CustomPrediction.class);

        // Set filter
        Bson findCustomPrediction = Filters.eq(fieldName, value);

        // Try to find document
        try {
            CustomPrediction firstCustomPrediction = collection.find(findCustomPrediction).first();
            if (firstCustomPrediction == null) {
                System.out.println("ERROR - Couldn't find any Custom Predictions containing " + fieldName + " : " + value + " in MongoDB - Envisionary - CustomPredictions.");
                // System.exit(1);
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to update Custom Prediction in MongoDB due to an error: " + me);
            // System.exit(1);
        }
        System.out.println("Document found. IMPLEMENT LOGIC");

        // Close the connection when done working with the client
        mongoClient.close();
    }

    public static void updateDocument(String fieldName, String value, String updateVariable, String updateValue) {
        // Connect to MongoDB CustomPredictions collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<CustomPrediction> collection = database.getCollection(COLLECTION_NAME, CustomPrediction.class);

        // Set filter for updater
        Bson findCustomPrediction = Filters.eq(fieldName, value);
        Bson updateFilter = Updates.set(updateVariable, updateValue);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);

        // Try to update document
        try {
            CustomPrediction updatedDocument = collection.findOneAndUpdate(findCustomPrediction, updateFilter, options);
            if (updatedDocument == null) {
                System.out.println("ERROR - Couldn't find any Custom Predictions containing " + fieldName + " : " + value + " in MongoDB - Envisionary - CustomPredictions.");
            } else {
                System.out.println("\nUpdated the Custom Prediction to: " + updatedDocument);
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to update Custom Predictions due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    public static void updateDocument(String fieldName1, String value1, String fieldName2, String value2, String updateVariable, String updateValue) {
        // Connect to MongoDB CustomPredictions collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<CustomPrediction> collection = database.getCollection(COLLECTION_NAME, CustomPrediction.class);

        // Set filter for updater
        Bson deleteFilter = Filters.and(
                Filters.eq(fieldName1, value1),
                Filters.eq(fieldName2, value2)
        );
        Bson findCustomPrediction = Filters.and(Filters.eq(fieldName1, value1),Filters.eq(fieldName2, value2));
        Bson updateFilter = Updates.set(updateVariable, updateValue);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);

        // Try to update document
        try {
            CustomPrediction updatedDocument = collection.findOneAndUpdate(findCustomPrediction, updateFilter, options);
            if (updatedDocument == null) {
                System.out.println("ERROR - Couldn't find any Custom Predictions containing " + fieldName1 + " : " + value1 + " / " + fieldName2 + " : " + value2 + " in MongoDB - Envisionary - CustomPredictions.");
            } else {
                System.out.println("\nUpdated the Custom Prediction to: " + updatedDocument);
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to update any Custom Predictions due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    public static void deleteDocument(String fieldName, String value) {
        // Connect to MongoDB CustomPredictions collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<CustomPrediction> collection = database.getCollection(COLLECTION_NAME, CustomPrediction.class);

        // Set filter
        Bson deleteFilter = Filters.in(fieldName, value);

        // Try to remove the document
        try {
            DeleteResult deleteResult = collection.deleteOne(deleteFilter);
            System.out.printf("\nDeleted %d documents.\n", deleteResult.getDeletedCount());
        } catch (MongoException me) {
            System.err.println("Unable to delete any Custom Predictions due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    public static void deleteDocument(String fieldName1, String value1, String fieldName2, String value2) {
        // Connect to MongoDB CustomPredictions collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<CustomPrediction> collection = database.getCollection(COLLECTION_NAME, CustomPrediction.class);

        // Set filter
        Bson deleteFilter = Filters.and(
                Filters.eq(fieldName1, value1),
                Filters.eq(fieldName2, value2)
        );

        // Try to remove the document
        try {
            DeleteResult deleteResult = collection.deleteOne(deleteFilter);
            System.out.printf("\nDeleted %d documents.\n", deleteResult.getDeletedCount());
        } catch (MongoException me) {
            System.err.println("Unable to delete any Custom Predictions due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    public static void deleteDocuments(String fieldName, String value) {
        // Connect to MongoDB CustomPredictions collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<CustomPrediction> collection = database.getCollection(COLLECTION_NAME, CustomPrediction.class);

        // Set filter
        Bson deleteFilter = Filters.in(fieldName, value);

        // Try to remove the documents
        try {
            DeleteResult deleteResult = collection.deleteMany(deleteFilter);
            System.out.printf("\nDeleted %d documents.\n", deleteResult.getDeletedCount());
        } catch (MongoException me) {
            System.err.println("Unable to delete any Custom Predictions due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    public static void main(String[] args) {
        // TODO : (CRUD) CREATE
        // Inserts a custom prediction into the database
        //CustomPrediction customPrediction = new CustomPrediction(new Prediction("TestUser1", "Custom", "I predict that the issues with this code will be resolved", ZonedDateTime.now().toString(), ZonedDateTime.now().plusWeeks(2).toString(), "Daily"), "Testing");
        //insertIndividualDocument(customPrediction);

        // TODO : (CRUD) READ
        // Retreive the entire collection and display (Will display all users)
        retrieveCollection();

        // Retreive all custom predictions made by one user
        //retrieveCollectionForUser("TestUser3");

        // Finds document? Implement read / return logic after finding
        //findDocument("prediction.userID", "TestUser1");

        // TODO : (CRUD) UPDATE
        // Updates end date of the first occurrence of a specified user's custom prediction
        //updateDocument("prediction.userID", "TestUser1", "endDate", ZonedDateTime.now().plusDays(7).toString());

        // Updates end date of the first occurrence of a specified userID and predictionContent
        //updateDocument("prediction.userID", "TestUser1","prediction.predictionContent","I predict that the issues with this code will be resolved", "endDate", ZonedDateTime.now().plusDays(7).toString());

        // TODO : (CRUD) DELETE
        // Deletes first occurrence of a custom prediction made by a specified user
        //deleteDocument("prediction.userID", "TestUser2");

        // Deletes the first occurrence of a custom prediction containing the matching predictionContent
        // (Mix-up between "predictionContent" and "predictionContext" within ResolvedPredictions.ResolvedPredictionInitializer, CustomPredictionInitializer, FootballMatchPredictions.FootballMatchPrediction, FootballMatchPredictions.FootballMatchPredictionInitializer, and FootballMatchPredictions.FootballMatchPredictionUpdater)
        //deleteDocument("prediction.predictionContent","I predict that the issues with this code will be resolved");

        // Deletes the first occurrence of a custom prediction containing the matching userID and predictionContent
        //deleteDocument("prediction.userID","TestUser4","prediction.predictionContent","I predict that the issues with this code will be resolved");

        // Deletes all custom predictions made by a specified user
        //deleteDocuments("prediction.userID","TestUser1");
    }
}