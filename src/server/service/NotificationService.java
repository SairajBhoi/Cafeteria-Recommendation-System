package server.service;

import java.util.List;

import client.model.Notification;
import client.util.JsonStringToObject;
import server.databaseoperation.NotificationDatabaseOperator;
import server.util.JsonConverter;

public class NotificationService {

	
	
	void addNotification(String message){
		
		NotificationDatabaseOperator notificationDatabaseOpertor = new NotificationDatabaseOperator();	
		int notificationID =notificationDatabaseOpertor.addNotification(message);
		System.out.println("added notification");
		
		
		if (notificationID != -1) {
		    boolean success =notificationDatabaseOpertor.associateNotificationWithUsers(notificationID);
		    if (success) {
		        System.out.println("Notification added and associated with all users.");
		    } else {
		        System.out.println("Failed to associate notification with users.");
		    }
		} else {
		    System.out.println("Failed to add notification.");
		}

	}
	
	
	public String getNotification(String data) {
	    String jsonResponse;
	    NotificationDatabaseOperator notificationDatabaseOperator = new NotificationDatabaseOperator();
	    
	    try {
	        String userID = JsonStringToObject.getValueFromData("UserID", data);
	        List<server.model.Notification> notifications = notificationDatabaseOperator.getAllNotifications(userID);
	        
	        if (notifications == null || notifications.isEmpty()) {
	            jsonResponse = JsonConverter.convertStatusAndMessageToJson("info", "No notifications found for the given UserID");
	        } else {
	            jsonResponse = JsonConverter.convertObjectToJson(notifications);
	        }
	    } catch (Exception e) {
	        jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return jsonResponse;
	}


	public String getUnseenNotifications(String data) {
	    String jsonResponse;
	    
	    try {
	        String userID = JsonStringToObject.getValueFromData("UserID", data);
	        NotificationDatabaseOperator notificationDatabaseOperator = new NotificationDatabaseOperator();    
	        List<server.model.Notification> notifications = notificationDatabaseOperator.getUnseenNotifications(userID);
	        
	        if (notifications == null || notifications.isEmpty()) {
	            jsonResponse = JsonConverter.convertStatusAndMessageToJson("info", "No unseen notifications found for the given UserID");
	        } else {
	            jsonResponse = JsonConverter.convertObjectToJson(notifications);
	        }
	    } catch (Exception e) {
	        jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return jsonResponse;
	}

}
