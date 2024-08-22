package client.service;

import java.io.IOException;

import client.requestgateway.VoteRequestGateway;
import client.util.InputHandler;
import client.util.PrintOutToConsole;
import client.util.RequestHandler;
import client.util.UserDecisionHandler;

public class VoteFuncationalityHandler {
    private final String role;
    private final String userID;
    private final VoteRequestGateway voteRequestGateway;
    private final RequestHandler requestHandler;
    private final UserDecisionHandler userDecisionHandler;

    public VoteFuncationalityHandler(String role, String userID) {
        this.role = role;
        this.userID = userID;
        this.voteRequestGateway = new VoteRequestGateway(role);
        this.requestHandler = new RequestHandler();
        this.userDecisionHandler = new UserDecisionHandler();
    }

    public VoteFuncationalityHandler(String role) {
        this(role, null);
    }

    public void viewChefRollout() {
        String jsonRequest = voteRequestGateway.createChefRolloutRequest();
        String jsonResponse = requestHandler.sendRequestToServer(jsonRequest);
        PrintOutToConsole.printToConsole(jsonResponse);
    }

    public void viewRecommendation() {
        String jsonRequest = voteRequestGateway.createRecommendationRequest();
        String jsonResponse = requestHandler.sendRequestToServer(jsonRequest);
        PrintOutToConsole.printToConsole(jsonResponse);
    }

    public void addVote() {
        try {
            displayRecommendations();

            while (true) {
                String rolloutIDInput = InputHandler.getStringInput("Enter the Rollout ID you want to vote for (or type 'quit' to exit): ");
                if (rolloutIDInput.equalsIgnoreCase("quit")) {
                    break;
                }

                Integer rolloutID = parseRolloutID(rolloutIDInput);
                if (rolloutID == null) continue;

                Boolean voteDecision = userDecisionHandler.getUserDecision("Do you want to vote for Rollout ID " + rolloutID + "?");
                if (voteDecision == null) {
                    break;
                }

                if (voteDecision) {
                    String voteRequest = voteRequestGateway.createVoteRequest(this.userID, rolloutID, voteDecision);
                    String jsonResponse = requestHandler.sendRequestToServer(voteRequest);
                    PrintOutToConsole.printToConsole(jsonResponse);
                } else {
                    System.out.println("Skipping vote for Rollout ID " + rolloutID + ".");
                }

                Boolean voteAnother = userDecisionHandler.getUserDecision("Do you want to vote for another item?");
                if (voteAnother == null || !voteAnother) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Server connection failed: " + e.getMessage());
        }
    }

    private void displayRecommendations() throws IOException {
        System.out.println("Recommendation");
        String jsonRequest = voteRequestGateway.createRecommendationRequest();
        String jsonResponse = requestHandler.sendRequestToServer(jsonRequest);
        PrintOutToConsole.printToConsole(jsonResponse);
    }

    private Integer parseRolloutID(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Rollout ID: " + input);
            return null;
        }
    }
}
