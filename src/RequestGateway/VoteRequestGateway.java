package RequestGateway;

import client.util.JsonConverter;
import org.json.simple.JSONObject;

public class VoteRequestGateway {
    private String basePath;

    public VoteRequestGateway(String role) {
        this.basePath = "/" + role;
    }

    private String generatePath(String endpoint) {
        return this.basePath + endpoint;
    }

    public String createRecommendationRequest() {
        return JsonConverter.convertObjectToJson(generatePath("/recommendationOnPreference"), null);
    }

    public String createChefRolloutRequest() {
        return JsonConverter.convertObjectToJson(generatePath("/viewChefRollout"), null);
    }

    public String createVoteRequest(String userID, int rolloutID, boolean voteDecision) {
        JSONObject voteJson = new JSONObject();
        voteJson.put("userID", userID);
        voteJson.put("rolloutID", rolloutID);
        voteJson.put("voteDecision", voteDecision);

        JSONObject requestJson = new JSONObject();
        requestJson.put("path", generatePath("/addVote"));
        requestJson.put("data", voteJson);

        return requestJson.toJSONString();
    }
}
