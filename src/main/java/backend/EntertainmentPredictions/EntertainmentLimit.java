package backend.EntertainmentPredictions;

import com.google.gson.annotations.SerializedName;

import java.time.ZonedDateTime;

public final class EntertainmentLimit {

    // variables
    @SerializedName("counter")
    private int counter = 0;

    @SerializedName("date")
    private String date = ZonedDateTime.now().toString();

    @SerializedName("active")
    private boolean active = false;

    // get

    public int getCounter() {return counter;}

    public String getDate() {return date.toString();}

    public boolean getActive() {return active;}

    // set

    public void setCounter(int counter){this.counter = counter;}

    public void setDate(String date){this.date = date;}

    public void setActive(boolean active){this.active = active;}

    // constructors

    public EntertainmentLimit(){}

    public EntertainmentLimit(int counter, String date, boolean active){
        this.counter = counter;
        this.date = date;
        this.active = active;
    }

}
