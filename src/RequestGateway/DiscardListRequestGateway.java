package RequestGateway;

import org.json.simple.JSONObject;

import client.model.DiscardedFoodItem;
import client.model.UserDiscardedFeedback;
import client.util.JsonConverter;

public class DiscardListRequestGateway {
    private String basePath;

    public DiscardListRequestGateway(String role) {
        this.basePath = "/" + role;
    }

    private String generatePath(String endpoint) {
        return this.basePath + endpoint;
    }

    public String createViewChefDiscardListRequest() {
        return JsonConverter.convertObjectToJson(generatePath("/viewChefDiscardedList"), null);
    }

    public String createViewDiscardedListRequest() {
        return JsonConverter.convertObjectToJson(generatePath("/viewDiscardedList"), null);
    }

    public String createAddChefDiscardedFoodItemRequest(DiscardedFoodItem discardedFoodItem) {
        return JsonConverter.convertObjectToJson(generatePath("/addChefDiscardedFoodItem"), discardedFoodItem);
    }

    public String createDeleteChefDiscardedFoodItemRequest(DiscardedFoodItem discardedFoodItem) {
        return JsonConverter.convertObjectToJson(generatePath("/deleteChefDiscardedFoodItem"), discardedFoodItem);
    }

    public String createViewFeedbackOnDiscardListRequest(UserDiscardedFeedback feedback) {
        return JsonConverter.convertObjectToJson(generatePath("/viewFeedbackOnDiscardFoodItem"), feedback);
    }
}
