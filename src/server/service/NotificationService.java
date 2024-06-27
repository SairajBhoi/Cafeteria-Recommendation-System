package server.service;

import java.util.List;

import client.model.Notification;
import client.util.JsonStringToObject;
import server.DatabaseOperation.NotificationDatabaseOperator;
import server.util.JsonConverter;

public class NotificationService {

	
	
	void addNotification(String message){
		
		NotificationDatabaseOperator notificationDatabaseOpertor = new NotificationDatabaseOperator();	
		notificationDatabaseOpertor.addNotification(message);
		System.out.println("added notification");

	}
	
	
public String getNotification(){
	
		
		NotificationDatabaseOperator notificationDatabaseOpertor = new NotificationDatabaseOperator();	
		List<server.model.Notification>	notifications = notificationDatabaseOpertor.getAllNotifications();
		
		return JsonConverter.convertObjectToJson(notifications);
	}

public String getUnseenNotifications(String data){
	String UserID=JsonStringToObject.getValueFromData("UserID", data);
	NotificationDatabaseOperator notificationDatabaseOpertor = new NotificationDatabaseOperator();	
	 List<server.model.Notification>	notifications = notificationDatabaseOpertor.getUnseenNotifications(UserID);
	
	return JsonConverter.convertObjectToJson(notifications);
}
}
