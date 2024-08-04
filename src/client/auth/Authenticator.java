package client.auth;

import client.Client;
import client.controller.AdminController;
import client.controller.ChefController;
import client.controller.EmployeeController;
import client.model.User;
import client.util.JsonConverter;
import client.util.JsonStringToObject;

public class Authenticator {
    
    private String requestPath;
    
    public Authenticator() {
        requestPath = "/authenticate";
    }

    public boolean authenticate(String userId, String password) {
        User user = new User();
        user.setUserId(userId);
        user.setUserPassword(password);

        try {
            String jsonRequest = JsonConverter.convertObjectToJson(requestPath, user);
            //System.out.println("JSON Request: " + jsonRequest);

            String jsonResponse = Client.requestServer(jsonRequest);
           // System.out.println("JSON Response: " + jsonResponse);

            String status = JsonStringToObject.getValueFromData("status", jsonResponse);
            if ("error".equals(status)) {
                String errorMessage = JsonStringToObject.getValueFromData("message", jsonResponse);
                System.out.println("Authentication error: " + errorMessage);
                return false;
            }

   
            user = JsonStringToObject.fromJsonToObject(jsonResponse, User.class);
            System.out.println("Authenticated User: " + user.getUserId() + " (" + user.getUserRole() + ")");


            String role = user.getUserRole();
            String userName = user.getUserName();

            switch (role) {
                case "ADMIN":
                    AdminController adminController = new AdminController(userName, userId);
                    adminController.runHomepage();
                    break;
                case "CHEF":
                    ChefController chefController = new ChefController(userName, userId);
                    chefController.runHomePage();
                    break;
                case "EMPLOYEE":
                    EmployeeController employeeController = new EmployeeController(userName, userId);
                    employeeController.runHomePage();
                    break;
                default:
                    System.out.println("Invalid role.");
            }
        } catch (Exception ex) {
            System.out.println("Authentication failed: " + ex.getMessage());
            return false;
        } finally {
            user.logout();
        }

        return true;
    }
}
