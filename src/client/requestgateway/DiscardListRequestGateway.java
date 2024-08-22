package client.requestgateway;

import client.model.DiscardedFoodItem;
import client.model.UserDiscardedFeedback;
import client.util.JsonConverter;

public class DiscardListRequestGateway {
    private String basePath;

   
    public DiscardListRequestGateway(String role) {
        this.basePath = buildBasePath(role);
    }

   
    public DiscardListRequestGateway() {
        this.basePath = "";
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

  
    public String createViewChefDiscardListRequest() {
        String path = generatePath("/viewChefDiscardedList");
        return convertToJson(path, null);
    }

    public String createViewDiscardedListRequest() {
        String path = generatePath("/viewDiscardedList");
        return convertToJson(path, null);
    }

    public String createAddChefDiscardedFoodItemRequest(DiscardedFoodItem discardedFoodItem) {
        String path = generatePath("/addChefDiscardedFoodItem");
        return convertToJson(path, discardedFoodItem);
    }

    public String createDeleteChefDiscardedFoodItemRequest(DiscardedFoodItem discardedFoodItem) {
        String path = generatePath("/deleteChefDiscardedFoodItem");
        return convertToJson(path, discardedFoodItem);
    }

    public String createViewFeedbackOnDiscardListRequest(UserDiscardedFeedback feedback) {
        String path = generatePath("/viewFeedbackOnDiscardFoodItem");
        return convertToJson(path, feedback);
    }
}
