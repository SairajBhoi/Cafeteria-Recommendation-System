package client.service;



import client.RequestGateway.UserProfileRequestGateway;

import client.model.UserPreference;
import client.util.InputHandler;

import client.util.PreferenceMapper;
import client.util.RequestHandler;

public class UserProfile {
    private final String userID;
    private final UserProfileRequestGateway requestGateway;
    private final RequestHandler requestHandler;

    public UserProfile(String userRole, String userID) {
        this.userID = userID;
        this.requestGateway = new UserProfileRequestGateway(userRole);
        this.requestHandler = new RequestHandler();
    }

    public void viewProfile() {
        String jsonRequest = requestGateway.createViewProfileRequest(userID);
        requestHandler.sendRequestToServer(jsonRequest);
    }

    public void updateUserProfile() {
        UserPreference userPreference = gatherUserPreferences();
        String jsonRequest = requestGateway.createUpdateProfileRequest(userPreference);
        requestHandler.sendRequestToServer(jsonRequest);
    }

    private UserPreference gatherUserPreferences() {
        UserPreference userPreference = new UserPreference();
        userPreference.setUserID(this.userID);

        userPreference.setDietaryPreference(PreferenceMapper.getDietaryPreference());
        userPreference.setPreferredSpiceLevel(PreferenceMapper.getSpiceLevel());
        userPreference.setPreferredCuisine(PreferenceMapper.getCuisine());
        userPreference.setHasSweetTooth(InputHandler.getBooleanInput("Do you have a sweet tooth?"));

        return userPreference;
    }
}
