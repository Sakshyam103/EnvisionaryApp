package backend.FootballMatchPredictions;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// FootballAPIv4MatchDataExtractor class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Formats the incoming String football match data into a JSON object for display onto the console or
// to return as an ArrayList of FootballMatch objects.
//
public class FootballAPIv4MatchDataExtractor {
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // extractAndDisplayMatches class
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Formats the String football match data into a JSON file format and displays the matches onto the
    // console.
    //
    public static void extractAndDisplayMatches(String jsonData) {
        try {
            // Parse the JSON data
            JSONObject jsonObject = new JSONObject(jsonData);

            // Extract data from the JSON object
            JSONObject filters = jsonObject.getJSONObject("filters");
            JSONObject resultSet = jsonObject.getJSONObject("resultSet");
            JSONArray matches = jsonObject.getJSONArray("matches");

            // Print filter data
            System.out.println("Filters:");
            System.out.println("Date From: " + filters.optString("dateFrom"));
            System.out.println("Date To: " + filters.optString("dateTo"));
            System.out.println("Permission: " + filters.optString("permission"));

            // Print resultSet data
            System.out.println("\nResultSet:");
            System.out.println("Count: " + resultSet.getInt("count"));
            System.out.println("Competitions: " + resultSet.getString("competitions"));
            System.out.println("First: " + resultSet.getString("first"));
            System.out.println("Last: " + resultSet.getString("last"));
            System.out.println("Played: " + resultSet.getInt("played"));

            System.out.println("\nMatches:");
            // For each match within the JSON object
            for (int i = 0; i < matches.length(); i++) {
                // Extract match information
                JSONObject match = matches.getJSONObject(i);
                JSONObject area = match.getJSONObject("area");
                JSONObject competition = match.getJSONObject("competition");
                JSONObject season = match.getJSONObject("season");
                JSONObject homeTeam = match.getJSONObject("homeTeam");
                JSONObject awayTeam = match.getJSONObject("awayTeam");
                JSONObject score = match.getJSONObject("score");
                JSONArray referees = match.optJSONArray("referees");

                // Print match index
                System.out.println("Match " + (i + 1));

                // Print match area
                System.out.println("Area: " + area.getString("name"));

                // Print match competition
                System.out.println("Competition: " + competition.getString("name"));

                // Print match season data
                System.out.println("Season Start Date: " + season.getString("startDate"));
                System.out.println("Season End Date: " + season.getString("endDate"));
                System.out.println("Match ID: " + match.getInt("id"));
                System.out.println("UTC Date: " + match.getString("utcDate"));
                System.out.println("Status: " + match.getString("status"));
                System.out.println("Matchday: " + match.getInt("matchday"));
                System.out.println("Stage: " + match.getString("stage"));
                System.out.println("Group: " + match.optString("group", "N/A"));
                System.out.println("Last Updated: " + match.getString("lastUpdated"));

                // Print home and away team names
                System.out.println("Home Team: " + homeTeam.getString("name"));
                System.out.println("Away Team: " + awayTeam.getString("name"));

                // Print score data
                System.out.println("Score:");
                System.out.println("Winner: " + score.optString("winner", "N/A"));
                System.out.println("Duration: " + score.getString("duration"));
                System.out.println("Full-Time Home Score: " + score.getJSONObject("fullTime").optInt("home"));
                System.out.println("Full-Time Away Score: " + score.getJSONObject("fullTime").optInt("away"));
                System.out.println("Half-Time Home Score: " + score.getJSONObject("halfTime").optInt("home"));
                System.out.println("Half-Time Away Score: " + score.getJSONObject("halfTime").optInt("away"));

                // Print referees data
                if (referees != null) {
                    System.out.println("Referees:");
                    for (int j = 0; j < referees.length(); j++) {
                        JSONObject referee = referees.getJSONObject(j);
                        System.out.println("Referee " + (j + 1));
                        System.out.println("ID: " + referee.getInt("id"));
                        System.out.println("Name: " + referee.getString("name"));
                        System.out.println("Type: " + referee.getString("type"));
                        System.out.println("Nationality: " + referee.getString("nationality"));
                    }
                } else {
                    System.out.println("Referees: N/A");
                }

                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // extractMatchesToArrayList class
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Formats the String football match data into a JSON file format and returns the list of football
    // matches as an ArrayList of FootballMatch objects.
    //
    public static ArrayList<FootballMatch> extractMatchesToArrayList(String jsonData) {

        // Array list of FootballMatch objects to return after extraction of match data
        ArrayList<FootballMatch> footballMatchList = new ArrayList<>();

        try {
            // Parse the JSON data
            JSONObject jsonObject = new JSONObject(jsonData);

            // Extract data from the JSON object
            JSONArray matches = jsonObject.getJSONArray("matches");

            // For each match within the JSON object
            for (int i = 0; i < matches.length(); i++) {

                // Extract match information
                JSONObject match = matches.getJSONObject(i);
                JSONObject area = match.getJSONObject("area");
                JSONObject competition = match.getJSONObject("competition");
                JSONObject season = match.getJSONObject("season");
                JSONObject homeTeam = match.getJSONObject("homeTeam");
                JSONObject awayTeam = match.getJSONObject("awayTeam");
                JSONObject score = match.getJSONObject("score");

                // Initialize a new FootballMatch
                FootballMatch footballMatch = new FootballMatch();

                // Store match area
                footballMatch.setAreaName(area.getString("name"));

                // Store match competition
                footballMatch.setCompetitionName(competition.getString("name"));

                // Store match season data
                footballMatch.setSeasonStartDate(season.getString("startDate"));
                footballMatch.setSeasonEndDate(season.getString("endDate"));
                footballMatch.setMatchId(match.getInt("id"));
                footballMatch.setUtcDate(match.getString("utcDate"));
                footballMatch.setStatus(match.getString("status"));
                footballMatch.setMatchDay(match.getInt("matchday"));
                footballMatch.setStage(match.getString("stage"));
                footballMatch.setGroup(match.optString("group", "N/A"));
                footballMatch.setLastUpdated(match.getString("lastUpdated"));

                // Store home and away team names
                footballMatch.setHomeTeam(homeTeam.getString("name"));
                footballMatch.setAwayTeam(awayTeam.getString("name"));

                // Store score data
                footballMatch.setWinner(score.optString("winner", "N/A"));
                footballMatch.setDuration(score.getString("duration"));
                footballMatch.setHomeScore(score.getJSONObject("fullTime").optInt("home"));
                footballMatch.setAwayScore(score.getJSONObject("fullTime").optInt("away"));
                footballMatch.setHalfTimeHomeScore(score.getJSONObject("halfTime").optInt("home"));
                footballMatch.setHalfTimeAwayScore(score.getJSONObject("halfTime").optInt("away"));

                // Add current match to array list of FootballMatch
                footballMatchList.add(footballMatch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return footballMatchList;
    }
}