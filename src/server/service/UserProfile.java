package server.service;

import server.databaseoperation.UserProfileDatabaseOperator;
import server.model.UserPreference;
import server.util.JsonConverter;
import server.util.JsonStringToObject;

public class UserProfile {
    private UserPreference userPreference;

    public String getUserProfile(String data) {
        try {
          
            userPreference = JsonStringToObject.fromJsonToObject(data, UserPreference.class);
            UserProfileDatabaseOperator userProfileDatabaseOperator = new UserProfileDatabaseOperator();

           
            userPreference = userProfileDatabaseOperator.getUserPreference(userPreference.getUserID());
            
          
            if (userPreference == null) {
                return JsonConverter.convertStatusAndMessageToJson("error", "Profile not set");
            }

          
            return JsonConverter.convertObjectToJson(userPreference);
        } catch (Exception e) {
          
            return JsonConverter.convertStatusAndMessageToJson("error", "Error retrieving user profile: " + e.getMessage());
        }
    }

    public String updateUserProfile(String data) {
        try {
           
            userPreference = JsonStringToObject.fromJsonToObject(data, UserPreference.class);
            UserProfileDatabaseOperator userProfileDatabaseOperator = new UserProfileDatabaseOperator();

         
            String response = userProfileDatabaseOperator.updateOrAddUserPreference(userPreference);
            
         
            return JsonConverter.convertStatusAndMessageToJson("success", response);
        } catch (Exception e) {
          
            return JsonConverter.convertStatusAndMessageToJson("error", "Error updating user profile: " + e.getMessage());
        }
    }
}
