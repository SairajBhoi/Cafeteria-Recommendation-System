package client.service;

import java.io.IOException;
import client.Client;
import client.util.JsonConverter;
import client.util.PrintOutToConsole;

public class UserNotificationService {
    private String role;
    private String requestPath;

    public UserNotificationService(String role) {
        this.role = role;
        this.requestPath = "/" + role;
    }

    public void viewUnseenNotification(String userID) {
        try {
            this.requestPath += "/viewUnseenNotification";
            String jsonRequest = JsonConverter.convertObjectToJson(this.requestPath, null);

            String userIdJsonString = JsonConverter.createJson("UserID", userID);
            String jsonRequestToServer = JsonConverter.addJsonObjectToDataField(jsonRequest, userIdJsonString);

            String jsonResponse = Client.requestServer(jsonRequestToServer);
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            resetRequestPath();
        }
    }

    public void viewNotification(String userID) {
        try {
            this.requestPath += "/viewNotification";
            String jsonRequest = JsonConverter.convertObjectToJson(this.requestPath, null);
             
            String userIdJsonString = JsonConverter.createJson("UserID", userID);
            String jsonRequestToServer = JsonConverter.addJsonObjectToDataField(jsonRequest, userIdJsonString);
            
            
            
            String jsonResponse = Client.requestServer(jsonRequestToServer);
            PrintOutToConsole.printToConsole(jsonResponse);
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            resetRequestPath();
        }
    }

    private void resetRequestPath() {
        this.requestPath = "/" + this.role;
    }
}
