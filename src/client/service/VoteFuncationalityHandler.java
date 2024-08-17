package client.service;

import java.io.IOException;

import client.Client;
import client.util.InputHandler;
import client.util.PrintOutToConsole;
import RequestGateway.VoteRequestGateway;

public class VoteFuncationalityHandler {
    private String role;
    private String userID;
    private VoteRequestGateway voteRequestGateway;

    public VoteFuncationalityHandler(String role, String userID) {
        this.role = role;
        this.userID = userID;
        this.voteRequestGateway = new VoteRequestGateway(role);
    }

    public VoteFuncationalityHandler(String role) {
        this(role, null);
    }

    public void viewChefRollout() {
        try {
            String jsonRequest = voteRequestGateway.createChefRolloutRequest();
            String jsonResponse = Client.requestServer(jsonRequest);
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Failed to retrieve chef rollout: " + e.getMessage());
        }
    }

    public void viewRecommendation() {
        try {
            String jsonRequest = voteRequestGateway.createRecommendationRequest();
            String jsonResponse = Client.requestServer(jsonRequest);
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Failed to retrieve recommendation: " + e.getMessage());
        }
    }

    public void addVote() {
        try {
            // Fetch and display recommendations
            System.out.println("Recommendation");
            String jsonRequestRecommendation = voteRequestGateway.createRecommendationRequest();
            String jsonResponseRecommendation = Client.requestServer(jsonRequestRecommendation);
            PrintOutToConsole.printToConsole(jsonResponseRecommendation);

            // Loop to handle voting for multiple Rollout IDs
            while (true) {
                // Get user input for a single Rollout ID
                String rolloutIDInput = InputHandler.getStringInput("Enter the Rollout ID you want to vote for (or type 'quit' to exit): ");
                if (rolloutIDInput.equalsIgnoreCase("quit")) {
                    break;
                }

                int rolloutID;
                try {
                    rolloutID = Integer.parseInt(rolloutIDInput.trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Rollout ID: " + rolloutIDInput);
                    continue;
                }

                Boolean voteDecision = getUserDecision("Do you want to vote for Rollout ID " + rolloutID + "? (yes/no/quit)");
                if (voteDecision == null) {
                    break;
                }

                if (voteDecision) {
                    String voteRequest = voteRequestGateway.createVoteRequest(this.userID, rolloutID, voteDecision);
                    String voteResponse = Client.requestServer(voteRequest);
                    System.out.println(voteResponse);
                } else {
                    System.out.println("Skipping vote for Rollout ID " + rolloutID + ".");
                }

                // Ask if the user wants to vote for another item
                Boolean voteAnother = getUserDecision("Do you want to vote for another item? (yes/no/quit)");
                if (voteAnother == null || !voteAnother) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Server connection failed: " + e.getMessage());
        }
    }

    private Boolean getUserDecision(String message) {
        while (true) {
            String input = InputHandler.getStringInput(message + " (yes/no/q/quit): ");
            switch (input.toLowerCase()) {
                case "y":
                case "yes":
                    return true;
                case "n":
                case "no":
                    return false;
                case "q":
                case "quit":
                    return null;
                default:
                    System.out.println("Invalid input. Please enter 'yes', 'no', 'q', or 'quit'.");
            }
        }
    }
}
