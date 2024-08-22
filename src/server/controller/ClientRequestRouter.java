package server.controller;

import java.sql.SQLException;


import server.auth.AuthenticationService;
import server.resources.recommendationengine.RecommendationService;
import server.service.ChefMenuRolloutHandler;
import server.service.DiscardedItemListHandler;
import server.service.FeedbackOnDiscardFoodItem;
import server.service.FeedbackService;
import server.service.FinalDecidedTodayMenuHandler;
import server.service.MenuItemHandler;
import server.service.NotificationService;
import server.service.UserProfile;
import server.service.UserVoteService;
import server.util.JsonConverter;
import server.util.JsonStringToObject;

public class ClientRequestRouter {

    private AuthenticationService authService;
    private MenuItemHandler menuItemHandler;
    private FeedbackService feedbackService;
    private ChefMenuRolloutHandler chefMenuRolloutHandler;
    private UserVoteService userVoteService;
    private FinalDecidedTodayMenuHandler finalResultMenuHandler;
    private RecommendationService recommendationService;
    private NotificationService notificationService;
    private  UserProfile userProfile;
    private DiscardedItemListHandler discardedItemListHandler;
    private FeedbackOnDiscardFoodItem feedbackOnDiscardFoodItem;
    public ClientRequestRouter() {
        authService = new AuthenticationService();
        try {
            menuItemHandler = new MenuItemHandler();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        feedbackService = new FeedbackService();
        finalResultMenuHandler = new FinalDecidedTodayMenuHandler();
        recommendationService = new RecommendationService();
        notificationService = new NotificationService();
        userVoteService = new UserVoteService();
        chefMenuRolloutHandler = new ChefMenuRolloutHandler();
        userProfile  = new UserProfile();
        discardedItemListHandler = new DiscardedItemListHandler();
        feedbackOnDiscardFoodItem = new FeedbackOnDiscardFoodItem();
    }

    public String route(String clientRequest) {
        String response = null;

        try {
            String path = JsonStringToObject.getPath(clientRequest);
            System.out.println("Request Path: " + path);
            String data = JsonStringToObject.getData(clientRequest);
            System.out.println("Request Data: " + data);

            switch (path) {
                case "/authenticate":
                    response = authService.authenticate(data);
                    break;
                    
                    
                    
                    
                case "/viewAllMenuItems":
                    response = menuItemHandler.viewAllMenuItems();
                    break;
                case "/viewFeedbackOnFoodItem":
                    response = feedbackService.viewFeedback(data);
                    break;
                    
                case "/viewFeedbackOnDiscardFoodItem":
                	response = feedbackOnDiscardFoodItem.viewFeedback(data);
                	break;
                    
                    
                    
                    
                    
               
                case "/finalDecidedMenuAfterRollout":
                    response = finalResultMenuHandler.viewFinalResultMenu();
                    break;	
                case "/viewTodaysMenu":
                	 response = finalResultMenuHandler.viewTodaysMenu();
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
                    response = recommendationService.getFoodItemForNextDay(data);
                    break;
                case "/CHEF/addfinalResultMenu":
                    response = finalResultMenuHandler.addFinalResultMenu(data);
                    break;
                case "/CHEF/rolloutMenu":
                    response = chefMenuRolloutHandler.rolloutMenu(data);
                    break;
                case "/CHEF/finalVoteMenu":
                    response = chefMenuRolloutHandler.viewRolloutResult();
                    break;
                case "/viewDiscardedList":
                	response = discardedItemListHandler.getLastMonthDiscardedList();
                	break;
                case "/viewChefDiscardedList":
                	response = discardedItemListHandler.getChefDiscardedList();
                	break;
                case "/CHEF/addChefDiscardedFoodItem":
                	response = discardedItemListHandler.insertChefDiscardsFoodItem(data);
                	break;
                case "/CHEF/deleteChefDiscardedFoodItem":
                	response = discardedItemListHandler.deleteDiscardFoodItem(data);
                	break;
                    
                    
                    
                    
                case "/EMPLOYEE/addFeedbackOnFoodItem":
                    response = feedbackService.addFeedback(data);
                    break;
                case "/EMPLOYEE/viewUserProfile":
				response = userProfile.getUserProfile(data);
                	break;
                case "/EMPLOYEE/updateUserProfile":
    				response = userProfile.updateUserProfile(data);
                    	break;   
                case "/EMPLOYEE/viewChefRollout":
                    response = userVoteService.getChefRolloutListForCurrentDateAsJson();
                    break;
                case "/EMPLOYEE/addVote":
                    response = userVoteService.addVote(data);
                    break;
                case "/EMPLOYEE/recommendation":
                    response = recommendationService.getFoodItemByTaste();
                    break;
                case "/EMPLOYEE/recommendationOnChefRooloout":
                	  response = recommendationService.getFoodOrderbyRatingOnChefRollout(); 
                	  break;
                case "/EMPLOYEE/recommendationOnPreference":
                	response =recommendationService.getFoodOrderbyUserPreferenceOnChefRollout(data);
                	break;
               case "/EMPLOYEE/viewNotification":
                    response = notificationService.getNotification(data);
                    break;
                case "/EMPLOYEE/viewUnseenNotification":
                    response = notificationService.getUnseenNotifications(data);
                    break;
                case "/EMPLOYEE/addFeedbackOnDiscardFoodItem":
                	response = feedbackOnDiscardFoodItem.addFeedback(data);
                	break;  
                    
                    
                  
                default:
                    response = JsonConverter.convertStatusAndMessageToJson("info", "Unknown request path: " + path);
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error processing client request: " + e.getMessage());
            response = JsonConverter.convertStatusAndMessageToJson("error", "Error processing request");
        }

        return response;
    }
}
