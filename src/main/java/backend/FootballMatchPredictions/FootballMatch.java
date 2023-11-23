package backend.FootballMatchPredictions;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// FootballMatchPredictions.FootballMatch object class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Represents a football (soccer) match given the data supplied by calls to the football-data.org API
// used within the Envisionary web app.
//
public final class FootballMatch {
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // FootballMatchPredictions.FootballMatch object variables
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    @SerializedName("areaName")
    private String areaName;            // Name of the area where the match is held
    @SerializedName("competitionName")
    private String competitionName;     // Name of the competition to which the match belongs
    @SerializedName("seasonStartDate")
    private String seasonStartDate;     // Start date of the competition season
    @SerializedName("seasonEndDate")
    private String seasonEndDate;       // End date of the competition season
    @SerializedName("matchId")
    private int matchId;                // Number representing the match ID
    @SerializedName("utcDate")
    private String utcDate;             // UTC (Coordinated Universal Time) Date of the given match (NY is UTC-04:00 in DST and UTC-05:00 when standard)
    @SerializedName("status")
    private String status;              // Status of the match
    @SerializedName("matchDay")
    private int matchDay;               // Number representing match day of the given match
    @SerializedName("stage")
    private String stage;               // Stage of the match
    @SerializedName("group")
    private String group;               // Group of the match
    @SerializedName("lastUpdated")
    private String lastUpdated;         // Date of last update
    @SerializedName("homeTeam")
    private String homeTeam;            // Name of the match's home team
    @SerializedName("awayTeam")
    private String awayTeam;            // Name of the match's away team
    @SerializedName("winner")
    private String winner;              // Winner of the match
    @SerializedName("duration")
    private String duration;            // Duration of the match
    @SerializedName("homeScore")
    private int homeScore;              // Full-time score of the home team
    @SerializedName("awayScore")
    private int awayScore;              // Full-time score of the away team
    @SerializedName("halfTimeHomeScore")
    private int halfTimeHomeScore;      // Halftime score of the home team
    @SerializedName("halfTimeAwayScore")
    private int halfTimeAwayScore;      // Halftime score of the away team
    @SerializedName("homeTeamCrest")
    private String homeTeamCrest;       // Home team crest image URL
    @SerializedName("awayTeamCrest")
    private String awayTeamCrest;       // Away team crest image URL


    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Getter and setter methods for the FootballMatchPredictions.FootballMatch object variables
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public String getAreaName() {
        return areaName;
    }
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCompetitionName() {
        return competitionName;
    }
    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public String getSeasonStartDate() {
        return seasonStartDate;
    }
    public void setSeasonStartDate(String seasonStartDate) {
        this.seasonStartDate = seasonStartDate;
    }

    public String getSeasonEndDate() {
        return seasonEndDate;
    }
    public void setSeasonEndDate(String seasonEndDate) {
        this.seasonEndDate = seasonEndDate;
    }

    public int getMatchId() {
        return matchId;
    }
    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public String getUtcDate() {
        return utcDate;
    }
    public void setUtcDate(String utcDate) {
        this.utcDate = utcDate;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getMatchDay() {
        return matchDay;
    }
    public void setMatchDay(int matchDay) {
        this.matchDay = matchDay;
    }

    public String getStage() {
        return stage;
    }
    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getGroup() {
        return group;
    }
    public void setGroup(String group) {
        this.group = group;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getHomeTeam() {
        return homeTeam;
    }
    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }
    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getWinner() {
        return winner;
    }
    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getHomeScore() {
        return homeScore;
    }
    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }
    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public int getHalfTimeHomeScore() {
        return halfTimeHomeScore;
    }
    public void setHalfTimeHomeScore(int halfTimeHomeScore) {
        this.halfTimeHomeScore = halfTimeHomeScore;
    }

    public int getHalfTimeAwayScore() {
        return halfTimeAwayScore;
    }
    public void setHalfTimeAwayScore(int halfTimeAwayScore) {
        this.halfTimeAwayScore = halfTimeAwayScore;
    }

