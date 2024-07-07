package client.service;

import java.io.IOException;

import client.Client;
import client.model.Feedback;
import client.util.InputHandler;
import client.util.JsonConverter;
import client.util.PrintOutToConsole;

public class FeedbackHandler {
    private Feedback feedback;
    private String requestPath;
    private String role;
    private String employeeName;
    private String employeeId;

    public FeedbackHandler(String role, String employeeName, String employeeID) {
        feedback = new Feedback();
        feedback.setEmployeeId(employeeID);
        feedback.setEmployeeName(employeeName);
        this.role = role;
        this.employeeName = employeeName;
        this.employeeId = employeeID;
        this.requestPath = "/" + role;
    }

    public FeedbackHandler(String role) {
        this.role = role;
        this.requestPath = "/" + role;
    }

    public void addFeedbackOnFoodItem() {
        String itemName = InputHandler.getStringInput("Enter the Food Item name: ");
        int tasteRating = getValidRating("Enter the Taste rating of " + itemName + " (0-5): ");
        int qualityRating = getValidRating("Enter the Quality rating of " + itemName + " (0-5): ");
        int freshnessRating = getValidRating("Enter the Freshness rating of " + itemName + " (0-5): ");
        int valueForMoneyRating = getValidRating("Enter the Value for Money rating of " + itemName + " (0-5): ");
        String feedbackMessage = InputHandler.getStringInput("Enter feedback message: ");

        feedback.setEmployeeName(this.employeeName);
        feedback.setItemName(itemName);
        feedback.setTasteRating(tasteRating);
        feedback.setQualityRating(qualityRating);
        feedback.setFreshnessRating(freshnessRating);
        feedback.setValueForMoneyRating(valueForMoneyRating);
        feedback.setFeedbackMessage(feedbackMessage);

        String fullPath = this.requestPath + "/addFeedbackOnFoodItem";
        String jsonRequest = JsonConverter.convertObjectToJson(fullPath, feedback);

        try {
            String jsonResponse = Client.requestServer(jsonRequest);
            System.out.print(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error sending feedback to the server: " + e.getMessage());
        }
    }

    private int getValidRating(String message) {
        int rating;
        do {
            rating = InputHandler.getIntegerInput(message);
        } while (!isValidRating(rating));
        return rating;
    }

    private boolean isValidRating(int rating) {
        return rating >= 0 && rating <= 5;
    }

    public void viewFeedbackOnFoodItem() {
        String itemName = InputHandler.getStringInput("Enter the Food Item name: ");
        feedback.setItemName(itemName);

        String fullPath = "/viewFeedbackOnFoodItem";
        String jsonRequest = JsonConverter.convertObjectToJson(fullPath, feedback);

        try {
            String jsonResponse = Client.requestServer(jsonRequest);
            System.out.print(jsonResponse);
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error retrieving feedback from the server: " + e.getMessage());
        }
    }
}
