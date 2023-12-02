//package backend.EntertainmentPredictions;
//
//import java.io.InputStream;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import javax.json.Json;
//import javax.json.JsonArray;
//import javax.json.JsonArrayBuilder;
//import javax.json.JsonObject;
//import javax.json.JsonReader;
//import javax.json.JsonReaderFactory;
//
//
//public class movieAPI{
//
//
//    public static JsonArray connectToMovie(){
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://streaming-availability.p.rapidapi.com/search/title?title=" + runEntertainment.movieTitle +"&country=us&show_type=all&output_language=en"))
//                .header("X-RapidAPI-Key", "9807f0692amsh360071520bd07f5p1528bejsn9375a7f9f3fd")
//                .header("X-RapidAPI-Host", "streaming-availability.p.rapidapi.com")
//                .method("GET", HttpRequest.BodyPublishers.noBody())
//                .build();
//        try
//        {
//            HttpResponse<InputStream> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofInputStream());
//            JsonReaderFactory readerFactory = Json.createReaderFactory(null);
//            JsonReader reader = readerFactory.createReader(response.body());
//            JsonArrayBuilder builder = Json.createArrayBuilder();
//            JsonObject object = reader.readObject();
//            JsonArray array = object.getJsonArray("result");
//            builder.add(array);
//            builder.build();
//            reader.close();
//            if(array == null){
//                movieNotFound();
//            }
//            return array;
//        }
//        catch (Exception ex)
//        {
//            System.out.println(ex);
//            return null;
//        }
//
//
//    }
//
//    // test case for movie not found
//    // array is null, therefore api returned nothing
//    private static void movieNotFound(){
//        System.out.println("Sorry but we could not find that movie \n Please try again");
//        runEntertainment.runEntertainmentPrediction();
//    }
//}
