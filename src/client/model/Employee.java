package client.model;

import java.text.ParseException;

import client.service.DiscardListService;
import client.service.FeedbackHandler;
import client.service.Menu;
import client.service.RolloutHandler;
import client.service.UserNotificationService;
import client.service.UserProfile;
import client.service.VoteFuncationalityHandler;

public class Employee extends User {
    private FeedbackHandler feedbackHandler;
    private UserNotificationService userNotificationService;
    private VoteFuncationalityHandler voteDAO;
    private UserProfile userProfile;
    private 	DiscardListService discardListService ;

    
    public Employee(String userName, String userId) {
        this.setUserRole("EMPLOYEE");
        this.setUserName(userName);
        this.setUserId(userId);

        this.feedbackHandler = new FeedbackHandler(this.getUserRole(), this.getUserName(), this.getUserId());
        this.userNotificationService = new UserNotificationService(this.getUserRole());
        this.voteDAO = new VoteFuncationalityHandler(this.getUserRole(), this.getUserId());
        this.userProfile= new UserProfile(this.getUserRole(),this.getUserId());
        discardListService = new DiscardListService(this.getUserRole());
    }

    public void viewFeedbackOnFoodItem() {
        feedbackHandler.viewFeedbackOnFoodItem();
    }

    public void addFeedbackOnFoodItem() {
        feedbackHandler.addFeedbackOnFoodItem();
    }

    public void viewChefRollout() {
        voteDAO.viewChefRollout();
    }

    public void viewNotification() {
        userNotificationService.viewNotification(this.getUserId());
    }

    public void viewUnseenNotification() {
        userNotificationService.viewUnseenNotification(this.getUserId());
    }

    public void viewMainMenu() {
        Menu menu = new Menu(this.getUserRole());
        menu.viewAllMenuItems();
    }

    public void voteForTomorrowsMenu() throws ParseException {
    	System.out.println("Rollout Menu");
    	voteDAO.viewChefRollout();
    	System.out.println("----------------------------------");
        voteDAO.addVote(); 
    }

    public void viewRecommendation() {
        voteDAO.viewRecommendation();
    }

    public void viewTodaysMenu() {
        RolloutHandler rolloutHandler = new RolloutHandler(this.getUserRole());
        rolloutHandler.todaysMenu();
    }
    
    public void viewUserProfile() {
    	userProfile.viewProfile();
    }
    
    public void updateUserProfile() {
    
    	userProfile.updateUserProfile();
    	
    }
    
    public void viewChefDiscardFoodItem() {
    discardListService.viewChefDiscardList();
    	
    }
    
    public void addFeedbackOnChefDiscardFoodItem() {
    	viewChefDiscardFoodItem();
    	feedbackHandler.addFeedbackOnChefDiscardedFoodItem();
       }
    
    
    
    
}
