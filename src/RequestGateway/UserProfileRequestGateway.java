package RequestGateway;

import client.model.UserPreference;
import client.util.JsonConverter;

public class UserProfileRequestGateway {
    private String basePath;

    public UserProfileRequestGateway(String role) {
        this.basePath = "/" + role;
    }

    private String generatePath(String endpoint) {
        return this.basePath + endpoint;
    }

    public String createViewProfileRequest(String userID) {
    	UserPreference userPreference= new UserPreference();
    	userPreference.setUserID(userID);
        return JsonConverter.convertObjectToJson(generatePath("/viewUserProfile"),userPreference );
    }

    public String createUpdateProfileRequest(UserPreference userPreference) {
        return JsonConverter.convertObjectToJson(generatePath("/updateUserProfile"), userPreference);
    }
}
