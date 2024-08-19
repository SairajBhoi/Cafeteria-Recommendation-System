package client.service;

import java.io.IOException;
import client.RequestGateway.UserNotificationRequestGateway;
import client.Client;
import client.util.PrintOutToConsole;

public class UserNotificationService {
    private final UserNotificationRequestGateway requestGateway;

    public UserNotificationService(String role) {
        this.requestGateway = new UserNotificationRequestGateway(role);
    }

    public void viewUnseenNotifications(String userID) {
        processNotificationRequest(
            requestGateway.createViewUnseenNotificationRequest(userID),
            "Error fetching unseen notifications:"
        );
    }

    public void viewNotifications(String userID) {
        processNotificationRequest(
            requestGateway.createViewNotificationRequest(userID),
            "Error fetching notifications:"
        );
    }

    private void processNotificationRequest(String jsonRequest, String errorMessage) {
        try {
            String jsonResponse = Client.requestServer(jsonRequest);
            PrintOutToConsole.printToConsole(jsonResponse);
        } catch (IOException e) {
            handleException(errorMessage, e);
        }
    }

    private void handleException(String message, IOException e) {
        System.err.println(message + " " + e.getMessage());
    }
}
