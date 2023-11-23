package backend.CelestialBodyPredictions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CelestialBodyUpdater {
    private static String userHome = System.getProperty("user.home");
    private static String folderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "SciencePredictions" + File.separator + "CelestialBodyData";

    public static void initializeFilePath() {
        // Create the directory if it doesn't exist
        File directory = new File(folderPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Created directory: " + folderPath);
            } else {
                System.err.println("ERROR - Failed to create directory: " + folderPath);
            }
        }
    }

    public static boolean updateCelestialBodiesFile() {
        // Add all football matches from match data extractor array list
        ArrayList<CelestialBody> celestialBodyArrayList = new ArrayList<>(CelestialBodiesAPI.getSortedData(CelestialBodiesAPI.getSpaceData()));

        // Initialize the previous celestial bodies file
        String notificationsFilePath = folderPath + File.separator + "celestial_bodies.json";
        File previousFootballMatchesFile = new File(notificationsFilePath);

        // Load the previous football matches file if it exists
        if (previousFootballMatchesFile.exists()) {
            // Load the previous football matches file
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(notificationsFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<CelestialBody>>() {}.getType();
                ArrayList<CelestialBody> previousCelestialBodyArrayList = gson.fromJson(json, listType);

                // If celestial body lists are not equal update the celestial body list
                if (!previousCelestialBodyArrayList.containsAll(celestialBodyArrayList)) {
                    System.out.println("Update to celestial bodies.");

                    // Save the list to a file in the specified directory
                    String filePath = folderPath + File.separator + "celestial_bodies.json";
                    try (FileWriter writer = new FileWriter(filePath)) {
                        Gson gson2 = new Gson();
                        Type listType2 = new TypeToken<ArrayList<CelestialBody>>() {}.getType();
                        String json2 = gson2.toJson(celestialBodyArrayList, listType2);
                        writer.write(json2);
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("No updates made to celestial bodies.");
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Initial update to celestial bodies.");
            // Save the list to a file in the specified directory
            String filePath = folderPath + File.separator + "celestial_bodies.json";
            try (FileWriter writer = new FileWriter(filePath)) {
                Gson gson2 = new Gson();
                Type listType2 = new TypeToken<ArrayList<CelestialBody>>() {}.getType();
                String json2 = gson2.toJson(celestialBodyArrayList, listType2);
                writer.write(json2);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // readAndDisplayCelestialBodiesFile
    public static void readAndDisplayCelestialBodiesFile() {
        // Initialize filePath and ArrayList of FootballMatchPredictions.FootballMatch
        String filePath = folderPath + File.separator + "celestial_bodies.json";
        ArrayList<CelestialBody> loadedCelestialBodies = new ArrayList<>();
        // Read the list of FootballMatchPredictions.FootballMatch objects back from the file
        try {
            String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<CelestialBody>>() {}.getType();
            loadedCelestialBodies = gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If loadedMatches contains the FootballMatchPredictions.FootballMatch objects
        if (loadedCelestialBodies != null) {
            for (CelestialBody body : loadedCelestialBodies) {
                System.out.println(body.toString()); // Print each match's details
                System.out.println();
            }
        }
        else {
            System.out.println("ERROR - Celestial body data not found.\n");
        }
    }

    public static ArrayList<CelestialBody> readAndReturnCelestialBodiesFile() {
        // Initialize filePath and ArrayList of FootballMatchPredictions.FootballMatch
        String filePath = folderPath + File.separator + "celestial_bodies.json";
        ArrayList<CelestialBody> loadedCelestialBodies = new ArrayList<>();
        // Read the list of FootballMatchPredictions.FootballMatch objects back from the file
        try {
            String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<CelestialBody>>() {}.getType();
            loadedCelestialBodies = gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If loadedMatches contains the FootballMatchPredictions.FootballMatch objects
        if (loadedCelestialBodies != null) {
            return loadedCelestialBodies;
        }
        else {
            System.out.println("ERROR - Celestial body data not found.\n");
            return null;
        }
    }

    public static boolean updateCelestialBodiesMongoDB() {
        // Add all football matches from match data extractor array list
        ArrayList<CelestialBody> celestialBodyArrayList = new ArrayList<>(CelestialBodiesAPI.getSortedData(CelestialBodiesAPI.getSpaceData()));

        ArrayList<CelestialBody> previousCelestialBodiesList = MongoDBCelestialBodyData.retrieveCollection();

        // If previous celestial bodies list is not empty
        if (!previousCelestialBodiesList.isEmpty()) {
            // If celestial body lists are not equal update the celestial body list
            if (!previousCelestialBodiesList.containsAll(celestialBodyArrayList)) {
                System.out.println("Update to celestial bodies.");
                MongoDBCelestialBodyData.updateDocuments(celestialBodyArrayList);
                return true;
            } else {
                System.out.println("No updates made to celestial bodies.");
                return false;
            }
        } else {
            System.out.println("Initial update to celestial bodies.");
            MongoDBCelestialBodyData.updateDocuments(celestialBodyArrayList);
            return true;
        }
    }

    public static void main(String[] args) {
        initializeFilePath();
        updateCelestialBodiesFile();
        readAndDisplayCelestialBodiesFile();
    }
}