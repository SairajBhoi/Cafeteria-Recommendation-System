package server.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.util.JsonStringToObject;
import server.databaseoperation.NotificationDatabaseOperator;
import server.util.JsonConverter;

public class NotificationService {

    private static final Logger logger = Logger.getLogger(NotificationService.class.getName());
    private final NotificationDatabaseOperator notificationDatabaseOperator;

    public NotificationService() {
        this.notificationDatabaseOperator = new NotificationDatabaseOperator();
    }

    public void addNotification(String message) {
        try {
            int notificationID = notificationDatabaseOperator.addNotification(message);
            if (notificationID != -1) {
                boolean success = notificationDatabaseOperator.associateNotificationWithUsers(notificationID);
                if (success) {
                    logger.info("Notification added and associated with all users.");
                } else {
                    logger.warning("Failed to associate notification with users.");
                }
            } else {
                logger.warning("Failed to add notification.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred during addNotification: {0}", e.getMessage());
        }
    }

    public String getNotification(String data) {
        String jsonResponse;
        try {
            String userID = JsonStringToObject.getValueFromData("UserID", data);
            List<server.model.Notification> notifications = notificationDatabaseOperator.getAllNotifications(userID);

            if (notifications == null || notifications.isEmpty()) {
                jsonResponse = JsonConverter.convertStatusAndMessageToJson("info", "No notifications found for the given UserID");
            } else {
                jsonResponse = JsonConverter.convertObjectToJson(notifications);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred during getNotification: {0}", e.getMessage());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
        }
        return jsonResponse;
    }

    public String getUnseenNotifications(String data) {
        String jsonResponse;
        try {
            String userID = JsonStringToObject.getValueFromData("UserID", data);
            List<server.model.Notification> notifications = notificationDatabaseOperator.getUnseenNotifications(userID);

            if (notifications == null || notifications.isEmpty()) {
                jsonResponse = JsonConverter.convertStatusAndMessageToJson("info", "No unseen notifications found for the given UserID");
            } else {
                jsonResponse = JsonConverter.convertObjectToJson(notifications);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred during getUnseenNotifications: {0}", e.getMessage());
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
        }
        return jsonResponse;
    }
}
