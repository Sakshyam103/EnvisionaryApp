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
}