package backend.FootballMatchPredictions;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// FootballAPIv4TeamDataExtractor class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Formats the incoming String football team data into a JSON object for display onto the console or
// to return as an ArrayList of FootballMatch objects.
//
public class FootballAPIv4TeamDataExtractor {
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // extractAndDisplayTeams class
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Formats the String football teams data into a JSON file format and displays the matches onto the
    // console.
    //
    public static void extractAndDisplayTeams(String jsonData) {
        try {
            // Parse the JSON data
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray teams = jsonObject.getJSONArray("teams");

            // Loop through the teams and print their information
            for (int i = 0; i < teams.length(); i++) {
                JSONObject team = teams.getJSONObject(i);
                System.out.println("Team " + (i + 1));
                System.out.println("ID: " + team.getInt("id"));
                System.out.println("Name: " + team.getString("name"));
                System.out.println("Short Name: " + team.getString("shortName"));
                System.out.println("TLA: " + team.getString("tla"));
                System.out.println("Crest: " + team.getString("crest"));
                System.out.println("Address: " + team.getString("address"));
                System.out.println("Website: " + team.getString("website"));
                System.out.println("Founded: " + team.getInt("founded"));
                System.out.println("Club Colors: " + team.optString("clubColors", "N/A"));
                System.out.println("Venue: " + team.optString("venue", "N/A"));
                System.out.println("Last Updated: " + team.getString("lastUpdated"));
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // extractTeamsToArrayList class
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Formats the String football teams data into a JSON file format and returns the list of football
    // teams as an ArrayList of FootballTeam objects.
    //
    public static ArrayList<FootballTeam> extractTeamsToArrayList(String jsonData) {
        ArrayList<FootballTeam> footballTeamList = new ArrayList<>();
        try {
            // Parse the JSON data
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray teams = jsonObject.getJSONArray("teams");

            // Loop through the teams and print their information
            for (int i = 0; i < teams.length(); i++) {
                JSONObject team = teams.getJSONObject(i);

                // Initialize a new FootballTeam
                FootballTeam footballTeam = new FootballTeam();

                // Store football team data
                footballTeam.setTeamId(team.getInt("id"));
                footballTeam.setTeamName(team.getString("name"));
                footballTeam.setShortTeamName(team.getString("shortName"));
                footballTeam.setTla(team.getString("tla"));
                footballTeam.setCrestURL(team.getString("crest"));
                footballTeam.setTeamAddress(team.getString("address"));
                footballTeam.setWebsiteURL(team.getString("website"));
                footballTeam.setYearFounded(team.getInt("founded"));
                footballTeam.setClubColors(team.optString("clubColors", "N/A"));
                footballTeam.setVenue(team.optString("venue", "N/A"));
                footballTeam.setLastUpdated(team.getString("lastUpdated"));

                // Add current team to array list of FootballTeam
                footballTeamList.add(footballTeam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return footballTeamList;
    }
}