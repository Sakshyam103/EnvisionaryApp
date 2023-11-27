package backend.CelestialBodyPredictions;

import java.util.ArrayList;

public class CelestialBodiesAPIDataExtractor {

    //Returns list of ID's as an arraylist of Strings
    public static ArrayList<String> getListofIDs(ArrayList<CelestialBody> sortedData) {
        ArrayList<String> list = new ArrayList<>();
        CelestialBody x;
        for (int i=0; i<sortedData.size(); i++){
            x= sortedData.get(i);
            list.add(x.getCelestialBodyType());
        }
        return list;
    }
    //Returns the list of number of objects as an arraylist of Integer's
    public static ArrayList<Integer> getListofCounts(ArrayList<CelestialBody> sortedData) {
        ArrayList<Integer> list = new ArrayList<>();
        CelestialBody x;
        for (int i=0; i<sortedData.size(); i++){
            x= sortedData.get(i);
            list.add(x.getKnownCount());
        }
        return list;
    }

    //We might never use it but might use it to print the result by changing the way data is printed
    public static void printData(ArrayList<CelestialBody> sortedData) {
        CelestialBody x;
        for (int i=0; i<sortedData.size(); i++){
            x= sortedData.get(i);
            System.out.println("The total number of " + x.getCelestialBodyType() + " known by " + x.getUpdatedDate() + " is " + x.getKnownCount() + ".");
        }
    }
    // EXAMPLE OUTPUT OF printData(SpaceAPI.getSortedData(SpaceAPI.getSpaceData()));
    //The total number of planet known by 24/08/2006 is 8.
    //The total number of dwarfPlanet known by 24/08/2006 is 5.
    //The total number of asteroid known by 21/11/2021 is 1113527.
    //The total number of comet known by 08/08/2021 is 3743.
    //The total number of moonsPlanet known by 01/08/2023 is 285.
    //The total number of moonsDwarfPlanet known by 02/05/2016 is 9.
    //The total number of moonsAsteroid known by 21/11/2021 is 558.

    // Possible prediction format?
    // I predict that the number of known ______ will increase by ______.
    // Ex. I predict that the number of comets will grow by 12/25/2023.
    // Store the prediction and then check each day or on the end date if number of comets increased?
}