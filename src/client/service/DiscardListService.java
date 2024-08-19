package client.service;

import java.io.IOException;
import java.util.List;

import client.Client;
import client.model.DiscardedFoodItem;
import client.model.UserDiscardedFeedback;
import client.util.InputHandler;

import client.util.JsonParser;
import client.util.RequestHandler;
import client.RequestGateway.DiscardListRequestGateway;
import client.util.UserDecisionHandler;

public class DiscardListService {
    private DiscardListRequestGateway requestGateway;
    private JsonParser jsonParser;
    private UserDecisionHandler userDecisionHandler;
    private RequestHandler requestHandler;

    public DiscardListService(String role) {
        this.requestGateway = new DiscardListRequestGateway(role);
        this.jsonParser = new JsonParser();
        this.userDecisionHandler = new UserDecisionHandler();
        this.requestHandler = new RequestHandler();
    }
    public DiscardListService() {
        this.requestGateway = new DiscardListRequestGateway();
        this.jsonParser = new JsonParser();
        this.userDecisionHandler = new UserDecisionHandler();
        this.requestHandler = new RequestHandler();
    }

    public void viewChefDiscardList() {
        String jsonRequest = requestGateway.createViewChefDiscardListRequest();
        requestHandler.sendRequestToServer(jsonRequest);
    }

    public void generateDiscardList() {
        String jsonRequest = requestGateway.createViewDiscardedListRequest();
        String jsonResponse = fetchJsonResponse(jsonRequest);

        if (jsonResponse != null) {
            List<DiscardedFoodItem> discardedFoodItemList = jsonParser.parseDiscardedFoodItems(jsonResponse);
            processDiscardedFoodItems(discardedFoodItemList);
        } else {
            System.out.println("No discarded food items available.");
        }
    }

    public void viewFeedbackOnDiscardList() {
        int discardID = InputHandler.getIntegerInput("Enter the Discard ID: ");
        UserDiscardedFeedback feedback = new UserDiscardedFeedback();
        feedback.setDiscardID(discardID);

        String jsonRequest = requestGateway.createViewFeedbackOnDiscardListRequest(feedback);
        requestHandler.sendRequestToServer(jsonRequest);
    }

    private String fetchJsonResponse(String jsonRequest) {
        try {
            return Client.requestServer(jsonRequest);
        } catch (IOException e) {
            System.out.println("Server connection failed.");
            e.printStackTrace();
            return null;
        }
    }

    private void processDiscardedFoodItems(List<DiscardedFoodItem> discardedFoodItemList) {
        for (DiscardedFoodItem discardedFoodItem : discardedFoodItemList) {
            Boolean decision = userDecisionHandler.getUserDecision(
                    "Enter whether you want to add " + discardedFoodItem.getItemName() + " to the Discarded Table for User Feedback");

            if (decision == null) {
                System.out.println("Operation terminated by user.");
                break;
            }

            if (decision) {
                addDiscardedFoodItem(discardedFoodItem);
            } else {
                Boolean deleteDecision = userDecisionHandler.getUserDecision(
                        "Delete " + discardedFoodItem.getItemName() + " from the main menu");

                if (deleteDecision == null) {
                    System.out.println("Operation terminated by user.");
                    break;
                }

                if (deleteDecision) {
                    deleteDiscardedFoodItem(discardedFoodItem);
                }
            }
        }
    }

    private void addDiscardedFoodItem(DiscardedFoodItem discardedFoodItem) {
        String jsonRequest = requestGateway.createAddChefDiscardedFoodItemRequest(discardedFoodItem);
        requestHandler.sendRequestToServer(jsonRequest);
    }

    private void deleteDiscardedFoodItem(DiscardedFoodItem discardedFoodItem) {
        String jsonRequest = requestGateway.createDeleteChefDiscardedFoodItemRequest(discardedFoodItem);
        requestHandler.sendRequestToServer(jsonRequest);
    }
}
