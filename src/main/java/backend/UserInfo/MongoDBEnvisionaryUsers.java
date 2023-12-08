package backend.UserInfo;

import backend.CelestialBodyPredictions.CelestialBodyPrediction;
import backend.CustomPredictions.CustomPrediction;
//import backend.EntertainmentPredictions.EntertainmentPrediction;
import backend.FootballMatchPredictions.FootballMatchPrediction;
import backend.WeatherPredictions.WeatherPrediction;
import backend.ResolvedPredictions.ResolvedPrediction;
import backend.Notifications.Notification;
import backend.UserStatistics.UserDescriptiveStatistics;
import backend.UserStatistics.UserInferentialStatistics;

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
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// FootballMatchPredictions.MongoDBFootballMatchData class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Connects to MongoDB for interaction with Envisionary User data.
//
public class MongoDBEnvisionaryUsers {
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Name of the database and collection to use.
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    private static final String DB_NAME = "Envisionary";
    private static String COLLECTION_NAME = "EnvisionaryUsers";

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
    // insertIndividualEnvisionaryUser
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void insertIndividualEnvisionaryUser(String userId, String userEmail) {
        // Create new EnvisionaryUser using userId
        EnvisionaryUser envisionaryUser = new EnvisionaryUser(userId, userEmail);

        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Try inserting EntertainmentEnvisionaryUser
        try {
            System.out.println("Inserting EnvisionaryUser: " + envisionaryUser);
            collection.insertOne(envisionaryUser);
            System.out.println("Inserted Envisionary User.\n");
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to insert EnvisionaryUser into MongoDB due to an error: " + me);
            // System.exit(1);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // insertIndividualEnvisionaryUser
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void insertIndividualEnvisionaryUser(EnvisionaryUser envisionaryUser) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Try inserting EntertainmentEnvisionaryUser
        try {
            System.out.println("Inserting EnvisionaryUser: " + envisionaryUser);
            collection.insertOne(envisionaryUser);
            System.out.println("Inserted Envisionary User.\n");
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to insert EnvisionaryUser into MongoDB due to an error: " + me);
            // System.exit(1);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // insertMultipleEnvisionaryUsers
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void insertMultipleEnvisionaryUsers(ArrayList<EnvisionaryUser> envisionaryUsers) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Try inserting multiple EnvisionaryUsers
        try {
            InsertManyResult result = collection.insertMany(envisionaryUsers);
            System.out.println("Inserted " + result.getInsertedIds().size() + " Envisionary Users.\n");
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to insert any EnvisionaryUsers into MongoDB due to an error: " + me);
            // System.exit(1);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveCollection
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static ArrayList<EnvisionaryUser> retrieveCollection() {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Initialize an array list of EntertainmentEnvisionaryUser
        ArrayList<EnvisionaryUser> envisionaryUsers = new ArrayList<>();

        // Try retreiving the collection of EnvisionaryUsers
        try (MongoCursor<EnvisionaryUser> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                envisionaryUsers.add(cursor.next());
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find any EnvisionaryUsers in MongoDB due to an error: " + me);
        }
        // Close the connection when done working with the client
        mongoClient.close();

        // Return the array list of Envisionary Users
        return envisionaryUsers;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveCollectionAndPrint
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void retrieveCollectionAndPrint() {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Try retreiving the collection of EnvisionaryUsers
        try (MongoCursor<EnvisionaryUser> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();
                System.out.printf("User: %s\n", currentEnvisionaryUser.getUserID());
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find any EnvisionaryUsers in MongoDB due to an error: " + me);
        }
        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveCollectionForUser
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void retrieveCollectionForUser(String userID) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("userID", userID);

        // Try retrieving the collection of EnvisionaryUsers for the user
        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();
                
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveAndDisplayUserEmail
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void retrieveAndDisplayUserEmail(String userID) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("userID", userID);

        // Try retrieving the collection of EnvisionaryUsers for the user
        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();

                // Print user email
                System.out.println("User Email: " + currentEnvisionaryUser.getEmail());

                // Close the connection when done working with the client
                mongoClient.close();
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveAndDisplayCustomPredictionsForUser
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void retrieveAndDisplayCustomPredictionsForUser(String userID) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("userID", userID);

        // Try retrieving the collection of EnvisionaryUsers for the user
        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();

                // Retrieve and print CustomPredictions
                ArrayList<CustomPrediction> customPredictions = currentEnvisionaryUser.getCustomPredictions();
                if (customPredictions != null && !customPredictions.isEmpty()) {
                    System.out.println("Custom Predictions:");
                    for (CustomPrediction customPrediction : customPredictions) {
                        customPrediction.printPredictionDetails();
                    }
                } else {
                    System.out.println("No Custom Predictions found for the user.");
                }
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

//    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
//    // retrieveAndDisplayEntertainmentPredictionsForUser
//    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
//    //
//    public static void retrieveAndDisplayEntertainmentPredictionsForUser(String userID) {
//        // Connect to MongoDB EnvisionaryUsers collection
//        MongoClient mongoClient = connectToMongoDB();
//        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
//        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);
//
//        // Set filter for the specified user
//        Bson userFilter = Filters.eq("userID", userID);
//
//        // Try retrieving the collection of EnvisionaryUsers for the user
//        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
//            while (cursor.hasNext()) {
//                EnvisionaryUser currentEnvisionaryUser = cursor.next();
//
//                // Retrieve and print EntertainmentPredictions
//                ArrayList<EntertainmentPrediction> entertainmentPredictions = currentEnvisionaryUser.getEntertainmentPredictions();
//                if (entertainmentPredictions != null && !entertainmentPredictions.isEmpty()) {
//                    System.out.println("Entertainment Predictions:");
//                    for (EntertainmentPrediction entertainmentPrediction : entertainmentPredictions) {
//                        System.out.println("   " + entertainmentPrediction.toString());
//                    }
//                } else {
//                    System.out.println("No Entertainment Predictions found for the user.");
//                }
//            }
//        } catch (MongoException me) {
//            System.err.println("ERROR - Unable to find EntertainmentEnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
//        }
//
//        // Close the connection when done working with the client
//        mongoClient.close();
//    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveAndDisplayFootballMatchPredictionsForUser
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void retrieveAndDisplayFootballMatchPredictionsForUser(String userID) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("userID", userID);

        // Try retrieving the collection of EnvisionaryUsers for the user
        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();

                // Retrieve and print FootballMatchPredictions
                ArrayList<FootballMatchPrediction> footballMatchPredictions = currentEnvisionaryUser.getFootballMatchPredictions();
                if (footballMatchPredictions != null && !footballMatchPredictions.isEmpty()) {
                    System.out.println("Football Match Predictions:");
                    for (FootballMatchPrediction footballMatchPrediction : footballMatchPredictions) {
                        System.out.println("   " + footballMatchPrediction.toString());
                    }
                } else {
                    System.out.println("No Football Match Predictions found for the user.");
                }
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveAndDisplayCelestialBodyPredictionsForUser
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void retrieveAndDisplayCelestialBodyPredictionsForUser(String userID) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("userID", userID);

        // Try retrieving the collection of EnvisionaryUsers for the user
        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();

                // Retrieve and print CelestialBodyPredictions
                ArrayList<CelestialBodyPrediction> celestialBodyPredictions = currentEnvisionaryUser.getCelestialBodyPredictions();
                if (celestialBodyPredictions != null && !celestialBodyPredictions.isEmpty()) {
                    System.out.println("Celestial Body Predictions:");
                    for (CelestialBodyPrediction celestialBodyPrediction : celestialBodyPredictions) {
                        celestialBodyPrediction.printPredictionDetails();
                    }
                } else {
                    System.out.println("No Celestial Body Predictions found for the user.");
                }
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
        }
        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveAndDisplayWeatherPredictionsForUser
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void retrieveAndDisplayWeatherPredictionsForUser(String userID) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("userID", userID);

        // Try retrieving the collection of EnvisionaryUsers for the user
        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();

                // Retrieve and print WeatherPredictions
                ArrayList<WeatherPrediction> weatherPredictions = currentEnvisionaryUser.getWeatherPredictions();
                if (weatherPredictions != null && !weatherPredictions.isEmpty()) {
                    System.out.println("Weather Predictions:");
                    for (WeatherPrediction weatherPrediction : weatherPredictions) {
                        weatherPrediction.printPredictionDetails();
                    }
                } else {
                    System.out.println("No Weather Predictions found for the user.");
                }
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveAndDisplayResolvedPredictionsForUser
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void retrieveAndDisplayResolvedPredictionsForUser(String userID) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("userID", userID);

        // Try retrieving the collection of EnvisionaryUsers for the user
        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();

                // Retrieve and print ResolvedPredictions
                ArrayList<ResolvedPrediction> resolvedPredictions = currentEnvisionaryUser.getResolvedPredictions();
                if (resolvedPredictions != null && !resolvedPredictions.isEmpty()) {
                    System.out.println("Resolved Predictions:");
                    for (ResolvedPrediction resolvedPrediction : resolvedPredictions) {
                        resolvedPrediction.printPredictionDetails();
                    }
                } else {
                    System.out.println("No Resolved Predictions found for the user.");
                }
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveAndDisplayNotificationsForUser
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void retrieveAndDisplayNotificationsForUser(String userID) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("userID", userID);

        // Try retrieving the collection of EnvisionaryUsers for the user
        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();

                // Retrieve and print Notifications
                ArrayList<Notification> notifications = currentEnvisionaryUser.getNotifications();
                if (notifications != null && !notifications.isEmpty()) {
                    System.out.println("Notifications:");
                    for (Notification notification : notifications) {
                        notification.printNotification();
                    }
                } else {
                    System.out.println("No Notifications found for the user.");
                }
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveAndDisplayUserDescriptiveStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void retrieveAndDisplayUserDescriptiveStatistics(String userID) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("userID", userID);

        // Try retrieving the collection of EnvisionaryUsers for the user
        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();

                // Retrieve and print User Descriptive Statistics
                UserDescriptiveStatistics descriptiveStatistics = currentEnvisionaryUser.getUserDescriptiveStatistics();
                if (descriptiveStatistics != null) {
                    System.out.println("   User Descriptive Statistics:");
                    System.out.println("   Prediction Count: " + descriptiveStatistics.getPredictionCount());
                    System.out.println("   Number of Correct Predictions: " + descriptiveStatistics.getCorrectPredictions());
                    System.out.println("   Number of Incorrect Predictions: " + descriptiveStatistics.getIncorrectPredictions());
                    System.out.println("   Percent of Correct Predictions: " + descriptiveStatistics.getPercentCorrect() + "%");
                    System.out.println("   Percent of Incorrect Predictions: " + descriptiveStatistics.getPercentIncorrect() + "%");
                } else {
                    System.out.println("No User Descriptive Statistics found for the user.");
                }
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveAndDisplayUserInferentialStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void retrieveAndDisplayUserInferentialStatistics(String userID) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("userID", userID);

        // Try retrieving the collection of EnvisionaryUsers for the user
        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();

                // Retrieve and print User Inferential Statistics
                UserInferentialStatistics inferentialStatistics = currentEnvisionaryUser.getUserInferentialStatistics();
                if (inferentialStatistics != null) {
                    System.out.println("User Inferential Statistics:");
                    currentEnvisionaryUser.getUserInferentialStatistics().printUserInferentialStatistics();
                } else {
                    System.out.println("No User Inferential Statistics found for the user.");
                }
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveUserEmail
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static String retrieveUserEmail(String userID) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("userID", userID);

        // Try retrieving the collection of EnvisionaryUsers for the user
        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {

            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();

                // Retrieve user email
                String userEmail = currentEnvisionaryUser.getEmail();

                // Close the connection when done working with the client
                mongoClient.close();

                // Return user email
                return userEmail;
            }


        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();

        // Return null if no user email found
        return null;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveUserCustomPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static ArrayList<CustomPrediction> retrieveUserCustomPredictions(String userID) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("userID", userID);

        // Initialize the list to store CustomPredictions
        ArrayList<CustomPrediction> customPredictionsList = new ArrayList<>();

        // Try retrieving the collection of EnvisionaryUsers for the user
        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();

                // Retrieve and add CustomPredictions to the list
                ArrayList<CustomPrediction> customPredictions = currentEnvisionaryUser.getCustomPredictions();
                if (customPredictions != null && !customPredictions.isEmpty()) {
                    customPredictionsList.addAll(customPredictions);
                } else {
                    mongoClient.close();
                    return null;
                }
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();

        return customPredictionsList;
    }

//    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
//    // retrieveUserEntertainmentPredictions
//    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
//    //
//    public static ArrayList<EntertainmentPrediction> retrieveUserEntertainmentPredictions(String userID) {
//        // Connect to MongoDB EnvisionaryUsers collection
//        MongoClient mongoClient = connectToMongoDB();
//        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
//        MongoCollection<EntertainmentEnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EntertainmentEnvisionaryUser.class);
//
//        // Set filter for the specified user
//        Bson userFilter = Filters.eq("userID", userID);
//
//        // Initialize the list to store EntertainmentPredictions
//        ArrayList<EntertainmentPrediction> entertainmentPredictionsList = new ArrayList<>();
//
//        // Try retrieving the collection of EnvisionaryUsers for the user
//        try (MongoCursor<EntertainmentEnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
//            while (cursor.hasNext()) {
//                EntertainmentEnvisionaryUser currentEnvisionaryUser = cursor.next();
//
//                // Retrieve and add EntertainmentPredictions to the list
//                ArrayList<EntertainmentPrediction> entertainmentPredictions = currentEnvisionaryUser.getEntertainmentPredictions();
//                if (entertainmentPredictions != null && !entertainmentPredictions.isEmpty()) {
//                    entertainmentPredictionsList.addAll(entertainmentPredictions);
//                } else {
//                    mongoClient.close();
//                    return null;
//                }
//            }
//        } catch (MongoException me) {
//            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
//        }
//
//        // Close the connection when done working with the client
//        mongoClient.close();
//
//        return entertainmentPredictionsList;
//    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveUserFootballMatchPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static ArrayList<FootballMatchPrediction> retrieveUserFootballMatchPredictions(String userID) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("userID", userID);

        // Initialize the list to store FootballMatchPredictions
        ArrayList<FootballMatchPrediction> footballMatchPredictionsList = new ArrayList<>();

        // Try retrieving the collection of EnvisionaryUsers for the user
        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();

                // Retrieve and add FootballMatchPredictions to the list
                ArrayList<FootballMatchPrediction> footballMatchPredictions = currentEnvisionaryUser.getFootballMatchPredictions();
                if (footballMatchPredictions != null && !footballMatchPredictions.isEmpty()) {
                    footballMatchPredictionsList.addAll(footballMatchPredictions);
                } else {
                    mongoClient.close();
                    return null;
                }
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();

        return footballMatchPredictionsList;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveUserCelestialBodyPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static ArrayList<CelestialBodyPrediction> retrieveUserCelestialBodyPredictions(String userID) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("userID", userID);

        // Initialize the list to store CelestialBodyPredictions
        ArrayList<CelestialBodyPrediction> celestialBodyPredictionsList = new ArrayList<>();

        // Try retrieving the collection of EnvisionaryUsers for the user
        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();

                // Retrieve and add CelestialBodyPredictions to the list
                ArrayList<CelestialBodyPrediction> celestialBodyPredictions = currentEnvisionaryUser.getCelestialBodyPredictions();
                if (celestialBodyPredictions != null && !celestialBodyPredictions.isEmpty()) {
                    celestialBodyPredictionsList.addAll(celestialBodyPredictions);
                } else {
                    mongoClient.close();
                    return null;
                }
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();

        return celestialBodyPredictionsList;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveUserWeatherPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static ArrayList<WeatherPrediction> retrieveUserWeatherPredictions(String userID) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("userID", userID);

        // Initialize the list to store WeatherPredictions
        ArrayList<WeatherPrediction> weatherPredictionsList = new ArrayList<>();

        // Try retrieving the collection of EnvisionaryUsers for the user
        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();

                // Retrieve and add WeatherPredictions to the list
                ArrayList<WeatherPrediction> weatherPredictions = currentEnvisionaryUser.getWeatherPredictions();
                if (weatherPredictions != null && !weatherPredictions.isEmpty()) {
                    weatherPredictionsList.addAll(weatherPredictions);
                } else {
                    mongoClient.close();
                    return null;
                }
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();

        return weatherPredictionsList;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveUserResolvedPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static ArrayList<ResolvedPrediction> retrieveUserResolvedPredictions(String userID) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("userID", userID);

        // Initialize the list to store ResolvedPredictions
        ArrayList<ResolvedPrediction> resolvedPredictionsList = new ArrayList<>();

        // Try retrieving the collection of EnvisionaryUsers for the user
        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();

                // Retrieve and add ResolvedPredictions to the list
                ArrayList<ResolvedPrediction> resolvedPredictions = currentEnvisionaryUser.getResolvedPredictions();
                if (resolvedPredictions != null && !resolvedPredictions.isEmpty()) {
                    resolvedPredictionsList.addAll(resolvedPredictions);
                } else {
                    mongoClient.close();
                    return null;
                }
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();

        return resolvedPredictionsList;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveUserResolvedCustomPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static ArrayList<ResolvedPrediction> retrieveUserResolvedCustomPredictions(String userIdentifier) {
        // Initialize new array list of ResolvedPrediction to load file data into
        ArrayList<ResolvedPrediction> loadedResolvedPredictions = retrieveUserResolvedPredictions(userIdentifier);
        if (loadedResolvedPredictions == null) {
            loadedResolvedPredictions = new ArrayList<>();
        }
        // Initialize new array list of ResolvedPrediction to store Custom predictions
        ArrayList<ResolvedPrediction> userResolvedCustomPredictions = new ArrayList<>();
        
        // For each resolved prediction within the array list of loaded resolved predictions
        for (ResolvedPrediction resolvedPrediction : loadedResolvedPredictions) {
            if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Custom")) {
                userResolvedCustomPredictions.add(resolvedPrediction);
            }
        }
        return userResolvedCustomPredictions;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveUserResolvedCelestialBodyPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static ArrayList<ResolvedPrediction> retrieveUserResolvedCelestialBodyPredictions(String userIdentifier) {
        // Initialize new array list of ResolvedPrediction to load file data into
        ArrayList<ResolvedPrediction> loadedResolvedPredictions = retrieveUserResolvedPredictions(userIdentifier);
        if (loadedResolvedPredictions == null) {
            loadedResolvedPredictions = new ArrayList<>();
        }
        // Initialize new array list of ResolvedPrediction to store CelestialBody predictions
        ArrayList<ResolvedPrediction> userResolvedCelestialBodyPredictions = new ArrayList<>();

        // For each resolved prediction within the array list of loaded resolved predictions
        for (ResolvedPrediction resolvedPrediction : loadedResolvedPredictions) {
            if (resolvedPrediction.getPredictionType().equalsIgnoreCase("CelestialBody")) {
                userResolvedCelestialBodyPredictions.add(resolvedPrediction);
            }
        }
        return userResolvedCelestialBodyPredictions;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveUserResolvedFootballMatchPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static ArrayList<ResolvedPrediction> retrieveUserResolvedFootballMatchPredictions(String userIdentifier) {
        // Initialize new array list of ResolvedPrediction to load file data into
        ArrayList<ResolvedPrediction> loadedResolvedPredictions = retrieveUserResolvedPredictions(userIdentifier);
        if (loadedResolvedPredictions == null) {
            loadedResolvedPredictions = new ArrayList<>();
        }
        // Initialize new array list of ResolvedPrediction to store FootballMatch predictions
        ArrayList<ResolvedPrediction> userResolvedFootballMatchPredictions = new ArrayList<>();

        // For each resolved prediction within the array list of loaded resolved predictions
        for (ResolvedPrediction resolvedPrediction : loadedResolvedPredictions) {
            if (resolvedPrediction.getPredictionType().equalsIgnoreCase("FootballMatch")) {
                userResolvedFootballMatchPredictions.add(resolvedPrediction);
            }
        }
        return userResolvedFootballMatchPredictions;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveUserResolvedWeatherPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static ArrayList<ResolvedPrediction> retrieveUserResolvedWeatherPredictions(String userIdentifier) {
        // Initialize new array list of ResolvedPrediction to load file data into
        ArrayList<ResolvedPrediction> loadedResolvedPredictions = retrieveUserResolvedPredictions(userIdentifier);
        if (loadedResolvedPredictions == null) {
            loadedResolvedPredictions = new ArrayList<>();
        }
        // Initialize new array list of ResolvedPrediction to store Weather predictions
        ArrayList<ResolvedPrediction> userResolvedWeatherPredictions = new ArrayList<>();

        // For each resolved prediction within the array list of loaded resolved predictions
        for (ResolvedPrediction resolvedPrediction : loadedResolvedPredictions) {
            if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Weather")) {
                userResolvedWeatherPredictions.add(resolvedPrediction);
            }
        }
        return userResolvedWeatherPredictions;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveUserResolvedEntertainmentPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static ArrayList<ResolvedPrediction> retrieveUserResolvedEntertainmentPredictions(String userIdentifier) {
        // Initialize new array list of ResolvedPrediction to load file data into
        ArrayList<ResolvedPrediction> loadedResolvedPredictions = retrieveUserResolvedPredictions(userIdentifier);
        if (loadedResolvedPredictions == null) {
            loadedResolvedPredictions = new ArrayList<>();
        }
        // Initialize new array list of ResolvedPrediction to store Entertainment predictions
        ArrayList<ResolvedPrediction> userResolvedEntertainmentPredictions = new ArrayList<>();

        // For each resolved prediction within the array list of loaded resolved predictions
        for (ResolvedPrediction resolvedPrediction : loadedResolvedPredictions) {
            if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Entertainment")) {
                userResolvedEntertainmentPredictions.add(resolvedPrediction);
            }
        }
        return userResolvedEntertainmentPredictions;
    }



    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveUserNotifications
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static ArrayList<Notification> retrieveUserNotifications(String userID) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("userID", userID);

        // Initialize the list to store Notifications
        ArrayList<Notification> notificationsList = new ArrayList<>();

        // Try retrieving the collection of EnvisionaryUsers for the user
        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();

                // Retrieve and add Notifications to the list
                ArrayList<Notification> notifications = currentEnvisionaryUser.getNotifications();
                if (notifications != null && !notifications.isEmpty()) {
                    notificationsList.addAll(notifications);
                } else {
                    mongoClient.close();
                    return null;
                }
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();

        return notificationsList;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveUserDescriptiveStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static UserDescriptiveStatistics retrieveUserDescriptiveStatistics(String userID) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("userID", userID);

        // Try retrieving the collection of EnvisionaryUsers for the user
        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();

                // Retrieve User Descriptive Statistics
                UserDescriptiveStatistics descriptiveStatistics = currentEnvisionaryUser.getUserDescriptiveStatistics();

                // Close the connection when done working with the client
                mongoClient.close();

                // Return User Descriptive Statistics
                return descriptiveStatistics;
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();

        // Return null if no User Descriptive Statistics found
        return null;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // retrieveUserInferentialStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static UserInferentialStatistics retrieveUserInferentialStatistics(String userID) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for the specified user
        Bson userFilter = Filters.eq("userID", userID);

        // Try retrieving the collection of EnvisionaryUsers for the user
        try (MongoCursor<EnvisionaryUser> cursor = collection.find(userFilter).iterator()) {
            while (cursor.hasNext()) {
                EnvisionaryUser currentEnvisionaryUser = cursor.next();

                // Retrieve User Inferential Statistics
                UserInferentialStatistics inferentialStatistics = currentEnvisionaryUser.getUserInferentialStatistics();

                // Close the connection when done working with the client
                mongoClient.close();

                // Return User Inferential Statistics
                return inferentialStatistics;
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find EnvisionaryUser: " + userID + " in MongoDB due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();

        // Return null if no User Inferential Statistics found
        return null;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // findEnvisionaryUser
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // TODO: Implement logic to search for friends to add / send notifications to?
    public static void findEnvisionaryUser(String fieldName, String value) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter
        Bson findEnvisionaryUser = Filters.eq(fieldName, value);

        // Try to find Envisionary User
        try {
            EnvisionaryUser firstEnvisionaryUser = collection.find(findEnvisionaryUser).first();
            if (firstEnvisionaryUser == null) {
                System.out.println("ERROR - Couldn't find any EnvisionaryUsers containing " + fieldName + " : " + value + " in MongoDB - Envisionary - EnvisionaryUsers.");
                // System.exit(1);
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to find a EnvisionaryUser to update in MongoDB due to an error: " + me);
            // System.exit(1);
        }
        System.out.println("EnvisionaryUser found. IMPLEMENT LOGIC");

        // TODO : IMPLEMENT LOGIC HERE.
        //  Finding friends to add to notification lists?
        //  Can we utilize this within a search bar?

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateEnvisionaryUser
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void updateEnvisionaryUser(String fieldName1, String value1, String fieldName2, String value2, String updateVariable, String updateValue) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter for updater
        Bson deleteFilter = Filters.and(
                Filters.eq(fieldName1, value1),
                Filters.eq(fieldName2, value2)
        );
        Bson findEnvisionaryUser = Filters.and(Filters.eq(fieldName1, value1),Filters.eq(fieldName2, value2));
        Bson updateFilter = Updates.set(updateVariable, updateValue);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);

        // Try to update Envisionary User
        try {
            EnvisionaryUser updatedEnvisionaryUser = collection.findOneAndUpdate(findEnvisionaryUser, updateFilter, options);
            if (updatedEnvisionaryUser == null) {
                System.out.println("ERROR - Couldn't find any EnvisionaryUsers containing " + fieldName1 + " : " + value1 + " / " + fieldName2 + " : " + value2 + " in MongoDB - Envisionary - EnvisionaryUsers.");
            } else {
                System.out.println("\nUpdated the EnvisionaryUser to: " + updatedEnvisionaryUser);
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to update any EnvisionaryUsers due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateUserCustomPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void updateUserCustomPredictions(String userID, ArrayList<CustomPrediction> updatedCustomPredictionsArrayList) {
        // Connect to MongoDB CustomPredictions collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<CustomPrediction> collection = database.getCollection(COLLECTION_NAME, CustomPrediction.class);

        // Set filter for updater
        Bson findCustomPredictions = Filters.eq("userID", userID);
        Bson updateFilter = Updates.set("customPredictions", updatedCustomPredictionsArrayList);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);


        // Try to update Envisionary User's Custom Predictions
        try {
            CustomPrediction updatedCustomPredictions = collection.findOneAndUpdate(findCustomPredictions, updateFilter, options);
            if (updatedCustomPredictions == null) {
                System.out.println("ERROR - Couldn't find any CustomPredictions for user: " + userID + " within MongoDB - EnvisionaryUsers.");
            } else {
                System.out.println("\nUpdated the CustomPrediction to: " + updatedCustomPredictions);
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to update any CustomPredictions due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateUserEntertainmentPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
//    public static void updateUserEntertainmentPredictions(String userID, ArrayList<EntertainmentPrediction> updatedEntertainmentPredictionsArrayList) {
//        // Connect to MongoDB EntertainmentPredictions collection
//        MongoClient mongoClient = connectToMongoDB();
//        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
//        MongoCollection<EntertainmentPrediction> collection = database.getCollection(COLLECTION_NAME, EntertainmentPrediction.class);
//
//        // Set filter for updater
//        Bson findEntertainmentPredictions = Filters.eq("userID", userID);
//        Bson updateFilter = Updates.set("entertainmentPredictions", updatedEntertainmentPredictionsArrayList);
//        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
//
//
//        // Try to update Envisionary User's Entertainment Predictions
//        try {
//            EntertainmentPrediction updatedEntertainmentPredictions = collection.findOneAndUpdate(findEntertainmentPredictions, updateFilter, options);
//            if (updatedEntertainmentPredictions == null) {
//                System.out.println("ERROR - Couldn't find any EntertainmentPredictions for user: " + userID + " within MongoDB - EnvisionaryUsers.");
//            } else {
//                System.out.println("\nUpdated the EntertainmentPrediction to: " + updatedEntertainmentPredictions);
//            }
//        } catch (MongoException me) {
//            System.err.println("ERROR - Unable to update any EntertainmentPredictions due to an error: " + me);
//        }
//
//        // Close the connection when done working with the client
//        mongoClient.close();
//    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateUserFootballMatchPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void updateUserFootballMatchPredictions(String userID, ArrayList<FootballMatchPrediction> updatedFootballMatchPredictionsArrayList) {
        // Connect to MongoDB FootballMatchPredictions collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<FootballMatchPrediction> collection = database.getCollection(COLLECTION_NAME, FootballMatchPrediction.class);

        // Set filter for updater
        Bson findFootballMatchPredictions = Filters.eq("userID", userID);
        Bson updateFilter = Updates.set("footballMatchPredictions", updatedFootballMatchPredictionsArrayList);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);


        // Try to update Envisionary User's Football Match Predictions
        try {
            FootballMatchPrediction updatedFootballMatchPredictions = collection.findOneAndUpdate(findFootballMatchPredictions, updateFilter, options);
            if (updatedFootballMatchPredictions == null) {
                System.out.println("ERROR - Couldn't find any FootballMatchPredictions for user: " + userID + " within MongoDB - EnvisionaryUsers.");
            } else {
                System.out.println("\nUpdated the FootballMatchPrediction to: " + updatedFootballMatchPredictions);
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to update any FootballMatchPredictions due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateUserCelestialBodyPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void updateUserCelestialBodyPredictions(String userID, ArrayList<CelestialBodyPrediction> updatedCelestialBodyPredictionsArrayList) {
        // Connect to MongoDB CelestialBodyPredictions collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<CelestialBodyPrediction> collection = database.getCollection(COLLECTION_NAME, CelestialBodyPrediction.class);

        // Set filter for updater
        Bson findCelestialBodyPredictions = Filters.eq("userID", userID);
        Bson updateFilter = Updates.set("celestialBodyPredictions", updatedCelestialBodyPredictionsArrayList);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);


        // Try to update Envisionary User's Celestial Body Predictions
        try {
            CelestialBodyPrediction updatedCelestialBodyPredictions = collection.findOneAndUpdate(findCelestialBodyPredictions, updateFilter, options);
            if (updatedCelestialBodyPredictions == null) {
                System.out.println("ERROR - Couldn't find any CelestialBodyPredictions for user: " + userID + " within MongoDB - EnvisionaryUsers.");
            } else {
                System.out.println("\nUpdated the CelestialBodyPrediction to: " + updatedCelestialBodyPredictions);
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to update any CelestialBodyPredictions due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateUserWeatherPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void updateUserWeatherPredictions(String userID, ArrayList<WeatherPrediction> updatedWeatherPredictionsArrayList) {
        // Connect to MongoDB WeatherPredictions collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<WeatherPrediction> collection = database.getCollection(COLLECTION_NAME, WeatherPrediction.class);

        // Set filter for updater
        Bson findWeatherPredictions = Filters.eq("userID", userID);
        Bson updateFilter = Updates.set("weatherPredictions", updatedWeatherPredictionsArrayList);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);


        // Try to update Envisionary User's Weather Predictions
        try {
            WeatherPrediction updatedWeatherPredictions = collection.findOneAndUpdate(findWeatherPredictions, updateFilter, options);
            if (updatedWeatherPredictions == null) {
                System.out.println("ERROR - Couldn't find any WeatherPredictions for user: " + userID + " within MongoDB - EnvisionaryUsers.");
            } else {
                System.out.println("\nUpdated the WeatherPrediction for user: " + userID);
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to update any WeatherPredictions due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateUserResolvedPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void updateUserResolvedPredictions(String userID, ArrayList<ResolvedPrediction> updatedResolvedPredictionsArrayList) {
        // Connect to MongoDB ResolvedPredictions collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<ResolvedPrediction> collection = database.getCollection(COLLECTION_NAME, ResolvedPrediction.class);

        // Set filter for updater
        Bson findResolvedPredictions = Filters.eq("userID", userID);
        Bson updateFilter = Updates.set("resolvedPredictions", updatedResolvedPredictionsArrayList);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);


        // Try to update Envisionary User's Resolved Predictions
        try {
            ResolvedPrediction updatedResolvedPredictions = collection.findOneAndUpdate(findResolvedPredictions, updateFilter, options);
            if (updatedResolvedPredictions == null) {
                System.out.println("ERROR - Couldn't find any ResolvedPredictions for user: " + userID + " within MongoDB - EnvisionaryUsers.");
            } else {
                System.out.println("\nUpdated the ResolvedPredictions to: " + updatedResolvedPredictions);
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to update any ResolvedPredictions due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateUserNotifications
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void updateUserNotifications(String userID, ArrayList<Notification> updatedNotificationsArrayList) {
        // Connect to MongoDB Notifications collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<Notification> collection = database.getCollection(COLLECTION_NAME, Notification.class);

        // Set filter for updater
        Bson findNotifications = Filters.eq("userID", userID);
        Bson updateFilter = Updates.set("notifications", updatedNotificationsArrayList);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);


        // Try to update Envisionary User's Notifications
        try {
            Notification updatedNotifications = collection.findOneAndUpdate(findNotifications, updateFilter, options);
            if (updatedNotifications == null) {
                System.out.println("ERROR - Couldn't find any Notifications for user: " + userID + " within MongoDB - EnvisionaryUsers.");
            } else {
                System.out.println("\nUpdated the Notification to: " + updatedNotifications);
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to update Notifications due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateUserDescriptiveStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void updateUserDescriptiveStatistics(String userID, UserDescriptiveStatistics updatedUserDescriptiveStatistics) {
        // Connect to MongoDB Notifications collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<UserDescriptiveStatistics> collection = database.getCollection(COLLECTION_NAME, UserDescriptiveStatistics.class);

        // Set filter for updater
        Bson findNotifications = Filters.eq("userID", userID);
        Bson updateFilter = Updates.set("userDescriptiveStatistics", updatedUserDescriptiveStatistics);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);


        // Try to update Envisionary User's User Descriptive Statistics
        try {
            UserDescriptiveStatistics userDescriptiveStatistics = collection.findOneAndUpdate(findNotifications, updateFilter, options);
            if (updatedUserDescriptiveStatistics == null) {
                System.out.println("ERROR - Couldn't find any UserStatistics.UserDescriptiveStatistics for user: " + userID + " within MongoDB - EnvisionaryUsers.");
            } else {
                System.out.println("\nUpdated the UserDescriptiveStatistics to: " + updatedUserDescriptiveStatistics);
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to update UserStatistics.UserDescriptiveStatistics due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateUserInferentialStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void updateUserInferentialStatistics(String userID, UserInferentialStatistics updatedUserInferentialStatistics) {
        // Connect to MongoDB Notifications collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<UserInferentialStatistics> collection = database.getCollection(COLLECTION_NAME, UserInferentialStatistics.class);

        // Set filter for updater
        Bson findNotifications = Filters.eq("userID", userID);
        Bson updateFilter = Updates.set("userInferentialStatistics", updatedUserInferentialStatistics);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);


        // Try to update Envisionary User's User Inferential Statistics
        try {
            UserInferentialStatistics userInferentialStatistics = collection.findOneAndUpdate(findNotifications, updateFilter, options);
            if (updatedUserInferentialStatistics == null) {
                System.out.println("ERROR - Couldn't find any UserStatistics.UserInferentialStatistics for user: " + userID + " within MongoDB - EnvisionaryUsers.");
            } else {
                System.out.println("\nUpdated the UserInferentialStatistics to: " + updatedUserInferentialStatistics);
            }
        } catch (MongoException me) {
            System.err.println("ERROR - Unable to update UserStatistics.UserInferentialStatistics due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // deleteEnvisionaryUser
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void deleteEnvisionaryUser(String fieldName, String value) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter
        Bson deleteFilter = Filters.in(fieldName, value);

        // Try to remove the Envisionary User
        try {
            DeleteResult deleteResult = collection.deleteOne(deleteFilter);
            System.out.printf("\nDeleted %d Envisionary Users.\n", deleteResult.getDeletedCount());
        } catch (MongoException me) {
            System.err.println("Unable to delete any EnvisionaryUsers due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // deleteEnvisionaryUser
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void deleteEnvisionaryUser(String fieldName1, String value1, String fieldName2, String value2) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter
        Bson deleteFilter = Filters.and(
                Filters.eq(fieldName1, value1),
                Filters.eq(fieldName2, value2)
        );

        // Try to remove the Envisionary User
        try {
            DeleteResult deleteResult = collection.deleteOne(deleteFilter);
            System.out.printf("\nDeleted %d Envisionary Users.\n", deleteResult.getDeletedCount());
        } catch (MongoException me) {
            System.err.println("Unable to delete any EnvisionaryUsers due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // deleteEnvisionaryUsers
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void deleteEnvisionaryUsers(String fieldName, String value) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter
        Bson deleteFilter = Filters.in(fieldName, value);

        // Try to remove the Envisionary Users
        try {
            DeleteResult deleteResult = collection.deleteMany(deleteFilter);
            System.out.printf("\nDeleted %d Envisionary Users.\n", deleteResult.getDeletedCount());
        } catch (MongoException me) {
            System.err.println("Unable to delete any EnvisionaryUsers due to an error: " + me);
        }

        // Close the connection when done working with the client
        mongoClient.close();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // userIdExists
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static boolean userIdExists(String userId) {
        // Connect to MongoDB EnvisionaryUsers collection
        MongoClient mongoClient = connectToMongoDB();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<EnvisionaryUser> collection = database.getCollection(COLLECTION_NAME, EnvisionaryUser.class);

        // Set filter
        Bson findUserFilter = Filters.eq("userId", userId);

        // Try to find Envisionary User
        try {
            EnvisionaryUser user = collection.find(findUserFilter).first();
            return user.getUserID() != null;
        } catch (MongoException me) {
            System.err.println("Unable to check if EnvisionaryUser exists due to an error: " + me);
            return false;
        } finally {
            // Close the connection when done working with the client
            mongoClient.close();
        }
    }

    public static void main(String[] args) {
        // TODO : (CRUD) CREATE

        // TODO : (CRUD) READ
        // Retreive the entire collection of Envisionary Users and display
        retrieveCollectionAndPrint();

        // TODO : (CRUD) UPDATE

        // TODO : (CRUD) DELETE

    }
}