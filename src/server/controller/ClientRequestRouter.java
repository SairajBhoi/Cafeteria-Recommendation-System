package server.controller;

import java.sql.SQLException;

import server.DatabaseOperation.UserVoteDatabaseOperator;
import server.auth.AuthenticationService;
import server.resources.RecommendationEngine;
import server.service.ChefMenuRolloutHandler;
import server.service.FeedbackService;
import server.service.FinalDecidedTodayMenuHandler;
import server.service.MenuItemHandler;
import server.service.NotificationService;
import server.service.RecommendationService;
import server.util.JsonConverter;
import server.util.JsonStringToObject;

public class ClientRequestRouter {

    private AuthenticationService authService;
    private MenuItemHandler menuItemHandler;
    private FeedbackService feedbackService;
    private ChefMenuRolloutHandler chefMenuRolloutHandler;
    private UserVoteDatabaseOperator uservoteDAO;
    private FinalDecidedTodayMenuHandler finalResultMenuHandler1;
 private RecommendationService recommendationService;
 private  FinalDecidedTodayMenuHandler finalResultMenuHandler ;

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
                case "/CHEF/recommendation":
                	System.out.println("inside the recommendation case");
                	RecommendationService recommendationService = new RecommendationService();
                response =  recommendationService. getFoodItemForNextDay(data);
                break;
                
                case"/CHEF/addfinalResultMenu":
                	 finalResultMenuHandler= new FinalDecidedTodayMenuHandler();
                	response = finalResultMenuHandler1.addFinalResultMenu(data);
                	break;
                	
                case"/finalDecidedMenuAfterRollout":
                	 finalResultMenuHandler = new FinalDecidedTodayMenuHandler();
                	response = finalResultMenuHandler.viewFinalResultMenu();
                	break; 
                	
                case "/CHEF/rolloutMenu":
                	chefMenuRolloutHandler = new ChefMenuRolloutHandler();
				    response = chefMenuRolloutHandler.rolloutMenu(data);
               	break;
               	
                case "/CHEF/finalVoteMenu":
                chefMenuRolloutHandler = new ChefMenuRolloutHandler();
			    response = chefMenuRolloutHandler.viewRolloutResult();
			    break;
                case "/EMPLOYEE/viewChefRollout":
                     uservoteDAO = new UserVoteDatabaseOperator();
                   response = uservoteDAO.getChefRolloutListForCurrentDateAsJson();
                   break;
                case "/EMPLOYEE/addVote":
                	uservoteDAO= new UserVoteDatabaseOperator();              
                	response=uservoteDAO.addVote(data);
                	break;
                
                case "/EMPLOYEE/recommendation":
                	System.out.println("inside the recommendation case");
                  recommendationService  = new RecommendationService();
                response = recommendationService.getFoodItemByTaste();
                break;
                case "/EMPLOYEE/viewNotification":
                	System.out.println("inside the Notification");
                 NotificationService notificationService  = new NotificationService();
                response =  notificationService.getNotification();
                break;
                
                case "/EMPLOYEE/viewUnseenNotification":
                	   notificationService  = new NotificationService();
                      response =  notificationService.getUnseenNotifications(data);
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
