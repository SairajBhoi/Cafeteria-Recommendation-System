package client.requestgateway;

import client.util.JsonConverter;
import client.model.ChefMenuRollout;
import client.model.TodayMenu;
import server.model.FoodCategory;

public class RolloutRequestGateway {
    private String basePath;

   
    public RolloutRequestGateway(String role) {
        this.basePath = buildBasePath(role);
    }

    
    private String buildBasePath(String role) {
        return (role != null && !role.isEmpty()) ? "/" + role : "";
    }

    
    private String generatePath(String endpoint) {
        return this.basePath + endpoint;
    }

    
    private String convertToJson(String path, Object data) {
        return JsonConverter.convertObjectToJson(path, data);
    }

    

    public String createRecommendationRequest(FoodCategory category) {
        String path = generatePath("/recommendation");
        return convertToJson(path, category);
    }

    public String createFinalVoteMenuRequest() {
        String path = generatePath("/finalVoteMenu");
        return convertToJson(path, null);
    }

    public String createFinalDecidedMenuRequest() {
        String path = generatePath("/finalDecidedMenuAfterRollout");
        return convertToJson(path, null);
    }

    public String createTodaysMenuRequest() {
        String path = generatePath("/viewTodaysMenu");
        return convertToJson(path, null);
    }

    public String createChefMenuRolloutRequest(ChefMenuRollout rollout) {
        String path = generatePath("/rolloutMenu");
        return convertToJson(path, rollout);
    }

    public String createFinalDecidedMenuAfterRolloutRequest(TodayMenu menu) {
        String path = generatePath("/addfinalResultMenu");
        return convertToJson(path, menu);
    }
}
