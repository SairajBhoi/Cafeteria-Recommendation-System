package server;

import java.sql.SQLException;

public class ClientRequestRouter {

    private AuthenticationService authService;
    private MenuItemHandler menuItemHandler;
    private FeedbackService feedbackService;

    public ClientRequestRouter() {
        authService = new AuthenticationService();
        try {
			menuItemHandler = new MenuItemHandler();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        feedbackService = new FeedbackService();
    }

    public String route(String clientRequest) {
        String response = null;

        try {
            String path = JsonStringToObject.getPath(clientRequest);
            System.out.print("path"+path);
            String data = JsonStringToObject.getData(clientRequest);
            System.out.print("data"+data);
            System.out.print("?????????????????????????????????");
            switch (path) {
                case "/authenticate":
                    response = authService.authenticate(data);
                    break;
                case "/viewAllMenuItems":
                    response = menuItemHandler.viewAllMenuItems();
                    break;
                case "/viewFeedbackonFoodItem":
                    response = feedbackService.viewFeedback(data);
                    break;
                case "/EMPLOYEE/addFeedbackOnFoodItem":
                    response = feedbackService.addFeedback(data);
                    break;
                case "/ADMIN/deleteMenuItem":
                    response = menuItemHandler.deleteMenuItem(data);
                    break;
                case "/ADMIN/addMenuItem":
                    response = menuItemHandler.addMenuItem(data);
                    break;
                case "/ADMIN/updateMenuItem":
                    response = menuItemHandler.updateMenuItem(data);
                    break;
                case "/ADMIN/updateFoodAvailableStatus":
                    response = menuItemHandler.updateFoodAvailableStatus(data);
                    break;
                default:
                    response = JsonConverter.convertStatusAndMessageToJson("info","Unknown request path: " + path);
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error processing client request: " + e.getMessage());
            response = "Error processing request";
        }

        return response;
    }
}
