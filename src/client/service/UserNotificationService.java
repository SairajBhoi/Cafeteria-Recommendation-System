package client.service;

import java.io.IOException;
import client.RequestGateway.UserNotificationRequestGateway;
import client.Client;
import client.util.PrintOutToConsole;
import client.util.RequestHandler;

public class UserNotificationService {
    private final UserNotificationRequestGateway requestGateway;
    private final RequestHandler requestHandler;

    public UserNotificationService(String role) {
        this.requestGateway = new UserNotificationRequestGateway(role);
        this.requestHandler = new RequestHandler();
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
        String jsonResponse = requestHandler.sendRequestToServer(jsonRequest);
		 PrintOutToConsole.printToConsole(jsonResponse);
    }

    private void handleException(String message, IOException e) {
        System.err.println(message + " " + e.getMessage());
    }
}
