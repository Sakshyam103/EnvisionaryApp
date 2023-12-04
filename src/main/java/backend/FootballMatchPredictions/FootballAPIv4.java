package backend.FootballMatchPredictions;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
//									NOTES:
//
//  When trying to call more than 10 requests per minute:
//
//  java.io.IOException: Failed to fetch data from the API. Response code: 429
//	at FootballAPIv4.fetchData(FootballAPIv4.java:89)
//	at FootballAPIv4.main(FootballAPIv4.java:125)
//
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
//									API:
//	Free account:
//	10 requests / minute
//	12 competitions
//	Scores delayed
//	Fixtures, Schedules
//	League Tables
//
//	API token: a1656790c3c4408593f44ac696c80b47
//
//	Example Requests for v4 API:
//
//	See today's matches of your subscribed competitions:
//	https://api.football-data.org/v4/matches
//
//	Get all matches of the Champions League:
//	https://api.football-data.org/v4/competitions/CL/matches
//
//	See all upcoming matches for Real Madrid:
//	https://api.football-data.org/v4/teams/86/matches?status=SCHEDULED
//
//	Get all matches where Gigi Buffon was in the squad:
//	https://api.football-data.org/v4/persons/2019/matches?status=FINISHED
//
//	Check schedules for Premier League on matchday 11:
//	https://api.football-data.org/v4/competitions/PL/matches?matchday=11
//
//	Get the league table for Eredivisie:
//	https://api.football-data.org/v4/competitions/DED/standings
//
//	See best 10 scorers of Italy's top league (scorers subresource defaults to limit=10):
//	https://api.football-data.org/v4/competitions/SA/scorers
//
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
//
//	Plain request:
//
// Within Java 11 there (finally) is a pretty plain HttpClient on board, so we can just jump in to the JShell,
// import the needed classes and fire up our request
//
//	HttpClient client = HttpClient.newHttpClient();
//	HttpRequest request = HttpRequest.newBuilder()
//		.uri(URI.create("https://api.football-data.org/v4/competitions"))
//		.build();
//
//	HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
//	System.out.println(response.statusCode());
//	System.out.println(response.body());
//
//	Adding a header to pass the authentication for other resources to use is easy.
//	Use the code below to add authentication to be able to use a restricted resource:
//
//	HttpRequest request = HttpRequest.newBuilder()
//		.uri(URI.create("https://api.football-data.org/v4/competitions/PL/matches?matchday=28"))
//		.header("X-Auth-Token", "UR_TOKEN")
//		.build();
//
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// FootballAPIv4 class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Gets requests from the football-data.org API using the final String API_KEY and a request URL line
// to finish the BASE_URL final String.
//
public class FootballAPIv4 {
    private static final String API_KEY = "a1656790c3c4408593f44ac696c80b47";		// API authorization key
    private static final String BASE_URL = "https://api.football-data.org/v4/";		// Base URL used for API Data Access

    public static String fetchData(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-Auth-Token", API_KEY)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new IOException("Failed to fetch data from the API. Response code: " + response.statusCode());
        }
    }
}