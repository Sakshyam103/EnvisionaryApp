package backend.CelestialBodyPredictions;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class CelestialBodiesAPI {
    // *****************************************************************************
    // Parsed the data and the string received from the api is parsed into an
    // arraylist of space objects
    public static ArrayList<CelestialBody> getSortedData(String spaceData) {
        ArrayList<CelestialBody> celestialBodyList = new ArrayList<>();

        try {
            JsonObject jsonObject = new JsonObject(spaceData);
            JSONArray knownCountArray = jsonObject.getJSONArray("knowncount");

            for (int i = 0; i < knownCountArray.length(); i++) {
                JSONObject celestialBodyData = knownCountArray.getJSONObject(i);

                String id = celestialBodyData.getString("id");
                int knownCount = celestialBodyData.getInt("knownCount");
                String updateDate = celestialBodyData.getString("updateDate");

                CelestialBody celestialBody = new CelestialBody();
                celestialBody.setCelestialBodyType(id);
                celestialBody.setKnownCount(knownCount);
                celestialBody.setUpdatedDate(updateDate);

                celestialBodyList.add(celestialBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return celestialBodyList;
    }

    // *****************************************************************************
    // This is where the api reads the data and returns the data as a String
    public static String getSpaceData() {
        URL url;
        {
            try {
                url = new URL("https://api.le-systeme-solaire.net/rest/knowncount/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();

                if (responseCode != 200) {
                    throw new RuntimeException("HttpResponseCode = " + responseCode);
                } else {

                    StringBuilder data = new StringBuilder();
                    Scanner scanner = new Scanner(url.openStream());

                    while (scanner.hasNext()) {
                        data.append(scanner.nextLine());
                    }
                    scanner.close();
                    return data.toString();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}