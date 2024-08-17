package RequestGateway;

import client.util.JsonConverter;
import client.model.Feedback;
import client.model.UserDiscardedFeedback;

public class FeedbackRequestGateway {
    private String basePath;

    public FeedbackRequestGateway(String role) {
        this.basePath = "/" + role;
    }

    private String generatePath(String endpoint) {
        return this.basePath + endpoint;
    }

    public String createAddFeedbackOnFoodItemRequest(Feedback feedback) {
        return JsonConverter.convertObjectToJson(generatePath("/addFeedbackOnFoodItem"), feedback);
    }

    public String createViewFeedbackOnFoodItemRequest(Feedback feedback) {
        return JsonConverter.convertObjectToJson(generatePath("/viewFeedbackOnFoodItem"), feedback);
    }

    public String createAddFeedbackOnDiscardFoodItemRequest(UserDiscardedFeedback feedback) {
        return JsonConverter.convertObjectToJson(generatePath("/addFeedbackOnDiscardFoodItem"), feedback);
    }
}
