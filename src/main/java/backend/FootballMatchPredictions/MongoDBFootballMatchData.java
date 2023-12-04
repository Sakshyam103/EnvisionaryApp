package backend.FootballMatchPredictions;

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
// MongoDBFootballMatchData class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Connects to MongoDB for interaction with Envisionary Football Match data.
//
public class MongoDBFootballMatchData {
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Name of the database and collection to use.
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    private static final String DB_NAME = "Envisionary";
    private static String COLLECTION_NAME = "FootballMatchData";

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
    public static void insertIndividualDocument(FootballMatchList footballMatchList) {
        // Connect to MongoDB FootballMatchData collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<FootballMatchList> collection = database.getCollection(COLLECTION_NAME, FootballMatchList.class);

        // Try inserting FootballMatch document
        try {
            collection.insertOne(footballMatchList);
            System.out.println("Inserted document.\n");
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to insert FootballMatchListData into MongoDB due to an error: " + me);
            // System.exit(1);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // insertMultipleDocuments
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void insertMultipleDocuments(ArrayList<FootballMatchList> footballMatchLists) {
        // Connect to MongoDB FootballMatchData collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<FootballMatchList> collection = database.getCollection(COLLECTION_NAME, FootballMatchList.class);

        // Try inserting multiple FootballMatchList documents
        try {
            collection.insertMany(footballMatchLists);
            System.out.println("Inserted multiple documents.\n");
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to insert FootballMatchListData into MongoDB due to an error: " + me);
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
        // Connect to MongoDB FootballMatchData collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<FootballMatchList> collection = database.getCollection(COLLECTION_NAME, FootballMatchList.class);

        // Try retreiving the collection of FootballMatchData
        try (MongoCursor<FootballMatchList> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                FootballMatchList currentFootballMatchList = cursor.next();
                System.out.printf("%s\n", currentFootballMatchList);
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find any FootballMatchData in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveCollectionAndReturn
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static FootballMatchList retrieveCollectionAndReturn() {
        // Connect to MongoDB FootballMatchData collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<FootballMatchList> collection = database.getCollection(COLLECTION_NAME, FootballMatchList.class);

        // Initialize a FootballMatchList object to store the retrieved data
        FootballMatchList footballMatchList = new FootballMatchList();

        // Try retrieving the collection of FootballMatchData
        try (MongoCursor<FootballMatchList> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                // Instead of printing, add the retrieved FootballMatchList to the object
                footballMatchList = cursor.next();
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find any FootballMatchData in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();

        // Return the FootballMatchList object
        return footballMatchList;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveCollectionTimeFrameAndDisplay
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void retrieveCollectionTimeFrameAndDisplay(String value) {
        // Connect to MongoDB FootballMatchData collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<FootballMatchList> collection = database.getCollection(COLLECTION_NAME, FootballMatchList.class);

        // Set filter for "matchListTimeFrame" equals "Tomorrow"
        Bson filter = Filters.eq("matchListTimeFrame", value);

        // Try retrieving the collection of FootballMatchData
        try (MongoCursor<FootballMatchList> cursor = collection.find(filter).iterator()) {
            while (cursor.hasNext()) {
                FootballMatchList currentFootballMatchList = cursor.next();
                if (currentFootballMatchList == null || currentFootballMatchList.getFootballMatches().isEmpty()) {
                    System.out.println("ERROR - No Football Matches Available for TimeFrame: " + value);
                }
                for (FootballMatch footballMatch : currentFootballMatchList.getFootballMatches()) {
                    footballMatch.printKeyMatchDetails();
                    System.out.println();
                }
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find any FootballMatchData in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveCollectionTimeFrameAndDisplay
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static ArrayList<FootballMatch> retrieveCollectionTimeFrameAndReturn(String value) {
        // Connect to MongoDB FootballMatchData collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<FootballMatchList> collection = database.getCollection(COLLECTION_NAME, FootballMatchList.class);

        // Set filter for "matchListTimeFrame" equals "Tomorrow"
        Bson filter = Filters.eq("matchListTimeFrame", value);

        // Try retrieving the collection of FootballMatchData
        try (MongoCursor<FootballMatchList> cursor = collection.find(filter).iterator()) {
            while (cursor.hasNext()) {
                FootballMatchList currentFootballMatchList = cursor.next();

                // Close the connection when done working with the client
                mongoClient.close();

                // Return the array list of football matches
                return currentFootballMatchList.getFootballMatches();
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find any FootballMatchData in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();

        return null;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveDocumentsWithinTimeFrameAndReturn
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static FootballMatchList retrieveDocumentsWithinTimeFrameAndReturn(String timeFrame) {
        // Connect to MongoDB FootballMatchData collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<FootballMatchList> collection = database.getCollection(COLLECTION_NAME, FootballMatchList.class);

        // Set filter for "matchListTimeFrame" equals the specified value
        Bson filter = Filters.eq("matchListTimeFrame", timeFrame);

        // Initialize a FootballMatchList object to store the retrieved data
        FootballMatchList footballMatchList = new FootballMatchList();

        // Try retrieving the collection of FootballMatchData
        try (MongoCursor<FootballMatchList> cursor = collection.find(filter).iterator()) {
            while (cursor.hasNext()) {
                // Add the retrieved FootballMatchList to the object
                footballMatchList = cursor.next();
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find any FootballMatchData in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();

        // Return the FootballMatchList object
        return footballMatchList;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateDocumentsWithinTimeframe
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void updateDocumentsWithinTimeframe(String timeFrame, ArrayList<FootballMatch> footballMatchList) {
        // Connect to MongoDB FootballMatchData collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<FootballMatchList> collection = database.getCollection(COLLECTION_NAME, FootballMatchList.class);

        // Set filter for "matchListTimeFrame" equals the specified value
        Bson filter = Filters.eq("matchListTimeFrame", timeFrame);

        // Create update document with new values
        Bson update = Updates.set("footballMatches", footballMatchList);

        // Use upsert option to create a new document if no match is found
        UpdateOptions options = new UpdateOptions().upsert(true);

        // Try to update the current FootballMatchList
        try {
            UpdateResult updateResult = collection.updateOne(filter, update, options);
            if (updateResult.getModifiedCount() > 0) {
                System.out.println("Successfully updated FootballMatchList for time frame: " + timeFrame);
            } else {
                System.out.println("No changes made for FootballMatchList with time frame: " + timeFrame);
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to update FootballMatchList for time frame: " + timeFrame + " due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateAllDocuments
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void updateAllDocuments() {
        updateDocumentsWithinTimeframe("Yesterday",FootballMatchUpdater.readAndReturnYesterdayMatchesFile());
        updateDocumentsWithinTimeframe("Today",FootballMatchUpdater.readAndReturnTodayMatchesFile());
        updateDocumentsWithinTimeframe("Tomorrow",FootballMatchUpdater.readAndReturnTomorrowMatchesFile());
        updateDocumentsWithinTimeframe("UpcomingWeek1",FootballMatchUpdater.readAndReturnUpcomingWeek1MatchesFile());
        updateDocumentsWithinTimeframe("UpcomingWeek2",FootballMatchUpdater.readAndReturnUpcomingWeek2MatchesFile());
        updateDocumentsWithinTimeframe("UpcomingWeek3",FootballMatchUpdater.readAndReturnUpcomingWeek3MatchesFile());
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // deleteAllDocuments
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void deleteAllDocuments() {
        // Connect to MongoDB FootballMatchData collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<FootballMatchList> collection = database.getCollection(COLLECTION_NAME, FootballMatchList.class);

        // Try to remove all documents
        try {
            DeleteResult deleteResult = collection.deleteMany(Filters.exists("matchListTimeFrame")); // Match all documents
            System.out.printf("\nDeleted %d documents.\n", deleteResult.getDeletedCount());
        } catch (MongoException me) {
            System.err.println("Unable to delete any FootballMatchData due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // main (Example Usage)
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void main(String[] args) {
        // TODO : (CRUD) CREATE
        // Inserts a football match list into the database
        //FootballMatchList yesterdayFootballMatchList = new FootballMatchList("Yesterday", FootballMatchUpdater.readAndReturnYesterdayMatchesFile());
        //insertIndividualDocument(yesterdayFootballMatchList);

        //FootballMatchList todayFootballMatchList = new FootballMatchList("Today", FootballMatchUpdater.readAndReturnTodayMatchesFile());
        //FootballMatchList tomorrowFootballMatchList = new FootballMatchList("Tomorrow", FootballMatchUpdater.readAndReturnTomorrowMatchesFile());
        //FootballMatchList upcomingWeek1FootballMatchList = new FootballMatchList("UpcomingWeek1", FootballMatchUpdater.readAndReturnUpcomingWeek1MatchesFile());
        //FootballMatchList upcomingWeek2FootballMatchList = new FootballMatchList("UpcomingWeek2", FootballMatchUpdater.readAndReturnUpcomingWeek2MatchesFile());
        //FootballMatchList upcomingWeek3FootballMatchList = new FootballMatchList("UpcomingWeek3", FootballMatchUpdater.readAndReturnUpcomingWeek3MatchesFile());

        //ArrayList<FootballMatchList> footballMatchLists = new ArrayList<>();

        //footballMatchLists.add(yesterdayFootballMatchList);
        //footballMatchLists.add(todayFootballMatchList);
        //footballMatchLists.add(tomorrowFootballMatchList);
        //footballMatchLists.add(upcomingWeek1FootballMatchList);
        //footballMatchLists.add(upcomingWeek2FootballMatchList);
        //footballMatchLists.add(upcomingWeek3FootballMatchList);

        //insertMultipleDocuments(footballMatchLists);

        // TODO : (CRUD) READ
        // Retrieve the entire collection and display (Will display all users)
        //retrieveCollectionAndDisplay();

        // Retrieve a list of football matches belonging to a specific time frame
        //retrieveCollectionTimeFrameAndDisplay("Tomorrow");
        //FootballMatchList footballMatchArrayList = retrieveCollectionTimeFrameAndReturn("Tomorrow");
        //System.out.println(footballMatchArrayList.toString());

        // TODO : (CRUD) UPDATE
        updateDocumentsWithinTimeframe("Yesterday", FootballMatchUpdater.readAndReturnYesterdayMatchesFile());
        updateDocumentsWithinTimeframe("Today",FootballMatchUpdater.readAndReturnTodayMatchesFile());
        updateDocumentsWithinTimeframe("Tomorrow",FootballMatchUpdater.readAndReturnTomorrowMatchesFile());
        updateDocumentsWithinTimeframe("UpcomingWeek1",FootballMatchUpdater.readAndReturnUpcomingWeek1MatchesFile());
        updateDocumentsWithinTimeframe("UpcomingWeek2",FootballMatchUpdater.readAndReturnUpcomingWeek2MatchesFile());
        updateDocumentsWithinTimeframe("UpcomingWeek3",FootballMatchUpdater.readAndReturnUpcomingWeek3MatchesFile());

        // TODO : (CRUD) DELETE
        // Deletes all football matches
        //deleteAllDocuments();
    }
}