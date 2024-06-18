package server;

import java.sql.SQLException;

public class ChefMenuRolloutHandler {
	
	
	String rolloutMenu(String data) {
		String jsonresponse = null;
		
		ChefMenuRolloutDAO chefMenuRolloutDAO = new ChefMenuRolloutDAO ();
		ChefMenuRollout chefMenuRollout=JsonStringToObject.fromJsonToObject(data,ChefMenuRollout.class);
		try {
			boolean status = chefMenuRolloutDAO.insertChefMenuRollout(chefMenuRollout);
			if(status)
			{
				jsonresponse=JsonConverter.convertStatusAndMessageToJson("suceess", "added to rollout Menu");
			}
			
		} catch (SQLException e) {
			jsonresponse=JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
			e.printStackTrace();
		}
		
		return jsonresponse;
		
		
		
		
		
		
	}
	
}
