package client.RequestGateway;

import client.util.JsonConverter;
import org.json.simple.JSONObject;

public class VoteRequestGateway {
    private String basePath;

    public VoteRequestGateway(String role) {
        this.basePath = buildBasePath(role);
    }

    
    private String buildBasePath(String role) {
        return (role != null && !role.isEmpty()) ? "/" + role : "";
    }

   
    private String generatePath(String endpoint) {
        return this.basePath + endpoint;
    }

   
    public String createRecommendationRequest() {
        return createJsonRequest("/recommendationOnPreference", null);
    }

   
    public String createChefRolloutRequest() {
        return createJsonRequest("/viewChefRollout", null);
    }

    
    public String createVoteRequest(String userID, int rolloutID, boolean voteDecision) {
        JSONObject voteJson = createVoteJson(userID, rolloutID, voteDecision);
        return createJsonRequest("/addVote", voteJson);
    }

  
    private JSONObject createVoteJson(String userID, int rolloutID, boolean voteDecision) {
        JSONObject voteJson = new JSONObject();
        voteJson.put("userID", userID);
        voteJson.put("rolloutID", rolloutID);
        voteJson.put("voteDecision", voteDecision);
        return voteJson;
    }

   
    private String createJsonRequest(String endpoint, Object data) {
        return JsonConverter.convertObjectToJson(generatePath(endpoint), data);
    }
}
