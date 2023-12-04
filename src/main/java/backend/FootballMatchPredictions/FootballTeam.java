package backend.FootballMatchPredictions;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// FootballTeam object class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Represents a football (soccer) team given the data supplied by calls to the football-data.org API
// used within the Envisionary web app.
//
public final class FootballTeam {
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // FootballTeam object variables
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    @SerializedName("teamId")
    private int teamId;				// Team ID number
    @SerializedName("teamName")
    private String teamName;		// Full team name
    @SerializedName("shortTeamName")
    private String shortTeamName;	// Shortened team name
    @SerializedName("tla")
    private String tla;				// Three letter acronym version of name
    @SerializedName("crestURL")
    private String crestURL;		// Team crest image URL
    @SerializedName("teamAddress")
    private String teamAddress;		// Team address
    @SerializedName("websiteURL")
    private String websiteURL;		// Team website URL
    @SerializedName("yearFounded")
    private int yearFounded;		// Year of team founding
    @SerializedName("clubColors")
    private String clubColors;		// Team's club colors
    @SerializedName("venue")
    private String venue;			// Team's home venue
    @SerializedName("lastUpdated")
    private String lastUpdated;		// Last updated date

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Getter and setter methods for the FootballTeam object variables
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public int getTeamId() {
        return teamId;
    }
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getShortTeamName() {
        return shortTeamName;
    }
    public void setShortTeamName(String shortTeamName) {
        this.shortTeamName = shortTeamName;
    }

    public String getTla() {
        return tla;
    }
    public void setTla(String tla) {
        this.tla = tla;
    }

    public String getCrestURL() {
        return crestURL;
    }
    public void setCrestURL(String crestURL) {
        this.crestURL = crestURL;
    }

    public String getTeamAddress() {
        return teamAddress;
    }
    public void setTeamAddress(String teamAddress) {
        this.teamAddress = teamAddress;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }
    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public int getYearFounded() {
        return yearFounded;
    }
    public void setYearFounded(int yearFounded) {
        this.yearFounded = yearFounded;
    }

    public String getClubColors() {
        return clubColors;
    }
    public void setClubColors(String clubColors) {
        this.clubColors = clubColors;
    }

    public String getVenue() {
        return venue;
    }
    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Default Empty Constructor
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public FootballTeam() {}

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printTeamDetails
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to print the FootballTeam object details to the console.
    //
    public void printTeamDetails() {
        System.out.println("Team ID: " + teamId);
        System.out.println("Team Name: " + teamName);
        System.out.println("Short Team Name: " + shortTeamName);
        System.out.println("TLA: " + tla);
        System.out.println("Crest URL: " + crestURL);
        System.out.println("Team Address: " + teamAddress);
        System.out.println("Year Founded: " + yearFounded);
        System.out.println("Club Colors: " + clubColors);
        System.out.println("Venue: " + venue);
        System.out.println("Last Updated: " + lastUpdated);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // toJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to convert the FootballTeam object to JSON.
    //
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // fromJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Static method to create a FootballTeam object from JSON.
    //
    public static FootballTeam fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, FootballTeam.class);
    }
}