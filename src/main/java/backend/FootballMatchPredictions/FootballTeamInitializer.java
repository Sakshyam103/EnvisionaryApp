package backend.FootballMatchPredictions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// FootballTeamInitializer class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Initializes the file path and folders needed to save the football teams data. Updates the team data
// JSON file, displays the football teams to the console, and returns an ArrayList of FootballTeam
// objects
//
public class FootballTeamInitializer {
    private static final String API_KEY = "a1656790c3c4408593f44ac696c80b47";		// API authorization key
    private static final String BASE_URL = "https://api.football-data.org/v4/";		// Base URL used for API Data Access
    private static String userHome = System.getProperty("user.home");
    private static String folderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "SportsPredictions" + File.separator + "FootballPredictions" + File.separator + "FootballTeamData";

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // initializeFilePath
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Initializes the file path and folders needed to save the football team file.
    //
    public static void initializeFilePath() {
        // Create the directory if it doesn't exist
        File directory = new File(folderPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Created directory: " + folderPath);
            } else {
                System.err.println("Failed to create directory: " + folderPath);
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // fetchData
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Fetches data from the football-data.org API using the given String URL parameter and returns the
    // JSON formatted football team data as a String.
    //
    public static String fetchData(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-Auth-Token", API_KEY)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new IOException("Failed to fetch data from the API. Response code: " + response.statusCode());
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateTeams
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Updates the file football_teams.json with current data from the football-data.org API
    // containing all the football (soccer) teams within the leagues that are offered.
    //
    public static void updateTeams() {
        try {
            // Fetch team data from football-data.org API
            String teams = fetchData(BASE_URL + "teams");

            // Initialize a new array list of FootballTeam
            ArrayList<FootballTeam> footballTeamList = FootballAPIv4TeamDataExtractor.extractTeamsToArrayList(teams);

            // Save the list to a JSON file in the specified directory
            String filePath = folderPath + File.separator + "football_teams.json";
            try (FileWriter writer = new FileWriter(filePath)) {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<FootballTeam>>() {}.getType();
                String json = gson.toJson(footballTeamList, listType);
                writer.write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // readFileAndPrintTeams
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Reads the data within the football_teams.json file and displays it to the console.
    //
    public static void readFileAndPrintTeams() {
        // Read the list of FootballTeam objects back from the JSON file
        String filePath = folderPath + File.separator + "football_teams.json";
        try {
            String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<FootballTeam>>() {}.getType();
            ArrayList<FootballTeam> loadedTeams = gson.fromJson(json, listType);

            if (loadedTeams != null) {
                for (FootballTeam team : loadedTeams) {
                    team.printTeamDetails();
                    System.out.println();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // readFileAndReturnTeams
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Reads the data within the football_teams.json file and returns an ArrayList of FootballTeam objects.
    //
	public static ArrayList<FootballTeam> readFileAndReturnTeams() {
	    // Read the list of FootballTeam objects back from the JSON file
	    String filePath = folderPath + File.separator + "football_teams.json";
	    try {
	        String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
	        Gson gson = new Gson();
	        Type listType = new TypeToken<ArrayList<FootballTeam>>() {}.getType();
	        ArrayList<FootballTeam> loadedTeams = gson.fromJson(json, listType);
	
	        return loadedTeams;
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	
	    return null; // Return null if there was an error or the file doesn't exist
	}
}