package client.service;

import java.io.IOException;
import java.time.LocalDate;
import java.sql.Date;

import client.Client;
import client.model.Feedback;
import client.model.UserDiscardedFeedback;
import client.util.InputHandler;
import client.util.PrintOutToConsole;
import RequestGateway.FeedbackRequestGateway;

public class FeedbackHandler {
    private Feedback feedback;
    private String role;
    private String employeeName;
    private String employeeId;
    private FeedbackRequestGateway requestGateway;

    public FeedbackHandler(String role, String employeeName, String employeeID) {
        feedback = new Feedback();
        feedback.setEmployeeId(employeeID);
        feedback.setEmployeeName(employeeName);
        this.role = role;
        this.employeeName = employeeName;
        this.employeeId = employeeID;
        this.requestGateway = new FeedbackRequestGateway(role);
    }

    public FeedbackHandler(String role) {
        feedback = new Feedback();
        this.role = role;
        this.requestGateway = new FeedbackRequestGateway(role);
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

        String jsonRequest = requestGateway.createAddFeedbackOnFoodItemRequest(feedback);

        sendRequestToServer(jsonRequest);
    }

    public void viewFeedbackOnFoodItem() {
        String itemName = InputHandler.getStringInput("Enter the Food Item name: ");
        feedback.setItemName(itemName);

        String jsonRequest = requestGateway.createViewFeedbackOnFoodItemRequest(feedback);

        try {
            String jsonResponse = Client.requestServer(jsonRequest);
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error retrieving feedback from the server: " + e.getMessage());
        }
    }

    public void addFeedbackOnChefDiscardedFoodItem() {
        String userID = this.employeeId;
        int discardID = InputHandler.getIntegerInput("Enter the Discard ID: ");
        String question1Answer = InputHandler.getStringInput("Enter your answer for Question 1: What did you not like about the food? ");
        String question2Answer = InputHandler.getStringInput("Enter your answer for Question 2: How would you like the taste to be improved? ");
        String question3Answer = InputHandler.getStringInput("Enter your answer for Question 3: Please provide a recipe ?: ");
        Date feedbackDate = Date.valueOf(LocalDate.now());

        UserDiscardedFeedback feedback = new UserDiscardedFeedback();
        feedback.setUserID(userID);
        feedback.setDiscardID(discardID);
        feedback.setQuestion1Answer(question1Answer);
        feedback.setQuestion2Answer(question2Answer);
        feedback.setQuestion3Answer(question3Answer);
        feedback.setFeedbackDate(feedbackDate);

        String jsonRequest = requestGateway.createAddFeedbackOnDiscardFoodItemRequest(feedback);

        sendRequestToServer(jsonRequest);
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

    private void sendRequestToServer(String jsonRequest) {
        try {
            String jsonResponse = Client.requestServer(jsonRequest);
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error sending feedback to the server: " + e.getMessage());
        }
    }
}
