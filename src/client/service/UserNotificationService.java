package client.service;

import java.io.IOException;
import RequestGateway.UserNotificationRequestGateway;
import client.Client;
import client.util.PrintOutToConsole;

public class UserNotificationService {
    private UserNotificationRequestGateway requestGateway;

    public UserNotificationService(String role) {
        this.requestGateway = new UserNotificationRequestGateway(role);
    }

    public void viewUnseenNotification(String userID) {
        try {
            String jsonRequest = requestGateway.createViewUnseenNotificationRequest(userID);
            String jsonResponse = Client.requestServer(jsonRequest);
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error fetching unseen notifications: " + e.getMessage());
        }
    }

    public void viewNotification(String userID) {
        try {
            String jsonRequest = requestGateway.createViewNotificationRequest(userID);
            String jsonResponse = Client.requestServer(jsonRequest);
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error fetching notifications: " + e.getMessage());
        }
    }
}
