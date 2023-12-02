package backend.UserInfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.Scanner;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// UserInfo.UserInitializer class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Initializes the file path and folders needed to save the user data.
//
public class UserInitializer {
    // UserInfo.UserInitializer Class Constants & Variables
    private static final String userHome = System.getProperty("user.home");
    private static final String userFolderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "Users";

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // initializeFilePath
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Initializes the file path and folders needed to save the users to the user file.
    //
    public static void initializeFilePath() {
        // Create the directory if it doesn't exist
        File directory = new File(userFolderPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Created directory: " + userFolderPath);
            } else {
                System.err.println("ERROR - Failed to create directory: " + userFolderPath);
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // getUserInput
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Gathers input from the user to create a new user account, validating that there is not another
    // account with the same userID or email already in use and if valid, creates and saves the new user
    // information so that login will be possible.
    //
    public static void getUserInput() {

        Scanner scan = new Scanner(System.in);

        try {
            System.out.println("Must be 7 or more characters long. Restricted to numbers and letters.");
            System.out.print("UserInfo.User ID: ");
            String userID = scan.next();

            // Validate userID
            if (!isUserIdValid(userID)) {
                System.out.println("UserID is already in use.");
                return; // Exit the method
            }
            System.out.println();

            System.out.println("Must be 5 or more characters long. Restricted to numbers and letters.");
            System.out.print("Password: ");
            String password = scan.next();

            // Validate password complexity
            if (!isPasswordValid(password)) {
                System.out.println("Password does not meet the required criteria.");
                return; // Exit the method
            }

            System.out.println();

            System.out.print("Email: ");
            String emailAddress = scan.next();

            // Validate email format
            if (!isEmailValid(emailAddress)) {
                return; // Exit the method
            }

            // If all input is valid, create a new account
            createNewAccount(userID, password, emailAddress);

        } catch (Exception e) {
            System.out.println("An error occurred. Please try again.");
        } finally {
            scan.close(); // Close the scanner when done.
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // isPasswordValid
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Validates that the password is of a valid format (Greater than or equal to 7 characters long and
    // containing at least one uppercase letter, one lowercase letter, one number, and one special
    // character). If valid, returns true.
    //
    public static boolean isPasswordValid(String password) {
        if (password.length() < 7) {
            return false; // Password must be at least 7 characters long
        }

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else {
                // Special characters are allowed: ! @ # $ % ^ & * ( ) _ + - = < > ? .
                if ("!@#$%^&*()_+-=<>?".contains(String.valueOf(c))) {
                    hasSpecialCharacter = true;
                }
            }
        }
        return (hasUpperCase && hasLowerCase && hasNumber && hasSpecialCharacter);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // isEmailValid
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Validates that the email is of a valid email format and that the email is not already addressed to
    // an existing user saved within the users file. If valid, returns true.
    //
    public static boolean isEmailValid(String email) {
        // Initialize emailRegex validation String
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.(com|edu|net|gov|org)$";

        // Initialize an array list of UserInfo.User objects
        ArrayList<User> loadedUsers = new ArrayList<>();

        // Initialize the userFilePath
        String userFilePath = userFolderPath + File.separator + "user_data.json";

        // Initialize a File of the users folder path
        File userFile = new File(userFilePath);

        if (userFile.exists()) {
            // Load the folder path of the user files
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(userFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<User>>() {
                }.getType();
                loadedUsers = gson.fromJson(json, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (User user : loadedUsers) {
                // Compare email
                if (user.getEmail() == email) {
                    System.out.println("ERROR - Email address already in use.");
                    return false;
                }
            }
            return Pattern.matches(emailRegex, email) && email.length() >= 7;
        } else {
            //System.out.println("ERROR - UserInfo.User files folder does not exist.");
        }
        return Pattern.matches(emailRegex, email) && email.length() >= 7;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // isEmailValidMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Validates that the email is of a valid email format and that the email is not already addressed to
    // an existing user saved within the users file. If valid, returns true.
    //
    public static boolean isEmailValidMongoDB(String email) {
        // Initialize emailRegex validation String
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.(com|edu|net|gov|org)$";

        // Initialize an array list of UserInfo.User objects
        ArrayList<EnvisionaryUser> loadedUsers = MongoDBEnvisionaryUsers.retrieveCollection();

        // For each Envisionary user
        for (EnvisionaryUser user : loadedUsers) {
            // Compare email and return false if already in use
            if (user.getEmail() == email) {
                System.out.println("ERROR - Email address already in use.");
                return false;
            }
        }
        return Pattern.matches(emailRegex, email) && email.length() >= 7;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // isUserIdValid
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Validates that the userID is of a valid format (Consisting only of letters and numbers and greater
    // than or equal to 5 characters long in length) and that the userID is not already addressed to
    // an existing user saved within the users file. If valid, returns true.
    //
    public static boolean isUserIdValid(String userID) {
        // Initialize userIdRegex validation String (Only upper/lowercase letters and numbers)
        String userIdRegex = "^[a-zA-Z0-9]+$";

        // Initialize an array list of UserInfo.User objects
        ArrayList<User> loadedUsers = new ArrayList<>();

        // Initialize the userFilePath
        String userFilePath = userFolderPath + File.separator + "user_data.json";

        // Initialize a File of the users folder path
        File userFile = new File(userFilePath);

        if (userFile.exists()) {
            // Load the folder path of the user files
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(userFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<User>>() {}.getType();
                loadedUsers = gson.fromJson(json, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (User user : loadedUsers) {
                // Compare userID
                if (userID.equals(user.getUserID())) {
                    System.out.println("ERROR - UserID already in use.");
                    return false;
                }
            }
            return Pattern.matches(userIdRegex, userID) && userID.length() >= 7;
        } else {
            //System.out.println("ERROR - UserInfo.User files folder does not exist.");
        }
        return Pattern.matches(userIdRegex, userID) && userID.length() >= 7;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // isUserIdValidMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Validates that the userID is of a valid format (Consisting only of letters and numbers and greater
    // than or equal to 5 characters long in length) and that the userID is not already addressed to
    // an existing user saved within the users file. If valid, returns true.
    //
    public static boolean isUserIdValidMongoDB(String userID) {
        // Initialize userIdRegex validation String (Only upper/lowercase letters and numbers)
        String userIdRegex = "^[a-zA-Z0-9]+$";

        // Initialize an array list of UserInfo.User objects
        ArrayList<EnvisionaryUser> loadedUsers = MongoDBEnvisionaryUsers.retrieveCollection();

        // For each Envisionary user
        for (EnvisionaryUser user : loadedUsers) {
            // Compare userID and return false if already in use
            if (userID.equals(user.getUserID())) {
                System.out.println("ERROR - UserID already in use.");
                return false;
            }
        }
        return Pattern.matches(userIdRegex, userID) && userID.length() >= 7;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewAccount
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Creates a new UserInfo.User object using the parameter String userID, String password, and String emailAddress
    // before loading the users file and adding the new user to the list and saving the updated users file.
    //
    public static void createNewAccount(String userID, String password, String emailAddress) {

        User newUser = new User();
        newUser.setUserID(userID);
        newUser.setPassword(password);
        newUser.setEmail(emailAddress);

        // Initialize a new user array list
        ArrayList<User> loadedUsers = new ArrayList<>();

        // Initialize the userFilePath
        String userFilePath = userFolderPath + File.separator + "user_data.json";

        // Initialize a File of the users folder path
        File userFilesFolder = new File(userFilePath);

        if (userFilesFolder.exists()) {
            // Load the user list
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(userFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<User>>() {
                }.getType();
                loadedUsers = gson.fromJson(json, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //System.out.println("ERROR - UserInfo.User files folder does not exist.\n");
        }

        // Add the newUser to the loadedUsers array list
        loadedUsers.add(newUser);

        // Save newUser to file
        try (FileWriter writer = new FileWriter(userFilePath)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<User>>() {}.getType();
            String json = gson.toJson(loadedUsers, listType);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewAccountMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Creates a new UserInfo.User object using the parameter String userID, String password, and String emailAddress
    // before loading the users file and adding the new user to the list and saving the updated users file.
    //
    public static void createNewAccountMongoDB(String userID, String emailAddress) {

        // Initialize the new user to save
        EnvisionaryUser newUser = new EnvisionaryUser(userID, emailAddress);

        // Add newUser to MongoDB
        MongoDBEnvisionaryUsers.insertIndividualEnvisionaryUser(newUser);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // login
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Attempts to log in using the parameter String userID and String password by loading the users file
    // to an ArrayList of UserInfo.User objects and comparing the userID and password to each of the saved UserInfo.User
    // objects within the loaded users file. If a match is found the login will return true, else it will
    // return false.
    //
    public static boolean login(String userID, String password) {
        // Initialize a new user array list to load the users file into
        ArrayList<User> loadedUsers = new ArrayList<>();

        // Initialize the userFilePath
        String userFilePath = userFolderPath + File.separator + "user_data.json";

        // Initialize a File of the users folder path
        File userFilesFolder = new File(userFilePath);

        if (userFilesFolder.exists()) {
            // Load the user list
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(userFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<User>>() {}.getType();
                loadedUsers = gson.fromJson(json, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //System.out.println("ERROR - UserInfo.User files folder does not exist.");
            return false;
        }

        for (User user : loadedUsers) {
            if (userID.equals(user.getUserID())) {
                System.out.println("Login success.");
                return true;
            }
        }
        System.out.println("ERROR - UserID does not exist.");
        return false;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // loginMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Attempts to log in using the parameter String userID and String password by loading the users file
    // to an ArrayList of UserInfo.User objects and comparing the userID and password to each of the saved UserInfo.User
    // objects within the loaded users file. If a match is found the login will return true, else it will
    // return false.
    //
    public static boolean loginMongoDB(String userID) {
        // Initialize a new UserInfo.EnvisionaryUser array list and load the users from MongoDB
        ArrayList<EnvisionaryUser> envisionaryUsers = MongoDBEnvisionaryUsers.retrieveCollection();

        for (EnvisionaryUser user : envisionaryUsers) {
            if (userID.equals(user.getUserID())) {
                System.out.println("Login success.");
                return true;
            }
        }
        System.out.println("ERROR - UserID does not exist.");
        return false;
    }
}