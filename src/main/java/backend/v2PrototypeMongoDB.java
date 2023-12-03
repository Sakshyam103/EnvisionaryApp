package backend;

import backend.CelestialBodyPredictions.CelestialBodyPredictionInitializer;
import backend.CelestialBodyPredictions.CelestialBodyUpdater;
import backend.CelestialBodyPredictions.MongoDBCelestialBodyData;
import backend.CustomPredictions.CustomPredictionInitializer;
import backend.FootballMatchPredictions.FootballMatchPredictionInitializer;
import backend.FootballMatchPredictions.FootballMatchUpdater;
import backend.FootballMatchPredictions.FootballTeamInitializer;
import backend.FootballMatchPredictions.MongoDBFootballMatchData;
import backend.Notifications.NotificationUpdater;
import backend.OverallStatistics.MongoDBOverallDescriptiveStatistics;
import backend.OverallStatistics.MongoDBOverallInferentialStatistics;
import backend.OverallStatistics.OverallDescriptiveStatisticsUpdater;
import backend.OverallStatistics.OverallInferentialStatisticsUpdater;
import backend.ResolvedPredictions.ResolvedPredictionInitializer;
import backend.UserInfo.EnvisionaryUser;
import backend.UserInfo.MongoDBEnvisionaryUsers;
import backend.UserInfo.UserInitializer;
import backend.UserStatistics.UserDescriptiveStatisticsUpdater;
import backend.UserStatistics.UserInferentialStatisticsUpdater;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
//														 INSTRUCTIONS
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
//
// 1. Run the BackgroundUpdaters.java to initialize the FootballMatchPredictions.FootballTeam and FootballMatchPredictions.FootballMatch files with data from the football-data.org API
//		- Example output to wait for:
//		        Updating FootballMatchPredictions.FootballTeam files...
//              FootballMatchPredictions.FootballTeam files have been updated at: 2023-10-31T18:14:22.833423300-04:00[America/New_York]
//              Updating FootballMatchPredictions.FootballMatch and FootballMatchPredictions.FootballMatchPrediction files...
//              FootballMatchPredictions.FootballMatch files and FootballMatchPredictions.FootballMatchPrediction files have been updated at: 2023-10-31T18:14:32.569676-04:00[America/New_York]
//
// 2. Run the v2Prototype
//
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
//															NOTES:
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
//													Creates Folder Path:
//
//
//                                         /-> [CustomPredictions] -> ...
//                                        /
//                                       /---> [Notifications] -> ...
//                                      /
//                                     /-----> [OverallStatistics] -> ...
//                                    /
//   (UserHome) ---> [EnvisionaryApp] -------> [ResolvedPredictions] -> ...                          /-> [FootballMatchData] -> ...
//                                    \                                                             /
//                                     \-----> [SportsPredictions] ---------> [FootballPredictions] ---> [FootballMatchPredictions] -> ...
//                                      \                                                           \
//                                       \---> [Users] -> ...                                        \-> [FootballTeamData] -> ...
//                                        \
//                                         \-> [UserStatistics] ---> [Descriptive] -> ...
//                                                              \
//                                                               \-> [Inferential] -> ...
//
//
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// 														  TODO Fixes:
//	FIXED - Added break; before Case8 switch case begins
//	After inputting 7 on the Football Match BasePredictionsObject.Prediction Menu (Make new prediction on tomorrow's matches), the match prediction
//	process completes and then asks "Would you like to make a prediction on the upcoming week's games? (Y/N)" as if there was
//	an 8 input somewhere even though there was not. Responding with an 'n' to this brings you back to the main menu.
//
//	FIXED - Removed line "scan.close();" at the end of FootballMatchPredictions.FootballMatchPredictionInitializer.removeFootballMatchPrediction() function
//	After inputting 6 on the Main Menu (Remove football match prediction), selecting the match, and selecting 'n' to the
//	confirmation, the program outputs, "BasePredictionsObject.Prediction removal canceled." and re-displays the Main Menu and Selection: before giving
//	a NoSuchElementException at v1Prototype.userMenu(98) and v1Prototype.main(245) and terminating.
//	Exception does NOT occur when doing the same process with remove custom match prediction.
//
//	FIXED - Added break; before Case8 switch case begins
//	Program is outputting "THANK YOU FOR USING THE ENVISIONARY PROTOTYPE APPLICATION! Shutting program down..." after selection 7
//	process is complete.
//
//  FIXED - Added initialization of menu selection to 0 before the output of the menu to the console
//  Selecting the main menu option 2 (Football Match BasePredictionsObject.Prediction menu) and then selecting 12 (Return to main menu), upon selection
//  of 2 again (Football Match BasePredictionsObject.Prediction menu), the main menu displays again instead of the football match prediction menu.
//
//  FIXED - Added "Scanner scan = new Scanner(System.in);" line to start of CustomPredictions.CustomPredictionInitializer.getUserCustomPrediction
//  Selecting option 1 (Make new custom prediction) to create a prediction and then selecting option 1 again after the creation
//  skips the first "Please enter your choice of sub-category..." as if the enter key press is lingering until the next scan.next.
//
//  FIXED - Changed output of error message within OverallStatisticsUpdater.loadAndDisplayOverallStatistics to "OVERALL STATISTICS FILE DOES NOT EXIST"
//  Error message "ERROR - USER RESOLVED PREDICTION FILE DOES NOT EXIST FOR USER: TestUser4 - SUCCESSFULLY RESOLVE A PREDICTION TO INITIALIZE"
//  comes up twice when a new user enters 7 on the main menu (Calculate user statistics).
//
//  FIXED - Erased all file paths and reinitialized all file paths. Corrected all .ser to .json within file path initializations.
//  On initial launch after running BackgroundUpdaters.java:
//	- Main Menu 3 -> Exception in thread "main" java.lang.IllegalArgumentException: NaN is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.
//	- Main Menu 10 ->  Exception in thread "main" java.lang.IllegalArgumentException: NaN is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.
//
//  FIXED - Changed line "Gson gson2 = new Gson();" to "Gson gson2 = gsonBuilder.create();" within file save section of function
//  Trying to initialize and save overall inferential statistics, Error message: Exception in thread "main" java.lang.IllegalArgumentException:
//  NaN is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.
//
//  Fixed - added conversion to String using decimal format df and reassigned each double after within OverallStatisticsUpdater
//  -----Overall Percent of Category Predictions Resolved out of Overall Predictions-----
//  Custom: 75.40983606557377%
//  Sports: 24.59016393442623%
//  Science: 0.0%
//  Weather: 0.0%
//  Entertainment: 0.0%
//
//  FIXED - Fixed logic within mode calculation to be NaN if there are equal
//  Mode shows as 28.57% (TestUser2's Percent Correct) as TestUser2 when there should be no mode to the data
//
//  FIXED - Make mode an array list of doubles in case there are multiple modes and fix the logic
//  Tested with cases of all elements occurring once ( [NaN]% ), 1 mode( [33.33]% ), and 2 modes ( [40.0, 33.33]% ).
//
//  FIXED - Added logic to print method of UserStatistics.UserInferentialStatistics to display different output if best and worst categories are the equal, else output the user's recommended categories to use and not use.
//  With only Custom predictions resolved, the userInferentialStatistics is outputting:
//  Based off of your statistical results:
//  You are best at accurately predicting Custom type predictions.
//  You should avoid making Custom type predictions to improve your overall correct predictions.
//
//  FIXED - Made changes to logic to set NaN for values that don't exist yet. Sets max values to -7.7% and min values to 777% to initialize and if still initial value after calculation, sets to NaN.
//  Change userInferentialStatistics to display NaN values for prediction types that have zero predictions made instead of 0.0%
//  -----Percent of Correct Predictions per Category-----
//  Custom: 71.43%
//  Sports: 76.92%
//  Science: 0.0%
//  Weather: 0.0%
//  Entertainment: 0.0%
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// v2PrototypeMongoDB - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Prototype of the Envisionary prediction web application working locally by saving to and loading
// from JSON files containing the data needed by the application. Uses BackgroundUpdaters.java to
// initialize the FootballMatchPredictions.FootballTeam and FootballMatchPredictions.FootballMatch data needed to create new FootballMatchPredictions
// and calls FootballMatchPredictions.FootballMatchPredictionUpdater to resolve and remove FootballMatchPredictions made by
// Envisionary app users automatically every X-minutes (Set within BackgroundUpdaters.java).
// Provides a rough overall representation of the application's functionality, limited to the console
// of the IDE running the program. When either BackgroundUpdaters or v2Prototype are run, the folder/file
// paths needed for the program to run will be initialized if they do not already exist. This folder
// can be found at UserHome > EnvisionaryApp and can be deleted at any time to erase the entire
// existing local "database" of the prototype Envisionary application.
//
public class v2PrototypeMongoDB {
    // v2Prototype Variables
    public static Scanner scan = new Scanner(System.in);
    public static String userID;
    public static String password;
    public static String email;
    public static int loginMenuSelection = 0;
    public static int userMenuSelection = 0;
    public static int customPredictionMenuSelection = 0;
    public static int celestialBodyPredictionMenuSelection = 0;
    public static int footballPredictionMenuSelection = 0;
    public static int footballMatchMenuSelection = 0;
    public static int statisticsMenuSelection = 0;
    public static String userFilePath = System.getProperty("user.home") + File.separator + "EnvisionaryApp" + File.separator + "Users" + File.separator + "user_data.json";

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // main
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Initializes all file paths and enters the loginMenu to get user input to either login using an
    // existing Envisionary user account, create a new Envisionary user account, or exit the application.
    //
    public static void main(String[] args) {
        // Initialize all file paths
        initializeFilePaths();

        // Login
        loginMenu();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // initializeFilePaths
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Initializes all the file path and folders needed to save the FootballMatchPredictions.FootballTeam file, FootballMatchPredictions.FootballMatch files,
    // CustomPredictions.CustomPrediction files, FootballMatchPredictions.FootballMatchPrediction files, ResolvedPredictions.ResolvedPrediction files,
    // UserStatistics.UserDescriptiveStatistics files, UserStatistics.UserInferentialStatistics files, OverallStatistics.OverallDescriptiveStatistics file,
    // OverallStatistics.OverallInferentialStatistics file, Notifications.Notification files, and the UserInfo.User file.
    //
    public static void initializeFilePaths() {
        FootballMatchUpdater.initializeFilePath();
        FootballTeamInitializer.initializeFilePath();
        CustomPredictionInitializer.initializeFilePath();
        FootballMatchPredictionInitializer.initializeFilePath();
        ResolvedPredictionInitializer.initializeFilePath();
        UserDescriptiveStatisticsUpdater.initializeFilePath();
        UserInferentialStatisticsUpdater.initializeFilePath();
        OverallDescriptiveStatisticsUpdater.initializeFilePath();
        OverallInferentialStatisticsUpdater.initializeFilePath();
        NotificationUpdater.initializeFilePath();
        UserInitializer.initializeFilePath();
        CelestialBodyUpdater.initializeFilePath();
        CelestialBodyPredictionInitializer.initializeFilePath();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // loginMenu
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Gathers input from the user to either login as an existing Envisionary user, create a new
    // Envisionary user account, or close the application.
    //
    public static void loginMenu() {
        loginMenuSelection = 0;
        while (loginMenuSelection != 3) {
            System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════╗\n" +
                    "║                                                                                                  ║\n" +
                    "║                             WELCOME TO THE ENVISIONARY APP PROTOTYPE                             ║\n" +
                    "║                                                                                                  ║\n" +
                    "╠══════════════════════════════════════════════════════════════════════════════════════════════════╣\n" +
                    "║                                                                                                  ║\n" +
                    "║                                                                                                  ║\n" +
                    "║                             1. Existing Envisionary user login                                   ║\n" +
                    "║                             2. Create a new Envisionary user account                             ║\n" +
                    "║                             3. Close application                                                 ║\n" +
                    "║                                                                                                  ║\n" +
                    "║                                                                                                  ║\n" +
                    "╚══════════════════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.print(  "                                        Selection: ");

            // Menu logic
            try {
                int input = scan.nextInt();

                if (input >= 1 && input <= 3) {
                    loginMenuSelection = input;
                    switch (loginMenuSelection) {
                        case 1:
                            File userFilesFolder = new File(userFilePath);
                            if (userFilesFolder.exists()) {
                                login();
                            } else {
                                System.out.println("ERROR - UserInfo.User files folder does not exist. Create the first Envisionary user to initialize!");
                            }
                            loginMenuSelection = 0;
                            break;
                        case 2:
                            createNewAccount();
                            loginMenuSelection = 0;
                            break;
                        case 3:
                            System.out.println("                 THANK YOU FOR USING THE ENVISIONARY PROTOTYPE APPLICATION!\n"
                                    + "                                  Shutting program down...");
                            System.exit(0);
                    }
                } else {
                    System.out.println("Invalid selection. Please enter a valid menu option (1-3).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu option (1-3).");
                scan.next(); // Clear the invalid input
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // login
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Gathers input from the user to login as an existing Envisionary user. If a valid UserInfo.User is found
    // within the UserInfo.User file, the application will go to the userMenu using the entered UserID as the
    // userIdentifier for saving and loading the user's prediction, notifications, and statistics JSON
    // files.
    //
    public static void login() {
        boolean isValidUser = false;
        while (!isValidUser) {
            userID = "";
            password = "";
            System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════╗\n" +
                    "║                                              LOGIN                                               ║\n" +
                    "╠══════════════════════════════════════════════════════════════════════════════════════════════════╣\n" +
                    "║                                                                                                  ║\n" +
                    "║                               Please enter your UserID                                           ║\n" +
                    "║                         Enter \"CANCEL\" to return back to the home screen                         ║\n" +
                    "║                                                                                                  ║\n" +
                    "╚══════════════════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.print(  "                                     UserID: ");
            userID = scan.next();
            if (userID.equalsIgnoreCase("CANCEL")){
                return;
            }
            isValidUser = UserInitializer.loginMongoDB(userID);
        }
        userMenu();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewAccount
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Gathers input from the user to create a new Envisionary user account or cancels the process at any
    // point by entering "CANCEL", ignoring the case of the String.
    //
    public static void createNewAccount() {
        boolean validUserID = false;
        boolean validPassword = false;
        boolean validEmail = false;
        while (!validUserID && !validPassword && !validEmail) {
            validUserID = false;
            validPassword = false;
            validEmail = false;
            userID = "";
            password = "";
            email = "";
            System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════╗\n" +
                    "║                                       NEW ENVISIONARY USER                                       ║\n" +
                    "╠══════════════════════════════════════════════════════════════════════════════════════════════════╣\n" +
                    "║                                                                                                  ║\n" +
                    "║                     Please enter your desired UserID and Email                                   ║\n" +
                    "║                        Enter \"CANCEL\" to return back to the home screen                          ║\n" +
                    "║                                                                                                  ║\n" +
                    "╚══════════════════════════════════════════════════════════════════════════════════════════════════╝");
            while (!validUserID) {
                System.out.println("                              Enter your choice of UserID:\n" +
                        "                         UserID must be 7 or more characters long.\n" +
                        "                  Restricted to letters and numbers. Enter \"CANCEL\" to cancel.");
                System.out.print("                                     UserID: ");
                userID = scan.next();
                if (userID.equalsIgnoreCase("CANCEL")) {
                    return;
                }
                validUserID = UserInitializer.isUserIdValidMongoDB(userID);
            }
            while (!validEmail) {
                System.out.println("                           Enter your email address:\n" +
                        "                      Email must be of a valid email format.");
                System.out.print("                                     Email: ");
                email = scan.next();
                if (email.equalsIgnoreCase("CANCEL")) {
                    return;
                }
                validEmail = UserInitializer.isEmailValidMongoDB(email);
                if (!validEmail) {
                    System.out.println("ERROR - Email not valid.");
                }
            }
        }
        UserInitializer.createNewAccountMongoDB(userID, email);
        System.out.println("New Envisionary user successfully created. Login using your account credentials.");
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // userMenu
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Gathers input from the user to either open the custom prediction menu, open the football match
    // prediction menu, view all active predictions, view all resolved predictions, view notifications,
    // remove notifications, remove all notifications, open the statistics menu, or log out.
    //
    public static void userMenu() {
        userMenuSelection = 0;
        while (userMenuSelection != 10) {
            System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════╗\n" +
                    "║                                                                                                  ║\n" +
                    "║                                            USER MENU                                             ║\n" +
                    "║                                                                                                  ║\n" +
                    "╠══════════════════════════════════════════════════════════════════════════════════════════════════╣\n" +
                    "║                                                                                                  ║\n" +
                    "║                                                                                                  ║\n" +
                    "║                                   1. Custom prediction menu                                      ║\n" +
                    "║                                   2. Celestial body prediction menu                              ║\n" +
                    "║                                   3. Football match prediction menu                              ║\n" +
                    "║                                   4. View all active predictions                                 ║\n" +
                    "║                                   5. View all resolved predictions                               ║\n" +
                    "║                                   6. View notifications                                          ║\n" +
                    "║                                   7. Remove notifications                                        ║\n" +
                    "║                                   8. Remove all notifications                                    ║\n" +
                    "║                                   9. Statistics menu                                             ║\n" +
                    "║                                  10. Log out                                                     ║\n" +
                    "║                                                                                                  ║\n" +
                    "║                                                                                                  ║\n" +
                    "╚══════════════════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.print(  "                                        Selection: ");

            // Menu logic
            try {
                int input = scan.nextInt();

                if (input >= 1 && input <= 10) {
                    userMenuSelection = input;
                    switch (userMenuSelection) {
                        case 1:
                            customPredictionMenu();
                            userMenuSelection = 0;
                            break;
                        case 2:
                            celestialBodyPredictionMenu();
                            userMenuSelection = 0;
                            break;
                        case 3:
                            footballMatchPredictionMenu();
                            userMenuSelection = 0;
                            break;
                        case 4:
                            MongoDBEnvisionaryUsers.retrieveAndDisplayCustomPredictionsForUser(userID);
                            MongoDBEnvisionaryUsers.retrieveAndDisplayCelestialBodyPredictionsForUser(userID);
                            //MongoDBEnvisionaryUsers.retrieveAndDisplayEntertainmentPredictionsForUser(userID);
                            MongoDBEnvisionaryUsers.retrieveAndDisplayFootballMatchPredictionsForUser(userID);
                            MongoDBEnvisionaryUsers.retrieveAndDisplayWeatherPredictionsForUser(userID);
                            userMenuSelection = 0;
                            break;
                        case 5:
                            MongoDBEnvisionaryUsers.retrieveAndDisplayResolvedPredictionsForUser(userID);
                            userMenuSelection = 0;
                            break;
                        case 6:
                            MongoDBEnvisionaryUsers.retrieveAndDisplayNotificationsForUser(userID);
                            userMenuSelection = 0;
                            break;
                        case 7:
                            NotificationUpdater.removeUserNotificationMongoDB(userID);
                            userMenuSelection = 0;
                            break;
                        case 8:
                            NotificationUpdater.removeAllUserNotificationsMongoDB(userID);
                            userMenuSelection = 0;
                            break;
                        case 9:
                            statisticsMenu();
                            userMenuSelection = 0;
                            break;
                        case 10:
                            System.out.println("                                  Successful log out...");
                            break;
                    }
                } else {
                    System.out.println("Invalid selection. Please enter a valid menu option (1-10).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu option (1-10).");
                scan.next(); // Clear the invalid input
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // customPredictionMenu
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Gathers input from the user to either create a new custom prediction, remove a custom prediction,
    // resolve a custom prediction, view all active custom predictions, view all resolved custom
    // predictions, or return to the main menu.
    //
    public static void customPredictionMenu() {
        customPredictionMenuSelection = 0;
        while (customPredictionMenuSelection != 6) {
            System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════╗\n" +
                    "║                                                                                                  ║\n" +
                    "║                                      CUSTOM PREDICTION MENU                                      ║\n" +
                    "║                                                                                                  ║\n" +
                    "╠══════════════════════════════════════════════════════════════════════════════════════════════════╣\n" +
                    "║                                                                                                  ║\n" +
                    "║                                                                                                  ║\n" +
                    "║                                 1. Create new custom prediction                                  ║\n" +
                    "║                                 2. Remove custom prediction                                      ║\n" +
                    "║                                 3. Resolve custom prediction                                     ║\n" +
                    "║                                 4. View all active custom predictions                            ║\n" +
                    "║                                 5. View all resolved custom predictions                          ║\n" +
                    "║                                 6. Return to main menu                                           ║\n" +
                    "║                                                                                                  ║\n" +
                    "║                                                                                                  ║\n" +
                    "╚══════════════════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.print(  "                                        Selection: ");

            // Menu logic
            try {
                int input = scan.nextInt();

                if (input >= 1 && input <= 6) {
                    customPredictionMenuSelection = input;
                    switch (customPredictionMenuSelection) {
                        case 1:
                            CustomPredictionInitializer.createNewCustomPredictionMongoDB(userID);
                            customPredictionMenuSelection = 0;
                            break;
                        case 2:
                            CustomPredictionInitializer.removeCustomPredictionMongoDB(userID);
                            customPredictionMenuSelection = 0;
                            break;
                        case 3:
                            CustomPredictionInitializer.resolveCustomPredictionMongoDB(userID);
                            customPredictionMenuSelection = 0;
                            break;
                        case 4:
                            MongoDBEnvisionaryUsers.retrieveAndDisplayCustomPredictionsForUser(userID);
                            customPredictionMenuSelection = 0;
                            break;
                        case 5:
                            ResolvedPredictionInitializer.printResolvedCustomPredictionsMongoDB(userID);
                            customPredictionMenuSelection = 0;
                            break;
                        case 6:
                            System.out.println("Returning back to main menu...");
                            break;
                    }
                } else {
                    System.out.println("Invalid selection. Please enter a valid menu option (1-6).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu option (1-6).");
                scan.next(); // Clear the invalid input
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // celestialBodyPredictionMenu
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    //
    public static void celestialBodyPredictionMenu() {
        celestialBodyPredictionMenuSelection = 0;
        while(celestialBodyPredictionMenuSelection != 6) {
            System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════╗\n" +
                    "║                                                                                                  ║\n" +
                    "║                                 CELESTIAL BODY PREDICTION MENU                                   ║\n" +
                    "║                                                                                                  ║\n" +
                    "╠══════════════════════════════════════════════════════════════════════════════════════════════════╣\n" +
                    "║                                                                                                  ║\n" +
                    "║                                                                                                  ║\n" +
                    "║                     1. View current celestial body data                                          ║\n" +
                    "║                     2. Make new prediction on a change in the number of celestial bodies         ║\n" +
                    "║                     3. Remove celestial body prediction                                          ║\n" +
                    "║                     4. View active celestial body predictions                                    ║\n" +
                    "║                     5. View resolved celestial body predictions                                  ║\n" +
                    "║                     6. Return to main menu                                                       ║\n" +
                    "║                                                                                                  ║\n" +
                    "║                                                                                                  ║\n" +
                    "╚══════════════════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.print(  "                                    Selection: ");
            // Menu logic
            try {
                int input = scan.nextInt();

                if (input >= 1 && input <= 6) {
                    celestialBodyPredictionMenuSelection = input;
                    switch (celestialBodyPredictionMenuSelection) {
                        case 1:
                            MongoDBCelestialBodyData.retrieveCollectionAndDisplay();
                            celestialBodyPredictionMenuSelection = 0;
                            break;
                        case 2:
                            CelestialBodyPredictionInitializer.createNewCelestialBodyPredictionMongoDB(userID);
                            celestialBodyPredictionMenuSelection = 0;
                            break;
                        case 3:
                            CelestialBodyPredictionInitializer.removeCelestialBodyPredictionMongoDB(userID);
                            celestialBodyPredictionMenuSelection = 0;
                            break;
                        case 4:
                            MongoDBEnvisionaryUsers.retrieveAndDisplayCelestialBodyPredictionsForUser(userID);
                            celestialBodyPredictionMenuSelection = 0;
                            break;
                        case 5:
                            ResolvedPredictionInitializer.printResolvedSciencePredictionsMongoDB(userID);
                            celestialBodyPredictionMenuSelection = 0;
                            break;
                        case 6:
                            System.out.println("Returning back to main menu...");
                            break;
                    }
                } else {
                    System.out.println("Invalid selection. Please enter a valid menu option (1-6).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu option (1-6).");
                scan.next(); // Clear the invalid input
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // footballMatchPredictionMenu
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Gathers input from the user to either open the football match menu, make a new prediction on
    // tomorrow's football matches, make a new prediction on the upcoming week's football matches, make
    // a prediction on the second upcoming week's football matches, make a prediction on the third upcoming
    // week's football matches, remove a football match prediction, view active football match predictions,
    // view resolved football match predictions, or return to the main menu.
    //
    public static void footballMatchPredictionMenu() {
        footballPredictionMenuSelection = 0;
        while(footballPredictionMenuSelection != 9) {
            System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════╗\n" +
                    "║                                                                                                  ║\n" +
                    "║                                 FOOTBALL MATCH PREDICTION MENU                                   ║\n" +
                    "║                                                                                                  ║\n" +
                    "╠══════════════════════════════════════════════════════════════════════════════════════════════════╣\n" +
                    "║                                                                                                  ║\n" +
                    "║                                                                                                  ║\n" +
                    "║                     1. Football match menu (View current football match data)                    ║\n" +
                    "║                     2. Make new prediction on tomorrow's matches                                 ║\n" +
                    "║                     3. Make new prediction on upcoming week's matches                            ║\n" +
                    "║                     4. Make new prediction on second upcoming week's matches                     ║\n" +
                    "║                     5. Make new prediction on third upcoming week's matches                      ║\n" +
                    "║                     6. Remove football match prediction                                          ║\n" +
                    "║                     7. View active football match predictions                                    ║\n" +
                    "║                     8. View resolved football match predictions                                  ║\n" +
                    "║                     9. Return to main menu                                                       ║\n" +
                    "║                                                                                                  ║\n" +
                    "║                                                                                                  ║\n" +
                    "╚══════════════════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.print(  "                                    Selection: ");
            // Menu logic
            try {
                int input = scan.nextInt();

                if (input >= 1 && input <= 9) {
                    footballPredictionMenuSelection = input;
                    switch (footballPredictionMenuSelection) {
                        case 1:
                            footballMatchMenu();
                            footballPredictionMenuSelection = 0;
                            break;
                        case 2:
                            FootballMatchPredictionInitializer.createNewFootballMatchTomorrowPredictionMongoDB(userID);        // TEST USING YESTERDAY'S MATCHES
                            footballPredictionMenuSelection = 0;
                            break;
                        case 3:
                            FootballMatchPredictionInitializer.createNewFootballMatchUpcomingWeek1PredictionMongoDB(userID);
                            footballPredictionMenuSelection = 0;
                            break;
                        case 4:
                            FootballMatchPredictionInitializer.createNewFootballMatchUpcomingWeek2PredictionMongoDB(userID);
                            footballPredictionMenuSelection = 0;
                            break;
                        case 5:
                            FootballMatchPredictionInitializer.createNewFootballMatchUpcomingWeek3PredictionMongoDB(userID);
                            footballPredictionMenuSelection = 0;
                            break;
                        case 6:
                            FootballMatchPredictionInitializer.removeFootballMatchPredictionMongoDB(userID);
                            footballPredictionMenuSelection = 0;
                            break;
                        case 7:
                            FootballMatchPredictionInitializer.printAllFootballMatchPredictionsMongoDB(userID);
                            footballPredictionMenuSelection = 0;
                            break;
                        case 8:
                            ResolvedPredictionInitializer.printResolvedFootballMatchPredictionsMongoDB(userID);
                            footballPredictionMenuSelection = 0;
                            break;
                        case 9:
                            System.out.println("Returning back to main menu...");
                            break;
                    }
                } else {
                    System.out.println("Invalid selection. Please enter a valid menu option (1-9).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu option (1-9).");
                scan.next(); // Clear the invalid input
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // footballMatchMenu
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Gathers input from the user to either view yesterday's football matches, view today's football
    // matches, view tomorrow's football matches, view the upcoming week's football matches, view the
    // second upcoming week's football matches, make a prediction on the third upcoming week's football
    // matches, or return to the football match prediction menu.
    //
    public static void footballMatchMenu() {
        footballMatchMenuSelection = 0;
        while(footballMatchMenuSelection != 7) {
            System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════╗\n" +
                    "║                                                                                                  ║\n" +
                    "║                                       FOOTBALL MATCH MENU                                        ║\n" +
                    "║                                                                                                  ║\n" +
                    "╠══════════════════════════════════════════════════════════════════════════════════════════════════╣\n" +
                    "║                                                                                                  ║\n" +
                    "║                                                                                                  ║\n" +
                    "║                             1. View yesterday's football matches                                 ║\n" +
                    "║                             2. View today's football matches                                     ║\n" +
                    "║                             3. View tomorrow's football matches                                  ║\n" +
                    "║                             4. View upcoming week's football matches                             ║\n" +
                    "║                             5. View second upcoming week's football matches                      ║\n" +
                    "║                             6. View third upcoming week's football matches                       ║\n" +
                    "║                             7. Return to football match prediction menu                          ║\n" +
                    "║                                                                                                  ║\n" +
                    "║                                                                                                  ║\n" +
                    "╚══════════════════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.print(  "                                    Selection: ");
            // Menu logic
            try {
                int input = scan.nextInt();

                if (input >= 1 && input <= 7) {
                    footballMatchMenuSelection = input;
                    switch (footballMatchMenuSelection) {
                        case 1:
                            MongoDBFootballMatchData.retrieveCollectionTimeFrameAndDisplay("Yesterday");
                            footballMatchMenuSelection = 0;
                            break;
                        case 2:
                            MongoDBFootballMatchData.retrieveCollectionTimeFrameAndDisplay("Today");
                            footballMatchMenuSelection = 0;
                            break;
                        case 3:
                            MongoDBFootballMatchData.retrieveCollectionTimeFrameAndDisplay("Tomorrow");
                            footballMatchMenuSelection = 0;
                            break;
                        case 4:
                            MongoDBFootballMatchData.retrieveCollectionTimeFrameAndDisplay("UpcomingWeek1");
                            footballMatchMenuSelection = 0;
                            break;
                        case 5:
                            MongoDBFootballMatchData.retrieveCollectionTimeFrameAndDisplay("UpcomingWeek2");
                            footballMatchMenuSelection = 0;
                            break;
                        case 6:
                            MongoDBFootballMatchData.retrieveCollectionTimeFrameAndDisplay("UpcomingWeek3");
                            footballMatchMenuSelection = 0;
                            break;
                        case 7:
                            System.out.println("Returning back to football match prediction menu...");
                            break;
                    }
                } else {
                    System.out.println("Invalid selection. Please enter a valid menu option (1-7).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu option (1-7).");
                scan.next(); // Clear the invalid input
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // statisticsMenu
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Gathers input from the user to either view the user's descriptive statistics, view the user's
    // inferential statistics, view the overall descriptive statistics, view the overall inferential
    // statistics, or return to the main menu.
    //
    public static void statisticsMenu() {
        statisticsMenuSelection = 0;
        while (statisticsMenuSelection != 5) {
            System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════╗\n" +
                    "║                                                                                                  ║\n" +
                    "║                                         STATISTICS MENU                                          ║\n" +
                    "║                                                                                                  ║\n" +
                    "╠══════════════════════════════════════════════════════════════════════════════════════════════════╣\n" +
                    "║                                                                                                  ║\n" +
                    "║                                                                                                  ║\n" +
                    "║                               1. View user descriptive statistics                                ║\n" +
                    "║                               2. View user inferential statistics                                ║\n" +
                    "║                               3. View overall descriptive statistics                             ║\n" +
                    "║                               4. View overall inferential statistics                             ║\n" +
                    "║                               5. Return to main menu                                             ║\n" +
                    "║                                                                                                  ║\n" +
                    "║                                                                                                  ║\n" +
                    "╚══════════════════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.print(  "                                        Selection: ");

            // Menu logic
            try {
                int input = scan.nextInt();

                if (input >= 1 && input <= 5) {
                    statisticsMenuSelection = input;
                    switch (statisticsMenuSelection) {
                        case 1:
                            MongoDBEnvisionaryUsers.retrieveUserDescriptiveStatistics(userID);
                            statisticsMenuSelection = 0;
                            break;
                        case 2:
                            MongoDBEnvisionaryUsers.retrieveUserInferentialStatistics(userID);
                            statisticsMenuSelection = 0;
                            break;
                        case 3:
                            MongoDBOverallDescriptiveStatistics.retrieveCollectionAndDisplay();
                            statisticsMenuSelection = 0;
                            break;
                        case 4:
                            MongoDBOverallInferentialStatistics.retrieveCollectionAndDisplay();
                            statisticsMenuSelection = 0;
                            break;
                        case 5:
                            System.out.println("Returning back to main menu...");
                            break;
                    }
                } else {
                    System.out.println("Invalid selection. Please enter a valid menu option (1-5).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu option (1-5).");
                scan.next(); // Clear the invalid input
            }
        }
    }
}