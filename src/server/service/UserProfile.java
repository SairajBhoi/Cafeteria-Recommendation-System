package server.service;

import server.DatabaseOperation.UserProfileDatabaseOperator;
import server.model.UserPreference;
import server.util.JsonConverter;
import server.util.JsonStringToObject;

public class UserProfile {
    public UserPreference userPreference;

    public String getUserProfile(String data) {
        userPreference = JsonStringToObject.fromJsonToObject(data, UserPreference.class);
        UserProfileDatabaseOperator userProfileDatabaseOperator = new UserProfileDatabaseOperator();

        try {
            userPreference = userProfileDatabaseOperator.getUserPreference(userPreference.getUserID());
            if (userPreference == null) {
                return JsonConverter.convertStatusAndMessageToJson("error", "Profile not set");
            }
        } catch (Exception e) {
            return JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
        }

        String jsonResponse = JsonConverter.convertObjectToJson(userPreference);
        return jsonResponse;
    }
    
    
    public String updateUserProfile(String data) {
    	
    	String jsonResonse=null;
    	userPreference = JsonStringToObject.fromJsonToObject(data, UserPreference.class);
        UserProfileDatabaseOperator userProfileDatabaseOperator = new UserProfileDatabaseOperator();
        String response;
        try {
            response = userProfileDatabaseOperator.updateOrAddUserPreference(userPreference);
            jsonResonse=JsonConverter.convertStatusAndMessageToJson("success",response);
           
        } catch (Exception e) {
            return JsonConverter.convertStatusAndMessageToJson("error", e.getMessage());
        }
    	
    	return jsonResonse;
    }
}
