package backend.OverallStatistics;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
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

public class MongoDBOverallInferentialStatistics {
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Name of the database and collection to use.
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    private static final String DB_NAME = "Envisionary";
    private static String COLLECTION_NAME = "OverallStatistics.OverallInferentialStatistics";

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
    public static void insertIndividualDocument(OverallInferentialStatistics overallInferentialStatistics) {
        // Connect to MongoDB FootballMatchData collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<OverallInferentialStatistics> collection = database.getCollection(COLLECTION_NAME, OverallInferentialStatistics.class);

        // Try inserting FootballMatchPredictions.FootballMatch document
        try {
            collection.insertOne(overallInferentialStatistics);
            System.out.println("Inserted document.\n");
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to insert Overall Inferential Statistics Data into MongoDB due to an error: " + me);
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
        // Connect to MongoDB OverallStatistics.OverallInferentialStatistics collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<OverallInferentialStatistics> collection = database.getCollection(COLLECTION_NAME, OverallInferentialStatistics.class);

        // Try retrieving the collection of OverallStatistics.OverallInferentialStatistics
        try (MongoCursor<OverallInferentialStatistics> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                OverallInferentialStatistics currentOverallInferentialStatistics = cursor.next();
                currentOverallInferentialStatistics.printOverallInferentialStatistics();
                System.out.println(); // Add a line break for separation
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find Overall Inferential Statistics Data in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveCollection
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static OverallInferentialStatistics retrieveCollection() {
        // Connect to MongoDB OverallStatistics.OverallInferentialStatistics collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<OverallInferentialStatistics> collection = database.getCollection(COLLECTION_NAME, OverallInferentialStatistics.class);

        // Initialize a new OverallStatistics.OverallInferentialStatistics object to store the retrieved data
        OverallInferentialStatistics overallInferentialStatistics = null;

        // Try retrieving the collection of OverallStatistics.OverallInferentialStatistics
        try (MongoCursor<OverallInferentialStatistics> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                // Add the retrieved OverallStatistics.OverallInferentialStatistics to the list
                overallInferentialStatistics = cursor.next();
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find Overall Inferential Statistics Data in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();

        // Return the ArrayList of OverallStatistics.OverallInferentialStatistics
        return overallInferentialStatistics;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateDocuments
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void updateDocument(OverallInferentialStatistics overallInferentialStatistics) {
        // Connect to MongoDB OverallStatistics.OverallInferentialStatistics collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<OverallInferentialStatistics> collection = database.getCollection(COLLECTION_NAME, OverallInferentialStatistics.class);

        // Use a field that always exists in your document for the filter
        Bson filter = Filters.exists("customPredictionCount");

        // Create update document with new values
        Bson update = Updates.combine(
                Updates.set("customPredictionCount", overallInferentialStatistics.getCustomPredictionCount()),
                Updates.set("sportsPredictionCount", overallInferentialStatistics.getSportsPredictionCount()),
                Updates.set("sciencePredictionCount", overallInferentialStatistics.getSciencePredictionCount()),
                Updates.set("weatherPredictionCount", overallInferentialStatistics.getWeatherPredictionCount()),
                Updates.set("entertainmentPredictionCount", overallInferentialStatistics.getEntertainmentPredictionCount()),
                Updates.set("predictionsMadeCount", overallInferentialStatistics.getPredictionsMadeCount()),
                Updates.set("customCorrectPredictions", overallInferentialStatistics.getCustomCorrectPredictions()),
                Updates.set("sportsCorrectPredictions", overallInferentialStatistics.getSportsCorrectPredictions()),
                Updates.set("scienceCorrectPredictions", overallInferentialStatistics.getScienceCorrectPredictions()),
                Updates.set("weatherCorrectPredictions", overallInferentialStatistics.getWeatherCorrectPredictions()),
                Updates.set("entertainmentCorrectPredictions", overallInferentialStatistics.getEntertainmentCorrectPredictions()),
                Updates.set("customIncorrectPredictions", overallInferentialStatistics.getCustomIncorrectPredictions()),
                Updates.set("sportsIncorrectPredictions", overallInferentialStatistics.getSportsIncorrectPredictions()),
                Updates.set("scienceIncorrectPredictions", overallInferentialStatistics.getScienceIncorrectPredictions()),
                Updates.set("weatherIncorrectPredictions", overallInferentialStatistics.getWeatherIncorrectPredictions()),
                Updates.set("entertainmentIncorrectPredictions", overallInferentialStatistics.getEntertainmentIncorrectPredictions()),
                Updates.set("customPercentCorrect", overallInferentialStatistics.getCustomPercentCorrect()),
                Updates.set("sportsPercentCorrect", overallInferentialStatistics.getSportsPercentCorrect()),
                Updates.set("sciencePercentCorrect", overallInferentialStatistics.getSciencePercentCorrect()),
                Updates.set("weatherPercentCorrect", overallInferentialStatistics.getWeatherPercentCorrect()),
                Updates.set("entertainmentPercentCorrect", overallInferentialStatistics.getEntertainmentPercentCorrect()),
                Updates.set("customPercentIncorrect", overallInferentialStatistics.getCustomPercentIncorrect()),
                Updates.set("sportsPercentIncorrect", overallInferentialStatistics.getSportsPercentIncorrect()),
                Updates.set("sciencePercentIncorrect", overallInferentialStatistics.getSciencePercentIncorrect()),
                Updates.set("weatherPercentIncorrect", overallInferentialStatistics.getWeatherPercentIncorrect()),
                Updates.set("entertainmentPercentIncorrect", overallInferentialStatistics.getEntertainmentPercentIncorrect()),
                Updates.set("customPercentPredictionsMade", overallInferentialStatistics.getCustomPercentPredictionsMade()),
                Updates.set("sportsPercentPredictionsMade", overallInferentialStatistics.getSportsPercentPredictionsMade()),
                Updates.set("sciencePercentPredictionsMade", overallInferentialStatistics.getSciencePercentPredictionsMade()),
                Updates.set("weatherPercentPredictionsMade", overallInferentialStatistics.getWeatherPercentPredictionsMade()),
                Updates.set("entertainmentPercentPredictionsMade", overallInferentialStatistics.getEntertainmentPercentPredictionsMade()),
                Updates.set("mostCorrectPredictionType", overallInferentialStatistics.getMostCorrectPredictionType()),
                Updates.set("leastCorrectPredictionType", overallInferentialStatistics.getLeastCorrectPredictionType()),
                Updates.set("minPercentCorrectCustom", overallInferentialStatistics.getMinPercentCorrectCustom()),
                Updates.set("maxPercentCorrectCustom", overallInferentialStatistics.getMaxPercentCorrectCustom()),
                Updates.set("minPercentCorrectSports", overallInferentialStatistics.getMinPercentCorrectSports()),
                Updates.set("maxPercentCorrectSports", overallInferentialStatistics.getMaxPercentCorrectSports()),
                Updates.set("minPercentCorrectScience", overallInferentialStatistics.getMinPercentCorrectScience()),
                Updates.set("maxPercentCorrectScience", overallInferentialStatistics.getMaxPercentCorrectScience()),
                Updates.set("minPercentCorrectWeather", overallInferentialStatistics.getMinPercentCorrectWeather()),
                Updates.set("maxPercentCorrectWeather", overallInferentialStatistics.getMaxPercentCorrectWeather()),
                Updates.set("minPercentCorrectEntertainment", overallInferentialStatistics.getMinPercentCorrectEntertainment()),
                Updates.set("maxPercentCorrectEntertainment", overallInferentialStatistics.getMaxPercentCorrectEntertainment()),
                Updates.set("meanCorrectPercentageCustom", overallInferentialStatistics.getMeanCorrectPercentageCustom()),
                Updates.set("meanCorrectPercentageSports", overallInferentialStatistics.getMeanCorrectPercentageSports()),
                Updates.set("meanCorrectPercentageScience", overallInferentialStatistics.getMeanCorrectPercentageScience()),
                Updates.set("meanCorrectPercentageWeather", overallInferentialStatistics.getMeanCorrectPercentageWeather()),
                Updates.set("meanCorrectPercentageEntertainment", overallInferentialStatistics.getMeanCorrectPercentageEntertainment()),
                Updates.set("medianCorrectPercentageCustom", overallInferentialStatistics.getMedianCorrectPercentageCustom()),
                Updates.set("medianCorrectPercentageSports", overallInferentialStatistics.getMedianCorrectPercentageSports()),
                Updates.set("medianCorrectPercentageScience", overallInferentialStatistics.getMedianCorrectPercentageScience()),
                Updates.set("medianCorrectPercentageWeather", overallInferentialStatistics.getMedianCorrectPercentageWeather()),
                Updates.set("medianCorrectPercentageEntertainment", overallInferentialStatistics.getMedianCorrectPercentageEntertainment()),
                Updates.set("modeCorrectPercentageCustom", overallInferentialStatistics.getModeCorrectPercentageCustom()),
                Updates.set("modeCorrectPercentageSports", overallInferentialStatistics.getModeCorrectPercentageSports()),
                Updates.set("modeCorrectPercentageScience", overallInferentialStatistics.getModeCorrectPercentageScience()),
                Updates.set("modeCorrectPercentageWeather", overallInferentialStatistics.getModeCorrectPercentageWeather()),
                Updates.set("modeCorrectPercentageEntertainment", overallInferentialStatistics.getModeCorrectPercentageEntertainment()),
                Updates.set("standardDeviationCorrectPercentageCustom", overallInferentialStatistics.getStandardDeviationCorrectPercentageCustom()),
                Updates.set("standardDeviationCorrectPercentageSports", overallInferentialStatistics.getStandardDeviationCorrectPercentageSports()),
                Updates.set("standardDeviationCorrectPercentageScience", overallInferentialStatistics.getStandardDeviationCorrectPercentageScience()),
                Updates.set("standardDeviationCorrectPercentageWeather", overallInferentialStatistics.getStandardDeviationCorrectPercentageWeather()),
                Updates.set("standardDeviationCorrectPercentageEntertainment", overallInferentialStatistics.getStandardDeviationCorrectPercentageEntertainment())
                //,Updates.set("updatedDate", ZonedDateTime.now()) // Add and update an "updatedDate" field with the current ZonedDateTime to keep track of updates
        );

        // Use upsert option to create a new document if no match is found
        UpdateOptions options = new UpdateOptions().upsert(true);

        // Try to update the current OverallStatistics.OverallInferentialStatistics
        try {
            UpdateResult updateResult = collection.updateOne(filter, update, options);
            if (updateResult.getModifiedCount() > 0) {
                System.out.println("Successfully updated OverallStatistics.OverallInferentialStatistics");
            } else {
                System.out.println("No changes made for OverallStatistics.OverallInferentialStatistics");
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to update OverallStatistics.OverallInferentialStatistics due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // deleteAllDocuments
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void deleteAllDocuments() {
        // Connect to MongoDB OverallStatistics.OverallInferentialStatistics collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<OverallInferentialStatistics> collection = database.getCollection(COLLECTION_NAME, OverallInferentialStatistics.class);

        // Try to remove all documents
        try {
            DeleteResult deleteResult = collection.deleteMany(Filters.exists("_id")); // Match all documents
            System.out.printf("\nDeleted %d documents.\n", deleteResult.getDeletedCount());
        } catch (MongoException me) {
            System.err.println("Unable to delete Overall Inferential Statistics Data due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    public static void main(String[] args) {
        // TODO: CRUD CREATE
        //insertIndividualDocument(OverallStatistics.OverallInferentialStatisticsUpdater.loadOverallInferentialStatistics());

        // TODO: CRUD READ
        //retrieveCollectionAndDisplay();

        //OverallStatistics.OverallInferentialStatistics overallInferentialStatistics = OverallStatistics.MongoDBOverallInferentialStatistics.retrieveCollection();
        //overallInferentialStatistics.printOverallInferentialStatistics();

        // TODO: CRUD UPDATE
        updateDocument(OverallInferentialStatisticsUpdater.loadOverallInferentialStatistics());

        // TODO CRUD DELETE
        //deleteAllDocuments();
    }
}