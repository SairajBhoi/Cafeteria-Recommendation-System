package server.service;



import org.json.simple.JSONArray;

import server.databaseoperation.UserVoteDatabaseOperator;
import server.model.Vote;
import server.util.JsonConverter;
import server.util.JsonStringToObject;

public class UserVoteService {

    private UserVoteDatabaseOperator databaseOperator;

    public UserVoteService() {
        this.databaseOperator = new UserVoteDatabaseOperator();
    }

    public String getChefRolloutListForCurrentDateAsJson() {
        JSONArray jsonArray = databaseOperator.getChefRolloutListForCurrentDate();

        if (jsonArray.isEmpty()) {
            return JsonConverter.convertStatusAndMessageToJson("info", "no rollout yet");
        } else {
            return JsonConverter.convertObjectToJson(jsonArray).toString();
        }
    }

    public String addVote(String data) {
        Vote vote = JsonStringToObject.fromJsonToObject(data, Vote.class);
        return processVote(vote);
    }

    private String processVote(Vote vote) {
        return databaseOperator.processVote(vote);
    }
}
