package client.requestgateway;

import client.util.JsonConverter;
import client.model.Feedback;
import client.model.UserDiscardedFeedback;

public class FeedbackRequestGateway {
    private String basePath;

   
    public FeedbackRequestGateway(String role) {
        this.basePath = buildBasePath(role);
    }

   
    public FeedbackRequestGateway() {
        this.basePath = "";
    }

   
    private String buildBasePath(String role) {
        return (role != null && !role.isEmpty()) ? "/" + role : "";
    }

   
    private String generatePath(String endpoint) {
        return this.basePath + endpoint;
    }

   
    public String createAddFeedbackOnFoodItemRequest(Feedback feedback) {
        return createJsonRequest("/addFeedbackOnFoodItem", feedback);
    }

  
    public String createViewFeedbackOnFoodItemRequest(Feedback feedback) {
        return createJsonRequest("/viewFeedbackOnFoodItem", feedback);
    }

    
    public String createAddFeedbackOnDiscardFoodItemRequest(UserDiscardedFeedback feedback) {
        return createJsonRequest("/addFeedbackOnDiscardFoodItem", feedback);
    }

   
    private String createJsonRequest(String endpoint, Object data) {
        return JsonConverter.convertObjectToJson(generatePath(endpoint), data);
    }
}
