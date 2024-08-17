package RequestGateway;



import client.util.JsonConverter;

public class UserNotificationRequestGateway {
    private String basePath;

    public UserNotificationRequestGateway(String role) {
        this.basePath = "/" + role;
    }

    private String generatePath(String endpoint) {
        return this.basePath + endpoint;
    }

    public String createViewUnseenNotificationRequest(String userID) {
        String jsonRequest = JsonConverter.convertObjectToJson(generatePath("/viewUnseenNotification"), null);
        String userIdJsonString = JsonConverter.createJson("UserID", userID);
        return JsonConverter.addJsonObjectToDataField(jsonRequest, userIdJsonString);
    }

    public String createViewNotificationRequest(String userID) {
        String jsonRequest = JsonConverter.convertObjectToJson(generatePath("/viewNotification"), null);
        String userIdJsonString = JsonConverter.createJson("UserID", userID);
        return JsonConverter.addJsonObjectToDataField(jsonRequest, userIdJsonString);
    }
}
