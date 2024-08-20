package server.service;

import java.sql.SQLException;
import java.util.List;

import server.databaseoperation.*;
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
	        List<ChefMenuRollout> rollouts = chefMenuRolloutDAO.getTodayRollouts();
	        
	        if (rollouts == null || rollouts.isEmpty()) {
	            jsonresponse = JsonConverter.convertStatusAndMessageToJson("info", "No rollouts created for today");
	        } else {
	            jsonresponse = JsonConverter.convertObjectToJson(rollouts);
	        }
	    } catch (Exception e) {
	        jsonresponse = JsonConverter.convertStatusAndMessageToJson("error", "Failed to produce data");
	    }
	    
	    return jsonresponse;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
