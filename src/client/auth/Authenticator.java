package client.auth;

import client.RequestGateway.AuthenticationRequestGateway;
import client.controller.AdminController;
import client.controller.ChefController;
import client.controller.EmployeeController;
import client.model.User;
import client.util.JsonStringToObject;
import client.util.RequestHandler;

public class Authenticator {

    private final AuthenticationRequestGateway requestGateway;
    private final RequestHandler requestHandler;

    public Authenticator() {
        this.requestGateway = new AuthenticationRequestGateway();
        this.requestHandler = new RequestHandler();
    }

    public boolean authenticate(String userId, String password) {
        User user = new User();
        user.setUserId(userId);
        user.setUserPassword(password);

        try {
            String jsonRequest = requestGateway.createAuthenticateRequest(user);
            String jsonResponse = requestHandler.sendRequestToServer(jsonRequest);

            if (jsonResponse == null) {
                System.out.println("Failed to communicate with the server.");
                return false;
            }
           System.out.println(jsonResponse);
            String status = JsonStringToObject.getValueFromData("status", jsonResponse);
            if ("error".equals(status)) {
                String errorMessage = JsonStringToObject.getValueFromData("message", jsonResponse);
                System.out.println("Authentication error: " + errorMessage);
                return false;
            }

            user = JsonStringToObject.fromJsonToObject(jsonResponse, User.class);
            System.out.println("Authenticated User: " + user.getUserId() + " (" + user.getUserRole() + ")");

            return handleUserRole(user);

        } catch (Exception ex) {
            System.out.println("Authentication failed: " + ex.getMessage());
            return false;
        } finally {
            user.logout();
        }
    }

    private boolean handleUserRole(User user) throws Exception {
        String role = user.getUserRole();
        String userName = user.getUserName();

        switch (role) {
            case "ADMIN":
                AdminController adminController = new AdminController(userName, user.getUserId());
                adminController.runHomepage();
                break;
            case "CHEF":
                ChefController chefController = new ChefController(userName, user.getUserId());
                chefController.runHomePage();
                break;
            case "EMPLOYEE":
                EmployeeController employeeController = new EmployeeController(userName, user.getUserId());
                employeeController.runHomePage();
                break;
            default:
                System.out.println("Invalid role.");
                return false;
        }
        return true;
    }
}
