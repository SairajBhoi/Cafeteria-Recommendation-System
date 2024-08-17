package client.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import client.Client;
import client.model.DiscardedFoodItem;
import client.model.UserDiscardedFeedback;
import client.util.InputHandler;
import client.util.PrintOutToConsole;
import RequestGateway.DiscardListRequestGateway;

public class DiscardListService {
    private DiscardListRequestGateway requestGateway;

    public DiscardListService(String role) {
        this.requestGateway = new DiscardListRequestGateway(role);
    }

    public void viewChefDiscardList() {
        String jsonRequest = requestGateway.createViewChefDiscardListRequest();
        sendRequestToServer(jsonRequest);
    }

    public void generateDiscardList() {
        String jsonRequest = requestGateway.createViewDiscardedListRequest();
        try {
            String jsonResponse = Client.requestServer(jsonRequest);
            ObjectMapper objectMapper = new ObjectMapper();
            List<DiscardedFoodItem> discardedFoodItemList = objectMapper.readValue(jsonResponse, new TypeReference<List<DiscardedFoodItem>>() {});

            if (discardedFoodItemList != null && !discardedFoodItemList.isEmpty()) {
                processDiscardedFoodItems(discardedFoodItemList);
            } else {
                System.out.println("No discarded food items available.");
            }
        } catch (IOException e) {
            System.out.println("Server connection failed.");
            e.printStackTrace();
        }
    }

    public void viewFeedbackOnDiscardList() {
        int discardID = InputHandler.getIntegerInput("Enter the Discard ID: ");
        UserDiscardedFeedback feedback = new UserDiscardedFeedback();
        feedback.setDiscardID(discardID);

        String jsonRequest = requestGateway.createViewFeedbackOnDiscardListRequest(feedback);
        sendRequestToServer(jsonRequest);
    }

    private void processDiscardedFoodItems(List<DiscardedFoodItem> discardedFoodItemList) {
        for (DiscardedFoodItem discardedFoodItem : discardedFoodItemList) {
            Boolean decision = getUserDecision("Enter whether you want to add " + discardedFoodItem.getItemName() + " in Discarded Table for User Feedback");

            if (decision == null) {
                System.out.println("Operation terminated by user.");
                break;
            }

            if (decision) {
                requestGateway.createAddChefDiscardedFoodItemRequest(discardedFoodItem);
            } else {
                Boolean deleteDecision = getUserDecision("Delete " + discardedFoodItem.getItemName() + " from main menu");

                if (deleteDecision == null) {
                    System.out.println("Operation terminated by user.");
                    break;
                }

                if (deleteDecision) {
                    requestGateway.createDeleteChefDiscardedFoodItemRequest(discardedFoodItem);
                }
            }
        }
    }

    private Boolean getUserDecision(String message) {
        while (true) {
            String input = InputHandler.getStringInput(message + " (yes/no/q/quit): ");
            switch (input.toLowerCase()) {
                case "yes":
                case "y":
                    return true;
                case "no":
                case "n":
                    return false;
                case "q":
                case "quit":
                    return null;
                default:
                    System.out.println("Invalid input. Please enter 'yes', 'no', 'q', or 'quit'.");
            }
        }
    }

    private void sendRequestToServer(String jsonRequest) {
        try {
            String jsonResponse = Client.requestServer(jsonRequest);
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error sending request to the server: " + e.getMessage());
        }
    }
}