    public String getHomeTeamCrest() {
        return homeTeamCrest;
    }
    public void setHomeTeamCrest(String homeTeamCrest) {
        this.homeTeamCrest = homeTeamCrest;
    }

    public String getAwayTeamCrest() {
        return awayTeamCrest;
    }
    public void setAwayTeamCrest(String awayTeamCrest) {
        this.awayTeamCrest = awayTeamCrest;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Default Empty Constructor
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public FootballMatch() {}

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printMatchDetails
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to print all the FootballMatchPredictions.FootballMatch object details to the console.
    //
    public void printMatchDetails() {
        System.out.println("Competition Name: " + competitionName);
        System.out.println("Home Team: " + homeTeam);
        System.out.println("Away Team: " + awayTeam);
        System.out.println("UTC Date: " + utcDate);
        System.out.println("Location: " + areaName);
        System.out.println("Status: " + status);
        System.out.println("Winner: " + winner);
        System.out.println("Duration: " + duration);
        System.out.println("Home Score: " + homeScore);
        System.out.println("Away Score: " + awayScore);
        System.out.println("Half-time Home Score: " + halfTimeHomeScore);
        System.out.println("Half-time Away Score: " + halfTimeAwayScore);
        System.out.println("Season Start Date: " + seasonStartDate);
        System.out.println("Season End Date: " + seasonEndDate);
        System.out.println("Match ID: " + matchId);
        System.out.println("Match Day: " + matchDay);
        System.out.println("Stage: " + stage);
        System.out.println("Group: " + group);
        System.out.println("Last Updated: " + lastUpdated);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printKeyMatchDetails
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to print the FootballMatchPredictions.FootballMatch object key details to the console.
    //
    public void printKeyMatchDetails() {
        System.out.println("Competition Name: " + competitionName);
        System.out.println("Home Team: " + homeTeam);
        System.out.println("Away Team: " + awayTeam);
        System.out.println("UTC Date: " + utcDate);
        System.out.println("Location: " + areaName);
        System.out.println("Status: " + status);
        System.out.println("Winner: " + winner);
        System.out.println("Home Score: " + homeScore);
        System.out.println("Away Score: " + awayScore);
        System.out.println("Half-time Home Score: " + halfTimeHomeScore);
        System.out.println("Half-time Away Score: " + halfTimeAwayScore);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // toJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to convert the FootballMatchPredictions.FootballMatch object to JSON.
    //
    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // fromJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Static method to create a FootballMatchPredictions.FootballMatch object from JSON.
    //
    public static FootballMatch fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, FootballMatch.class);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // @Override equals
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to compare two FootballMatchPredictions.FootballMatch objects
    //
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FootballMatch other = (FootballMatch) obj;
        return matchId == other.matchId &&
                homeScore == other.homeScore &&
                awayScore == other.awayScore &&
                halfTimeHomeScore == other.halfTimeHomeScore &&
                halfTimeAwayScore == other.halfTimeAwayScore &&
                Objects.equals(areaName, other.areaName) &&
                Objects.equals(competitionName, other.competitionName) &&
                Objects.equals(seasonStartDate, other.seasonStartDate) &&
                Objects.equals(seasonEndDate, other.seasonEndDate) &&
                Objects.equals(utcDate, other.utcDate) &&
                Objects.equals(status, other.status) &&
                Objects.equals(stage, other.stage) &&
                Objects.equals(group, other.group) &&
                Objects.equals(lastUpdated, other.lastUpdated) &&
                Objects.equals(homeTeam, other.homeTeam) &&
                Objects.equals(awayTeam, other.awayTeam) &&
                Objects.equals(winner, other.winner) &&
                Objects.equals(duration, other.duration) &&
                Objects.equals(homeTeamCrest, other.homeTeamCrest) &&
                Objects.equals(awayTeamCrest, other.awayTeamCrest);
    }
}