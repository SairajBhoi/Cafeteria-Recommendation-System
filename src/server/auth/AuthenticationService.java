package server.auth;

import server.databaseoperation.UserDatatabaseOperator;
import server.model.User;
import server.util.JsonConverter;
import server.util.JsonStringToObject;

public class AuthenticationService {
    private final UserDatatabaseOperator userDAO;
    public AuthenticationService() {
        this.userDAO = new UserDatatabaseOperator();
    }

    public String authenticate(String data) {
        User user = null;
        try {
           
            user = JsonStringToObject.fromJsonToObject(data, User.class);
            if (user == null || user.getUserId() == null || user.getUserPassword() == null) {
               
                return JsonConverter.convertStatusAndMessageToJson("error", "Invalid input data.");
            }
        } catch (Exception e) {
           
            e.printStackTrace();
            return JsonConverter.convertStatusAndMessageToJson("error", "Error parsing input data.");
        }

        String jsonResponse;
        try {
           
            User authenticatedUser = userDAO.authenticateUser(user.getUserId(), user.getUserPassword());
            if (authenticatedUser != null) {
               
                jsonResponse = JsonConverter.convertObjectToJson(authenticatedUser);
            } else {
               
                jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", "Authentication failed: Invalid user ID or password.");
            }
        } catch (Exception e) {
          
            jsonResponse = JsonConverter.convertStatusAndMessageToJson("error", "Error during authentication: " + e.getMessage());
        }
        return jsonResponse;
    }

}
