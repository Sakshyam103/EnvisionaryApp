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
import java.time.LocalDate;
import java.util.ArrayList;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// FootballMatchUpdater class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Initializes the file path and folders needed to save the football match data. Gets requests from
// the football-data.org API to gather football match data for yesterday's, today's's, tomorrow's,
// the upcoming week's, the second upcoming week's, and the third upcoming week's football match
// data and saves them as JSON files to the given folder/file path.
//
public class FootballMatchUpdater {
    // FootballMatchUpdater Class Constants
    private static final String API_KEY = System.getenv("FOOTBALL-DATA.ORG_API_KEY");		// API authorization key
    private static final String BASE_URL = "https://api.football-data.org/v4/";		            // Base URL used for API Data Access

    // FootballMatchUpdater Class Variables
    private static String userHome = System.getProperty("user.home");
    private static String folderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "SportsPredictions" + File.separator + "FootballPredictions" + File.separator + "FootballMatchData";

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // initializeFilePath
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Initializes the file path and folders needed to save the football match files.
    //
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

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // fetchData
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Fetches data from the football-data.org API using the given String URL parameter and returns the
    // JSON formatted football match data as a String.
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
    // updateYesterdayMatchesLocally
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Updates the file football_matches_yesterday.json with current data from the football-data.org API
    // containing all the matches for the prior day (Yesterday).
    public static void updateYesterdayMatchesLocally() {
        try {
            String yesterdayMatches = fetchData(BASE_URL + "matches?date=YESTERDAY");

            // Initialize a new array list of FootballTeam
            ArrayList<FootballMatch> footballMatchList = new ArrayList<>();

            // Add all football teams from team data extractor array list
            footballMatchList.addAll(FootballAPIv4MatchDataExtractor.extractMatchesToArrayList(yesterdayMatches));

            // Save the list to a file in the specified directory
            String filePath = folderPath + File.separator + "football_matches_yesterday.json";
            try (FileWriter writer = new FileWriter(filePath)) {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
                String json = gson.toJson(footballMatchList, listType);
                writer.write(json);
                //System.out.println("Football matches have been converted to JSON and saved to the file: " + filePath + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateTodayMatchesLocally
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Updates the file football_matches_today.json with current data from the football-data.org API
    // containing all the matches for the current day (Today). Returns true if update occurred.
    //
    public static boolean updateTodayMatchesLocally() {
        try {
            String todayMatches = fetchData(BASE_URL + "matches");

            // Initialize a new array list of FootballTeam
            ArrayList<FootballMatch> footballMatchList = new ArrayList<>();

            // Add all football matches from match data extractor array list
            footballMatchList.addAll(FootballAPIv4MatchDataExtractor.extractMatchesToArrayList(todayMatches));

            // Initialize the previous football matches file
            String notificationsFilePath = folderPath + File.separator + "football_matches_today.json";
            File previousFootballMatchesFile = new File(notificationsFilePath);

            // Load the previous football matches file if it exists
            if (previousFootballMatchesFile.exists()) {
                // Load the previous football matches file
                try {
                    String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(notificationsFilePath)));
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<FootballMatch>>() {
                    }.getType();
                    ArrayList<FootballMatch> previousFootballMatchList = gson.fromJson(json, listType);

                    // If match lists are not equal update the match list
                    if (!previousFootballMatchList.containsAll(footballMatchList)) {
                        System.out.println("Update to today's matches.");

                        // Save the list to a file in the specified directory
                        String filePath = folderPath + File.separator + "football_matches_today.json";
                        try (FileWriter writer = new FileWriter(filePath)) {
                            Gson gson2 = new Gson();
                            Type listType2 = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
                            String json2 = gson2.toJson(footballMatchList, listType2);
                            writer.write(json2);
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("No updates made to today's matches.");
                        return false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Initial update to today's matches.");

                // Save the list to a file in the specified directory
                String filePath = folderPath + File.separator + "football_matches_today.json";
                try (FileWriter writer = new FileWriter(filePath)) {
                    Gson gson2 = new Gson();
                    Type listType2 = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
                    String json2 = gson2.toJson(footballMatchList, listType2);
                    writer.write(json2);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateTomorrowMatchesLocally
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Updates the file football_matches_tomorrow.json with current data from the football-data.org API
    // containing all the matches for the upcoming day (Tomorrow). Returns true if update occurred.
    //
    public static boolean updateTomorrowMatchesLocally() {
        try {
            String tomorrowMatches = fetchData(BASE_URL + "matches?date=TOMORROW");

            // Initialize a new array list of FootballTeam
            ArrayList<FootballMatch> footballMatchList = new ArrayList<>();

            // Add all football matches from match data extractor array list
            footballMatchList.addAll(FootballAPIv4MatchDataExtractor.extractMatchesToArrayList(tomorrowMatches));

            // Initialize the previous football matches file
            String notificationsFilePath = folderPath + File.separator + "football_matches_tomorrow.json";
            File previousFootballMatchesFile = new File(notificationsFilePath);

            // Load the previous football matches file if it exists
            if (previousFootballMatchesFile.exists()) {
                // Load the previous football matches file
                try {
                    String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(notificationsFilePath)));
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<FootballMatch>>() {
                    }.getType();
                    ArrayList<FootballMatch> previousFootballMatchList = gson.fromJson(json, listType);

                    // If match lists are not equal update the match list
                    if (!previousFootballMatchList.containsAll(footballMatchList)) {
                        System.out.println("Update to tomorrow's matches");

                        // Save the list to a file in the specified directory
                        String filePath = folderPath + File.separator + "football_matches_tomorrow.json";
                        try (FileWriter writer = new FileWriter(filePath)) {
                            Gson gson2 = new Gson();
                            Type listType2 = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
                            String json2 = gson2.toJson(footballMatchList, listType2);
                            writer.write(json2);
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateUpcomingWeek1MatchesLocally
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Updates the file football_matches_upcoming_week1.json with current data from the football-data.org API
    // containing all the matches for the upcoming week (Tomorrow - A week after tomorrow). Returns true if
    // update occurred.
    //
    public static boolean updateUpcomingWeek1MatchesLocally() {
        // Extract the LocalDates
        LocalDate weekAfterTomorrow = LocalDate.now().plusWeeks(1).plusDays(1);
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        try {
            String upcomingWeek1Matches = fetchData(BASE_URL + "matches?dateFrom=" + tomorrow.toString() + "&dateTo=" + weekAfterTomorrow.toString());

            // Initialize a new array list of FootballTeam
            ArrayList<FootballMatch> footballMatchList = new ArrayList<>();

            // Add all football matches from match data extractor array list
            footballMatchList.addAll(FootballAPIv4MatchDataExtractor.extractMatchesToArrayList(upcomingWeek1Matches));

            // Initialize the previous football matches file
            String notificationsFilePath = folderPath + File.separator + "football_matches_upcoming_week1.json";
            File previousFootballMatchesFile = new File(notificationsFilePath);

            // Load the previous football matches file if it exists
            if (previousFootballMatchesFile.exists()) {
                // Load the previous football matches file
                try {
                    String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(notificationsFilePath)));
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<FootballMatch>>() {
                    }.getType();
                    ArrayList<FootballMatch> previousFootballMatchList = gson.fromJson(json, listType);

                    // If match lists are not equal update the match list
                    if (!previousFootballMatchList.containsAll(footballMatchList)) {
                        System.out.println("Update to upcoming week 1 matches");

                        // Save the list to a file in the specified directory
                        String filePath = folderPath + File.separator + "football_matches_upcoming_week1.json";
                        try (FileWriter writer = new FileWriter(filePath)) {
                            Gson gson2 = new Gson();
                            Type listType2 = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
                            String json2 = gson.toJson(footballMatchList, listType2);
                            writer.write(json2);
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateUpcomingWeek2MatchesLocally
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Updates the file football_matches_upcoming_week2.json with current data from the football-data.org API
    // containing all the matches for the second upcoming week (A week after tomorrow - Two weeks after
    // tomorrow). Returns true if update occurred.
    //
    public static boolean updateUpcomingWeek2MatchesLocally() {
        // Extract the LocalDates
        LocalDate secondWeekAfterTomorrow = LocalDate.now().plusWeeks(2).plusDays(1);
        LocalDate weekAfterTomorrow = LocalDate.now().plusWeeks(1).plusDays(1);

        try {
            String upcomingWeek2Matches = fetchData(BASE_URL + "matches?dateFrom=" + weekAfterTomorrow.toString() + "&dateTo=" + secondWeekAfterTomorrow.toString());

            // Initialize a new array list of FootballTeam
            ArrayList<FootballMatch> footballMatchList = new ArrayList<>();

            // Add all football matches from match data extractor array list
            footballMatchList.addAll(FootballAPIv4MatchDataExtractor.extractMatchesToArrayList(upcomingWeek2Matches));

            // Initialize the previous football matches file
            String notificationsFilePath = folderPath + File.separator + "football_matches_upcoming_week2.json";
            File previousFootballMatchesFile = new File(notificationsFilePath);

            // Load the previous football matches file if it exists
            if (previousFootballMatchesFile.exists()) {
                // Load the previous football matches file
                try {
                    String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(notificationsFilePath)));
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<FootballMatch>>() {
                    }.getType();
                    ArrayList<FootballMatch> previousFootballMatchList = gson.fromJson(json, listType);

                    // If match lists are not equal update the match list
                    if (!previousFootballMatchList.containsAll(footballMatchList)) {
                        System.out.println("Update to upcoming week 2 matches");

                        // Save the list to a file in the specified directory
                        String filePath = folderPath + File.separator + "football_matches_upcoming_week2.json";
                        try (FileWriter writer = new FileWriter(filePath)) {
                            Gson gson2 = new Gson();
                            Type listType2 = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
                            String json2 = gson2.toJson(footballMatchList, listType2);
                            writer.write(json2);
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateUpcomingWeek3MatchesLocally
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Updates the file football_matches_upcoming_week3.json with current data from the football-data.org API
    // containing all the matches for the third upcoming week (Two weeks after tomorrow - Three weeks after
    // tomorrow). Returns true if update occurred.
    //
    public static boolean updateUpcomingWeek3MatchesLocally() {
        // Extract the LocalDates
        LocalDate thirdWeekAfterTomorrow = LocalDate.now().plusWeeks(3).plusDays(1);
        LocalDate secondWeekAfterTomorrow = LocalDate.now().plusWeeks(2).plusDays(1);

        try {
            String upcomingWeek3Matches = fetchData(BASE_URL + "matches?dateFrom=" + secondWeekAfterTomorrow.toString() + "&dateTo=" + thirdWeekAfterTomorrow.toString());

            // Initialize a new array list of FootballTeam
            ArrayList<FootballMatch> footballMatchList = new ArrayList<>();

            // Add all football matches from match data extractor array list
            footballMatchList.addAll(FootballAPIv4MatchDataExtractor.extractMatchesToArrayList(upcomingWeek3Matches));

            // Initialize the previous football matches file
            String footballMatchesFilePath = folderPath + File.separator + "football_matches_upcoming_week3.json";
            File previousFootballMatchesFile = new File(footballMatchesFilePath);

            // Load the previous football matches file if it exists
            if (previousFootballMatchesFile.exists()) {
                // Load the previous football matches file
                try {
                    String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(footballMatchesFilePath)));
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
                    ArrayList<FootballMatch> previousFootballMatchList = gson.fromJson(json, listType);

                    // If match lists are not equal update the match list
                    if (!previousFootballMatchList.containsAll(footballMatchList)) {
                        System.out.println("Update to upcoming week 3 matches");

                        // Save the list to a file in the specified directory
                        String filePath = folderPath + File.separator + "football_matches_upcoming_week3.json";
                        try (FileWriter writer = new FileWriter(filePath)) {
                            Gson gson2 = new Gson();
                            Type listType2 = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
                            String json2 = gson2.toJson(footballMatchList, listType2);
                            writer.write(json2);
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateAllMatchesLocally
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Updates all local football match files from the football-data.org API.
    //
    public static void updateAllMatchesLocally() {
        updateYesterdayMatchesLocally();
        updateTodayMatchesLocally();
        updateTomorrowMatchesLocally();
        updateUpcomingWeek1MatchesLocally();
        updateUpcomingWeek2MatchesLocally();
        updateUpcomingWeek3MatchesLocally();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // readAndDisplayYesterdayMatchesFile
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Reads the data within the football_matches_yesterday.json file and displays it to the
    // console.
    //
    public static void readAndDisplayYesterdayMatchesFile() {
        // Initialize filePath and ArrayList of FootballMatch
        String filePath = folderPath + File.separator + "football_matches_yesterday.json";
        ArrayList<FootballMatch> loadedMatches = new ArrayList<>();
        // Read the list of FootballMatch objects back from the file
        try {
            String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
            loadedMatches = gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If loadedMatches contains the FootballMatch objects
        if (loadedMatches != null) {
            for (FootballMatch match : loadedMatches) {
                match.printKeyMatchDetails(); // Print each match's details
                System.out.println();
            }
        }
        else
            System.out.println("ERROR - Yesterday's football match data not found.\n");
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // readAndDisplayTodayMatchesFile
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Reads the data within the football_matches_today.json file and displays it to the console.
    //
    public static void readAndDisplayTodayMatchesFile() {
        // Initialize filePath and ArrayList of FootballMatch
        String filePath = folderPath + File.separator + "football_matches_today.json";
        ArrayList<FootballMatch> loadedMatches = new ArrayList<>();
        // Read the list of FootballMatch objects back from the file
        try {
            String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
            loadedMatches = gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If loadedMatches contains the FootballMatch objects
        if (loadedMatches != null) {
            for (FootballMatch match : loadedMatches) {
                match.printKeyMatchDetails(); // Print each match's details
                System.out.println();
            }
        }
        else
            System.out.println("ERROR - Today's football match data not found.\n");
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // readAndDisplayTomorrowMatchesFile
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Reads the data within the football_matches_tomorrow.json file and displays it to the
    // console.
    //
    public static void readAndDisplayTomorrowMatchesFile() {
        // Initialize filePath and ArrayList of FootballMatch
        String filePath = folderPath + File.separator + "football_matches_tomorrow.json";
        ArrayList<FootballMatch> loadedMatches = new ArrayList<>();
        // Read the list of FootballMatch objects back from the file
        try {
            String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
            loadedMatches = gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If loadedMatches contains the FootballMatch objects
        if (loadedMatches != null) {
            for (FootballMatch match : loadedMatches) {
                match.printKeyMatchDetails(); // Print each match's details
                System.out.println();
            }
        }
        else
            System.out.println("ERROR - Tomorrow's football match data not found.\n");
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // readAndDisplayUpcomingWeek1MatchesFile
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Reads the data within the football_matches_upcoming_week1.json file and displays it to
    // the console.
    //
    public static void readAndDisplayUpcomingWeek1MatchesFile() {
        // Initialize filePath and ArrayList of FootballMatch
        String filePath = folderPath + File.separator + "football_matches_upcoming_week1.json";
        ArrayList<FootballMatch> loadedMatches = new ArrayList<>();
        // Read the list of FootballMatch objects back from the file
        try {
            String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
            loadedMatches = gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If loadedMatches contains the FootballMatch objects
        if (loadedMatches != null) {
            for (FootballMatch match : loadedMatches) {
                match.printKeyMatchDetails(); // Print each match's details
                System.out.println();
            }
        }
        else
            System.out.println("ERROR - Upcoming week's football match data not found.\n");
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // readAndDisplayUpcomingWeek2MatchesFile
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Reads the data within the football_matches_upcoming_week2.json file and displays it to
    // the console.
    //
    public static void readAndDisplayUpcomingWeek2MatchesFile() {
        // Initialize filePath and ArrayList of FootballMatch
        String filePath = folderPath + File.separator + "football_matches_upcoming_week2.json";
        ArrayList<FootballMatch> loadedMatches = new ArrayList<>();
        // Read the list of FootballMatch objects back from the file
        try {
            String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
            loadedMatches = gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If loadedMatches contains the FootballMatch objects
        if (loadedMatches != null) {
            for (FootballMatch match : loadedMatches) {
                match.printKeyMatchDetails(); // Print each team's details
                System.out.println();
            }
        }
        else
            System.out.println("ERROR - Upcoming second week's football match data not found.\n");
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // readAndDisplayUpcomingWeek3MatchesFile
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Reads the data within the football_matches_upcoming_week3.json file and displays it to
    // the console.
    //
    public static void readAndDisplayUpcomingWeek3MatchesFile() {
        // Initialize filePath and ArrayList of FootballMatch
        String filePath = folderPath + File.separator + "football_matches_upcoming_week3.json";
        ArrayList<FootballMatch> loadedMatches = new ArrayList<>();
        // Read the list of FootballMatch objects back from the file
        try {
            String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
            loadedMatches = gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If loadedMatches contains the FootballMatch objects
        if (loadedMatches != null) {
            for (FootballMatch match : loadedMatches) {
                match.printKeyMatchDetails(); // Print each match's details
                System.out.println();
            }
        }
        else
            System.out.println("ERROR - Upcoming third week's football match data not found.\n");
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // readAndReturnYesterdayMatchesFile
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Reads the data within the football_matches_yesterday.json file and returns it as an
    // ArrayList of FootballMatch object(s).
    //
    public static ArrayList<FootballMatch> readAndReturnYesterdayMatchesFile() {
        // Initialize filePath and ArrayList of FootballMatch
        String filePath = folderPath + File.separator + "football_matches_yesterday.json";
        ArrayList<FootballMatch> loadedMatches = new ArrayList<>();
        // Read the list of FootballMatch objects back from the file
        try {
            String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
            loadedMatches = gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If loadedMatches contains the FootballMatch objects
        if (loadedMatches != null)
            return loadedMatches;
        else
            return null;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // readAndReturnTodayMatchesFile
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Reads the data within the football_matches_today.json file and returns it as an
    // ArrayList of FootballMatch object(s).
    //
    public static ArrayList<FootballMatch> readAndReturnTodayMatchesFile() {
        // Initialize filePath and ArrayList of FootballMatch
        String filePath = folderPath + File.separator + "football_matches_today.json";
        ArrayList<FootballMatch> loadedMatches = new ArrayList<>();
        // Read the list of FootballMatch objects back from the file
        try {
            String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
            loadedMatches = gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If loadedMatches contains the FootballMatch objects
        if (loadedMatches != null)
            return loadedMatches;
        else
            return null;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // readAndReturnTomorrowMatchesFile
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Reads the data within the football_matches_tomorrow.json file and returns it as an
    // ArrayList of FootballMatch object(s).
    //
    public static ArrayList<FootballMatch> readAndReturnTomorrowMatchesFile() {
        // Initialize filePath and ArrayList of FootballMatch
        String filePath = folderPath + File.separator + "football_matches_tomorrow.json";
        ArrayList<FootballMatch> loadedMatches = new ArrayList<>();
        // Read the list of FootballMatch objects back from the file
        try {
            String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
            loadedMatches = gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If loadedMatches contains the FootballMatch objects
        if (loadedMatches != null)
            return loadedMatches;
        else
            return null;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // readAndReturnUpcomingWeekMatchesFile
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Reads the data within the football_matches_upcoming_week1.json file and returns it as an
    // ArrayList of FootballMatch object(s).
    //
    public static ArrayList<FootballMatch> readAndReturnUpcomingWeek1MatchesFile() {
        // Initialize filePath and ArrayList of FootballMatch
        String filePath = folderPath + File.separator + "football_matches_upcoming_week1.json";
        ArrayList<FootballMatch> loadedMatches = new ArrayList<>();
        // Read the list of FootballMatch objects back from the file
        try {
            String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
            loadedMatches = gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If loadedMatches contains the FootballMatch objects
        if (loadedMatches != null)
            return loadedMatches;
        else
            return null;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // readAndReturnUpcomingWeek2MatchesFile
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Reads the data within the football_matches_upcoming_week2.json file and returns it as an
    // ArrayList of FootballMatch object(s).
    //
    public static ArrayList<FootballMatch> readAndReturnUpcomingWeek2MatchesFile() {
        // Initialize filePath and ArrayList of FootballMatch
        String filePath = folderPath + File.separator + "football_matches_upcoming_week2.json";
        ArrayList<FootballMatch> loadedMatches = new ArrayList<>();
        // Read the list of FootballMatch objects back from the file
        try {
            String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
            loadedMatches = gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If loadedMatches contains the FootballMatch objects
        if (loadedMatches != null)
            return loadedMatches;
        else
            return null;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // readAndReturnUpcomingWeek3MatchesFile
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Reads the data within the football_matches_upcoming_week3.json file and returns it as an
    // ArrayList of FootballMatch object(s).
    //
    public static ArrayList<FootballMatch> readAndReturnUpcomingWeek3MatchesFile() {
        // Initialize filePath and ArrayList of FootballMatch
        String filePath = folderPath + File.separator + "football_matches_upcoming_week3.json";
        ArrayList<FootballMatch> loadedMatches = new ArrayList<>();
        // Read the list of FootballMatch objects back from the file
        try {
            String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
            loadedMatches = gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If loadedMatches contains the FootballMatch objects
        if (loadedMatches != null)
            return loadedMatches;
        else
            return null;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateYesterdayMatchesLocally
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Updates the file football_matches_yesterday.json with current data from the football-data.org API
    // containing all the matches for the prior day (Yesterday).
    public static void updateYesterdayMatchesMongoDB() {
        try {
            String yesterdayMatches = fetchData(BASE_URL + "matches?date=YESTERDAY");

            // Initialize a new array list of FootballTeam
            ArrayList<FootballMatch> footballMatchList = new ArrayList<>();

            // Add all football teams from team data extractor array list
            footballMatchList.addAll(FootballAPIv4MatchDataExtractor.extractMatchesToArrayList(yesterdayMatches));

            // Update yesterday's football match list using MongoDB
            MongoDBFootballMatchData.updateDocumentsWithinTimeframe("Yesterday", footballMatchList);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateTodayMatchesMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Updates the file football_matches_today.json with current data from the football-data.org API
    // containing all the matches for the current day (Today). Returns true if update occurred.
    //
    public static boolean updateTodayMatchesMongoDB() {
        try {
            String todayMatches = fetchData(BASE_URL + "matches");

            // Initialize a new array list of FootballTeam
            ArrayList<FootballMatch> footballMatchList = new ArrayList<>();

            // Add all football matches from match data extractor array list
            footballMatchList.addAll(FootballAPIv4MatchDataExtractor.extractMatchesToArrayList(todayMatches));

            ArrayList<FootballMatch> previousFootballMatches = MongoDBFootballMatchData.retrieveDocumentsWithinTimeFrameAndReturn("Today").getFootballMatches();

            // Load the previous football matches file if it exists
            if (!previousFootballMatches.isEmpty()) {
                // If match lists are not equal update the match list
                if (!previousFootballMatches.containsAll(footballMatchList)) {
                    System.out.println("Updated today's matches.");
                    MongoDBFootballMatchData.updateDocumentsWithinTimeframe("Today", footballMatchList);
                    return true;
                } else {
                    System.out.println("No updates made to today's matches.");
                    return false;
                }
            } else {
                System.out.println("Initial update to today's matches.");
                MongoDBFootballMatchData.updateDocumentsWithinTimeframe("Today", footballMatchList);
                return true;
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateTomorrowMatchesMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Updates the file football_matches_tomorrow.json with current data from the football-data.org API
    // containing all the matches for the current day (Tomorrow). Returns true if update occurred.
    //
    public static boolean updateTomorrowMatchesMongoDB() {
        try {
            String tomorrowMatches = fetchData(BASE_URL + "matches");

            // Initialize a new array list of FootballMatch
            ArrayList<FootballMatch> footballMatchList = FootballAPIv4MatchDataExtractor.extractMatchesToArrayList(tomorrowMatches);

            ArrayList<FootballMatch> previousFootballMatches = MongoDBFootballMatchData.retrieveDocumentsWithinTimeFrameAndReturn("Tomorrow").getFootballMatches();

            // Load the previous football matches file if it exists
            if (!previousFootballMatches.isEmpty()) {
                // If match lists are not equal update the match list
                if (!previousFootballMatches.containsAll(footballMatchList)) {
                    System.out.println("Updated tomorrow's matches.");
                    MongoDBFootballMatchData.updateDocumentsWithinTimeframe("Tomorrow", footballMatchList);
                    return true;
                } else {
                    System.out.println("No updates made to tomorrow's matches.");
                    return false;
                }
            } else {
                System.out.println("Initial update to tomorrow's matches.");
                MongoDBFootballMatchData.updateDocumentsWithinTimeframe("Tomorrow", footballMatchList);
                return true;
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateUpcomingWeek1MatchesMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Updates the file football_matches_upcomingWeek1.json with current data from the football-data.org API
    // containing all the matches for the current day (UpcomingWeek1). Returns true if update occurred.
    //
    public static boolean updateUpcomingWeek1MatchesMongoDB() {
        try {
            String upcomingWeek1Matches = fetchData(BASE_URL + "matches");

            // Initialize a new array list of FootballTeam
            ArrayList<FootballMatch> footballMatchList = new ArrayList<>();

            // Add all football matches from match data extractor array list
            footballMatchList.addAll(FootballAPIv4MatchDataExtractor.extractMatchesToArrayList(upcomingWeek1Matches));

            ArrayList<FootballMatch> previousFootballMatches = MongoDBFootballMatchData.retrieveDocumentsWithinTimeFrameAndReturn("UpcomingWeek1").getFootballMatches();

            // Load the previous football matches file if it exists
            if (!previousFootballMatches.isEmpty()) {
                // If match lists are not equal update the match list
                if (!previousFootballMatches.containsAll(footballMatchList)) {
                    System.out.println("Updated upcomingWeek1's matches.");
                    MongoDBFootballMatchData.updateDocumentsWithinTimeframe("UpcomingWeek1", footballMatchList);
                    return true;
                } else {
                    System.out.println("No updates made to upcomingWeek1's matches.");
                    return false;
                }
            } else {
                System.out.println("Initial update to upcomingWeek1's matches.");
                MongoDBFootballMatchData.updateDocumentsWithinTimeframe("UpcomingWeek1", footballMatchList);
                return true;
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateUpcomingWeek2MatchesMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Updates the file football_matches_upcomingWeek2.json with current data from the football-data.org API
    // containing all the matches for the current day (UpcomingWeek2). Returns true if update occurred.
    //
    public static boolean updateUpcomingWeek2MatchesMongoDB() {
        try {
            String upcomingWeek2Matches = fetchData(BASE_URL + "matches");

            // Initialize a new array list of FootballTeam
            ArrayList<FootballMatch> footballMatchList = new ArrayList<>();

            // Add all football matches from match data extractor array list
            footballMatchList.addAll(FootballAPIv4MatchDataExtractor.extractMatchesToArrayList(upcomingWeek2Matches));

            ArrayList<FootballMatch> previousFootballMatches = MongoDBFootballMatchData.retrieveDocumentsWithinTimeFrameAndReturn("UpcomingWeek2").getFootballMatches();

            // Load the previous football matches file if it exists
            if (!previousFootballMatches.isEmpty()) {
                // If match lists are not equal update the match list
                if (!previousFootballMatches.containsAll(footballMatchList)) {
                    System.out.println("Updated upcomingWeek2's matches.");
                    MongoDBFootballMatchData.updateDocumentsWithinTimeframe("UpcomingWeek2", footballMatchList);
                    return true;
                } else {
                    System.out.println("No updates made to upcomingWeek2's matches.");
                    return false;
                }
            } else {
                System.out.println("Initial update to upcomingWeek2's matches.");
                MongoDBFootballMatchData.updateDocumentsWithinTimeframe("UpcomingWeek2", footballMatchList);
                return true;
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateUpcomingWeek3MatchesMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Updates the file football_matches_upcomingWeek3.json with current data from the football-data.org API
    // containing all the matches for the current day (UpcomingWeek3). Returns true if update occurred.
    //
    public static boolean updateUpcomingWeek3MatchesMongoDB() {
        try {
            String upcomingWeek3Matches = fetchData(BASE_URL + "matches");

            // Initialize a new array list of FootballTeam
            ArrayList<FootballMatch> footballMatchList = new ArrayList<>();

            // Add all football matches from match data extractor array list
            footballMatchList.addAll(FootballAPIv4MatchDataExtractor.extractMatchesToArrayList(upcomingWeek3Matches));

            ArrayList<FootballMatch> previousFootballMatches = MongoDBFootballMatchData.retrieveDocumentsWithinTimeFrameAndReturn("UpcomingWeek3").getFootballMatches();

            // Load the previous football matches file if it exists
            if (!previousFootballMatches.isEmpty()) {
                // If match lists are not equal update the match list
                if (!previousFootballMatches.containsAll(footballMatchList)) {
                    System.out.println("Updated upcomingWeek3's matches.");
                    MongoDBFootballMatchData.updateDocumentsWithinTimeframe("UpcomingWeek3", footballMatchList);
                    return true;
                } else {
                    System.out.println("No updates made to upcomingWeek3's matches.");
                    return false;
                }
            } else {
                System.out.println("Initial update to upcomingWeek3's matches.");
                MongoDBFootballMatchData.updateDocumentsWithinTimeframe("UpcomingWeek3", footballMatchList);
                return true;
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateAllMatchesMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Updates all local football match files from the football-data.org API.
    //
    public static void updateAllMatchesMongoDB() {
        updateYesterdayMatchesMongoDB();
        updateTodayMatchesMongoDB();
        updateTomorrowMatchesMongoDB();
        updateUpcomingWeek1MatchesMongoDB();
        updateUpcomingWeek2MatchesMongoDB();
        updateUpcomingWeek3MatchesMongoDB();
    }
}