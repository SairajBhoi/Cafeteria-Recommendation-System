package client.RequestGateway;

import client.util.JsonConverter;

public class UserNotificationRequestGateway {
    private String basePath;

   
    public UserNotificationRequestGateway(String role) {
        this.basePath = buildBasePath(role);
    }

   
    private String buildBasePath(String role) {
        return (role != null && !role.isEmpty()) ? "/" + role : "";
    }

    
    private String generatePath(String endpoint) {
        return this.basePath + endpoint;
    }

    
    private String createRequestWithUserID(String endpoint, String userID) {
        String path = generatePath(endpoint);
        String jsonRequest = JsonConverter.convertObjectToJson(path, null);
        String userIdJsonString = JsonConverter.createJson("UserID", userID);
        return JsonConverter.addJsonObjectToDataField(jsonRequest, userIdJsonString);
    }

   
    public String createViewUnseenNotificationRequest(String userID) {
        return createRequestWithUserID("/viewUnseenNotification", userID);
    }

    
    public String createViewNotificationRequest(String userID) {
        return createRequestWithUserID("/viewNotification", userID);
    }
}
