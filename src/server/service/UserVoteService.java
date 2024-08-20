package server.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import server.databaseoperation.UserVoteDatabaseOperator;
import server.model.Vote;
import server.util.JsonConverter;
import server.util.JsonStringToObject;

public class UserVoteService {

    private static final Logger logger = Logger.getLogger(UserVoteService.class.getName());
    private final UserVoteDatabaseOperator databaseOperator;

    public UserVoteService() {
        this.databaseOperator = new UserVoteDatabaseOperator();
    }

    public String getChefRolloutListForCurrentDateAsJson() {
        String jsonResponse;
        try {
            JSONArray jsonArray = databaseOperator.getChefRolloutListForCurrentDate();

            if (jsonArray.isEmpty()) {
                jsonResponse = JsonConverter.convertStatusAndMessageToJson("info", "No rollout yet");
            } else {
                jsonResponse = JsonConverter.convertObjectToJson(jsonArray).toString();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving chef rollout list for current date", e);
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", "Error retrieving chef rollout list");
        }
        return jsonResponse;
    }

    public String addVote(String data) {
        String jsonResponse;
        try {
            Vote vote = JsonStringToObject.fromJsonToObject(data, Vote.class);
            jsonResponse = processVote(vote);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing vote", e);
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", "Error processing vote: " + e.getMessage());
        }
        return jsonResponse;
    }

    private String processVote(Vote vote) {
        String jsonResponse;
        try {
            jsonResponse = databaseOperator.processVote(vote);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing vote", e);
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", "Error processing vote: " + e.getMessage());
        }
        return jsonResponse;
    }
}
