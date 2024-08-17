package client.model;

import client.service.RolloutHandler;
import client.service.DiscardListService;
import client.service.FeedbackHandler;
import client.service.Menu;

public class Chef extends User {

    public Chef(String userId, String userName) {
        this.setUserRole("CHEF");
        this.setUserName(userName);
        this.setUserId(userId);
    }

    public void menurollout() {
        RolloutHandler chef = new RolloutHandler(this.getUserRole());
        chef.createAllChefMenuRollouts();
        System.out.println("Rollout menu for tomorrow.");
    }

    public void viewRecommendation() {
        RolloutHandler chef = new RolloutHandler(this.getUserRole());
        chef.recommendation();
        System.out.println("View Recommendation from Engine.");
    }

    public void FinalVoteMenu() {
        System.out.println("Final Result Vote Menu");
        RolloutHandler chefrollout = new RolloutHandler(this.getUserRole());
        chefrollout.getFinalVoteMenu();
    }

    public void viewFinalMenuDecided() {
        System.out.println("Final Menu after Rollout");
        RolloutHandler chefrollout = new RolloutHandler(this.getUserRole());
        chefrollout.getFinalDecidedMenu();
        System.out.println("View Decided Menu To be prepared tomorrow.");
    }

    public void addFinalMenuafterRollout() {
        RolloutHandler chef = new RolloutHandler(this.getUserRole());
        chef.createAllFinalDecidedChefMenu();
        System.out.println("Adding Decided Menu To be prepared tomorrow.");
    }

    public void ViewFeedbackonFoodItem() {
        FeedbackHandler feedbackHandler = new FeedbackHandler(this.getUserRole());
        System.out.println("View Feedback on food item.");
        feedbackHandler.viewFeedbackOnFoodItem();
    }
    
    public void viewChefDiscardList() {
           DiscardListService discardList = new  DiscardListService(this.getUserRole());
           discardList.viewChefDiscardList();
    }
    
    public void generateDiscardList(){
    	DiscardListService discardList = new  DiscardListService(this.getUserRole());
    	 discardList.generateDiscardList();
    }
    
    
    public void viewFeedbackOnDiscardList() {
    	DiscardListService discardList = new  DiscardListService(this.getUserRole());
   	    discardList.viewFeedbackOnDiscardList();
    	
    }
    
    

    void generateReport() {
        System.out.println("Generate report.");
    }
    
    public void viewMainMenu() {
    	
    	Menu menu = new Menu(this.getUserRole());
    	menu.viewAllMenuItems();
    }
    
    
    
}
