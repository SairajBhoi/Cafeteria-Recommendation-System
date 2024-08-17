package RequestGateway;

import client.util.JsonConverter;
import client.model.ChefMenuRollout;
import client.model.TodayMenu;
import server.model.FoodCategory;

public class RolloutRequestGateway {
    private String basePath;

    public RolloutRequestGateway(String role) {
        this.basePath = "/" + role;
    }

    private String generatePath(String endpoint) {
        return this.basePath + endpoint;
    }

    public String createRecommendationRequest(FoodCategory category) {
        return JsonConverter.convertObjectToJson(generatePath("/recommendation"), category);
    }

    public String createFinalVoteMenuRequest() {
        return JsonConverter.convertObjectToJson(generatePath("/finalVoteMenu"), null);
    }

    public String createFinalDecidedMenuRequest() {
        return JsonConverter.convertObjectToJson(generatePath("/finalDecidedMenuAfterRollout"), null);
    }

    public String createTodaysMenuRequest() {
        return JsonConverter.convertObjectToJson(generatePath("/viewTodaysMenu"), null);
    }

    public String createChefMenuRolloutRequest(ChefMenuRollout rollout) {
        return JsonConverter.convertObjectToJson(generatePath("/rolloutMenu"), rollout);
    }

    public String createFinalDecidedMenuAfterRolloutRequest(TodayMenu menu) {
        return JsonConverter.convertObjectToJson(generatePath("/addfinalResultMenu"), menu);
    }
}
