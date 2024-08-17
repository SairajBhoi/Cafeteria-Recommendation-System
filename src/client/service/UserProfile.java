package client.service;

import java.io.IOException;

import RequestGateway.UserProfileRequestGateway;
import client.Client;
import client.model.UserPreference;
import client.util.InputHandler;
import client.util.PrintOutToConsole;

public class UserProfile {
    private String userID;
    private String userRole;
    private UserProfileRequestGateway requestGateway;

    public UserProfile(String userRole, String userID) {
        this.userRole = userRole;
        this.userID = userID;
        this.requestGateway = new UserProfileRequestGateway(userRole);
    }

    public void viewProfile() {
        try {
            String jsonRequest = requestGateway.createViewProfileRequest(userID);
            String jsonResponse = Client.requestServer(jsonRequest);
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error fetching profile: " + e.getMessage());
        }
    }

    public void updateUserProfile() {
        UserPreference userPreference = new UserPreference();
        userPreference.setUserID(this.userID);

        int dietaryInput;
        String dietaryPreference;
        do {
            dietaryInput = InputHandler.getIntegerInput("Enter dietary preference (1 - Vegetarian, 2 - Non-Vegetarian, 3 - Eggetarian): ");
            dietaryPreference = mapDietaryPreference(dietaryInput);
            if (dietaryPreference == null) {
                System.out.println("Invalid input. Please enter a valid integer (1, 2, 3).");
            }
        } while (dietaryPreference == null);
        userPreference.setDietaryPreference(dietaryPreference);

        int spiceInput;
        String spiceLevel;
        do {
            spiceInput = InputHandler.getIntegerInput("Enter preferred spice level (1 - Low, 2 - Medium, 3 - High): ");
            spiceLevel = mapSpiceLevel(spiceInput);
            if (spiceLevel == null) {
                System.out.println("Invalid input. Please enter a valid integer (1, 2, 3).");
            }
        } while (spiceLevel == null);
        userPreference.setPreferredSpiceLevel(spiceLevel);

        int cuisineInput;
        String cuisine;
        do {
            cuisineInput = InputHandler.getIntegerInput("Enter preferred cuisine (1 - North Indian, 2 - South Indian, 3 - Other): ");
            cuisine = mapCuisine(cuisineInput);
            if (cuisine == null) {
                System.out.println("Invalid input. Please enter a valid integer (1, 2, 3).");
            }
        } while (cuisine == null);
        userPreference.setPreferredCuisine(cuisine);

        userPreference.setHasSweetTooth(InputHandler.getBooleanInput("Do you have a sweet tooth?"));

        try {
            String jsonRequest = requestGateway.createUpdateProfileRequest(userPreference);
            String jsonResponse = Client.requestServer(jsonRequest);
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error updating profile: " + e.getMessage());
        }
    }

    private String mapDietaryPreference(int dietaryInput) {
        switch (dietaryInput) {
            case 1:
                return "Vegetarian";
            case 2:
                return "Non-Vegetarian";
            case 3:
                return "Eggetarian";
            default:
                return null;
        }
    }

    private String mapSpiceLevel(int spiceInput) {
        switch (spiceInput) {
            case 1:
                return "Low";
            case 2:
                return "Medium";
            case 3:
                return "High";
            default:
                return null;
        }
    }

    private String mapCuisine(int cuisineInput) {
        switch (cuisineInput) {
            case 1:
                return "North Indian";
            case 2:
                return "South Indian";
            case 3:
                return "Other";
            default:
                return null;
        }
    }
}
