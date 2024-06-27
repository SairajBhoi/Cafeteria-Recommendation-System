package server.service;

import java.sql.SQLException;
import java.util.List;

import server.DatabaseOperation.ChefMenuRolloutDatabaseOperator;
import server.model.ChefMenuRollout;
import server.util.JsonConverter;
import server.util.JsonStringToObject;

public class ChefMenuRolloutHandler {
	
	
	public String rolloutMenu(String data) {
		String jsonresponse = null;
		
		ChefMenuRolloutDatabaseOperator chefMenuRolloutDAO = new ChefMenuRolloutDatabaseOperator ();
		ChefMenuRollout chefMenuRollout=JsonStringToObject.fromJsonToObject(data,ChefMenuRollout.class);
		try {
			boolean status = chefMenuRolloutDAO.insertChefMenuRollout(chefMenuRollout);
			if(status)
			{
				jsonresponse=JsonConverter.convertStatusAndMessageToJson("suceess", "added to rollout Menu");
				NotificationService notificationService=	new NotificationService();
				notificationService.addNotification("Chef Rolled out Menu please check and Vote");
			}
			
		} catch (SQLException e) {
			jsonresponse=JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
			e.printStackTrace();
		}
		
		return jsonresponse;
		
		
	}
	
	
	public String viewRolloutResult() {
		String jsonresponse = null;
		
		ChefMenuRolloutDatabaseOperator chefMenuRolloutDAO = new ChefMenuRolloutDatabaseOperator();
		try {
			List<ChefMenuRollout> roolouts = chefMenuRolloutDAO.getTodayRollouts();
			
		
				
			jsonresponse=JsonConverter.convertObjectToJson(roolouts);
		}
		catch(Exception e){
			jsonresponse=JsonConverter.convertStatusAndMessageToJson("error", "Failed to produce data");
		}
		
		
		return jsonresponse;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
