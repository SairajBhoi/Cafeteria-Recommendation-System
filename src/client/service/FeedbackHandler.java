package client.service;

import client.model.Feedback;
import client.model.UserDiscardedFeedback;
import client.util.InputHandler;
import client.util.PrintOutToConsole;
import client.util.RequestHandler;

import java.time.LocalDate;

import client.RequestGateway.FeedbackRequestGateway;

public class FeedbackHandler {
    private FeedbackRequestGateway requestGateway;
    private RequestHandler requestHandler;
    private String role;
    private String employeeName;
    private String employeeId;

    public FeedbackHandler(String role, String employeeName, String employeeID) {
        this.role = role;
        this.employeeName = employeeName;
        this.employeeId = employeeID;
        this.requestGateway = new FeedbackRequestGateway(role);
        this.requestHandler = new RequestHandler();
    }

    public FeedbackHandler(String role) {
        this(role, null, null);
    }
    public FeedbackHandler() {
        this(null, null, null);
    }

    public void addFeedbackOnFoodItem() {
        Feedback feedback = gatherFoodItemFeedback();
        String jsonRequest = requestGateway.createAddFeedbackOnFoodItemRequest(feedback);
        String jsonResponse = requestHandler.sendRequestToServer(jsonRequest);
        PrintOutToConsole.printToConsole(jsonResponse);
    }

    public void viewFeedbackOnFoodItem() {
        String itemName = InputHandler.getStringInput("Enter the Food Item name: ");
        Feedback feedback = new Feedback();
        feedback.setItemName(itemName);

        String jsonRequest = requestGateway.createViewFeedbackOnFoodItemRequest(feedback);
        String jsonResponse = requestHandler.sendRequestToServer(jsonRequest);
        PrintOutToConsole.printToConsole(jsonResponse);
    }

    public void addFeedbackOnChefDiscardedFoodItem() {
        UserDiscardedFeedback feedback = gatherDiscardedFoodItemFeedback();
        String jsonRequest = requestGateway.createAddFeedbackOnDiscardFoodItemRequest(feedback);
        String jsonResponse = requestHandler.sendRequestToServer(jsonRequest);
        PrintOutToConsole.printToConsole(jsonResponse);
    }

    private Feedback gatherFoodItemFeedback() {
        Feedback feedback = new Feedback();
        feedback.setEmployeeName(this.employeeName);

        String itemName = InputHandler.getStringInput("Enter the Food Item name: ");
        feedback.setItemName(itemName);

        feedback.setTasteRating(getValidRating("Enter the Taste rating of " + itemName + " (0-5): "));
        feedback.setQualityRating(getValidRating("Enter the Quality rating of " + itemName + " (0-5): "));
        feedback.setFreshnessRating(getValidRating("Enter the Freshness rating of " + itemName + " (0-5): "));
        feedback.setValueForMoneyRating(getValidRating("Enter the Value for Money rating of " + itemName + " (0-5): "));
        feedback.setFeedbackMessage(InputHandler.getStringInput("Enter feedback message: "));

        return feedback;
    }

    private UserDiscardedFeedback gatherDiscardedFoodItemFeedback() {
        UserDiscardedFeedback feedback = new UserDiscardedFeedback();
        feedback.setUserID(this.employeeId);
        feedback.setDiscardID(InputHandler.getIntegerInput("Enter the Discard ID: "));
        feedback.setQuestion1Answer(InputHandler.getStringInput("Enter your answer for Question 1: What did you not like about the food? "));
        feedback.setQuestion2Answer(InputHandler.getStringInput("Enter your answer for Question 2: How would you like the taste to be improved? "));
        feedback.setQuestion3Answer(InputHandler.getStringInput("Enter your answer for Question 3: Please provide a recipe? "));
        feedback.setFeedbackDate(java.sql.Date.valueOf(LocalDate.now()));

        return feedback;
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
}
