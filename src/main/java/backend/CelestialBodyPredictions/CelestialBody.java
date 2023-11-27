package backend.CelestialBodyPredictions;

import com.google.gson.annotations.SerializedName;

public class CelestialBody {
    @SerializedName("celestialBodyType")
    private String celestialBodyType; // String ID of celestial body (Ex. moon, planet, asteroid, star, etc...)
    @SerializedName("knownCount")
    private int knownCount;         // Number representing known count of the celestial body
    @SerializedName("updatedDate")
    private String updatedDate;     // String representation of last date the count was updated (DD/MM/YYYY)

    public String getCelestialBodyType(){
        return celestialBodyType;
    }

    public void setCelestialBodyType(String celestialBodyType) {
        this.celestialBodyType = celestialBodyType;
    }

    public int getKnownCount(){
        return knownCount;
    }

    public void setKnownCount(int knownCount){
        this.knownCount=knownCount;
    }

    public String getUpdatedDate(){
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    // Default Empty Constructor
    public CelestialBody() {}

    // Constructor with parameters (for deserialization)
    public CelestialBody(String celestialBodyType, int knownCount, String updatedDate) {
        this.celestialBodyType = celestialBodyType;
        this.knownCount = knownCount;
        this.updatedDate = updatedDate;
    }

    public String toString(){
        return "Celestial Body Type: " + getCelestialBodyType() + "\n" +
                "Known Count: " + getKnownCount() + "\n" +
                "Last Updated: " + getUpdatedDate();
    }
}