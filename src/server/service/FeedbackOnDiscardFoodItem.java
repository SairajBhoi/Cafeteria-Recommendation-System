package server.service;

import java.sql.SQLException;
import java.util.List;

import server.databaseoperation.UserDiscardFeedbackDatabaseOper;
import server.model.UserDiscardedFeedback;
import server.util.JsonConverter;
import server.util.JsonStringToObject;

public class FeedbackOnDiscardFoodItem {
	
	
	public String viewFeedback(String data) {
	    UserDiscardFeedbackDatabaseOper userDiscardFeedbackDatabaseOper = new UserDiscardFeedbackDatabaseOper();
	    UserDiscardedFeedback userDiscardedFeedback = JsonStringToObject.fromJsonToObject(data, UserDiscardedFeedback.class);
	    List<UserDiscardedFeedback> userDiscardedFeedbackList = null;
	    String jsonResponse = null;

	    try {
	        userDiscardedFeedbackList = userDiscardFeedbackDatabaseOper.viewAllFeedbackForDiscardID(userDiscardedFeedback.getDiscardID());
	        
	        if (userDiscardedFeedbackList == null || userDiscardedFeedbackList.isEmpty()) {
	            jsonResponse = JsonConverter.convertStatusAndMessageToJson("info", "No feedback found for the given discard ID");
	        } else {
	            jsonResponse = JsonConverter.convertObjectToJson(userDiscardedFeedbackList);
	        }
	    } catch (SQLException e) {
	        jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", "Failed to retrieve feedback");
	        e.printStackTrace();
	    }
	    
	    return jsonResponse;
	}

	
	
	
	public String addFeedback(String data){
		UserDiscardFeedbackDatabaseOper userDiscardFeedbackDatabaseOper = new  UserDiscardFeedbackDatabaseOper();
		UserDiscardedFeedback userDiscardedFeedback	= JsonStringToObject.fromJsonToObject(data, UserDiscardedFeedback.class);
		String jsonResponse= null;
		String response= null;
		try {
		  response	=  userDiscardFeedbackDatabaseOper.insertUserDiscardedFeedback(userDiscardedFeedback);;
		jsonResponse =JsonConverter.convertStatusAndMessageToJson("Success", response);
		} catch (SQLException e) {
			jsonResponse =JsonConverter.convertStatusAndMessageToJson("error", response);
			e.printStackTrace();
		}
		
		return jsonResponse;
		
		
		
	}
	
	
	

}
