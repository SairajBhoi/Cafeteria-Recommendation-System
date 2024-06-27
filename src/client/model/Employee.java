package client.model;

import java.io.IOException;
import java.text.ParseException;

import client.service.FeedbackHandler;
import client.service.Menu;
import client.service.RolloutHandler;
import client.service.UserNotificationService;
import client.service.VoteFuncationalityHandler;

public class Employee extends User {

    private FeedbackHandler feedbackHandler;
    private UserNotificationService userNotificationService;
    private   VoteFuncationalityHandler voteDAO;
    public Employee(String userName, String userId){
    	  this.setUserRole("EMPLOYEE");
		    this.setUserName(userName);
	        this.setUserId(userId); 

    }
public void ViewFeedbackonFoodItem(){
	feedbackHandler=new FeedbackHandler(this.getUserRole(),this.getUserName(),this.getUserId());
    feedbackHandler.viewFeedbackonFoodItem();

}

public void addFeedbackonFooditem(){
  try {
	feedbackHandler=new FeedbackHandler(this.getUserRole(),this.getUserName(),this.getUserId());
	feedbackHandler.addFeedbackOnFoodItem();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}

public void viewChefRollout() {
	voteDAO = new VoteFuncationalityHandler(this.getUserRole(),this.getUserId());
	voteDAO.viewChefRollout();
	
}

public void viewNotification(){
	userNotificationService = new UserNotificationService(this.getUserRole());
    userNotificationService.viewNotification(this.getUserId());
}

public void viewMainMenu() {
	 Menu menu = new Menu(this.getUserRole());
	menu.viewAllMenuItems();
	
	
}



public void votefortomorrowsMenu() throws ParseException, IOException {
	voteDAO = new VoteFuncationalityHandler(this.getUserRole(),this.getUserId());
	voteDAO.viewChefRollout();
	
	voteDAO.addVote();
	
}



public void viewRecommendation() {
	System.out.println(this.getUserRole());
	voteDAO = new VoteFuncationalityHandler(this.getUserRole(),this.getUserId());
	voteDAO.viewRecommendation();
}

public void viewTodaysMenu() {
	RolloutHandler rollouthandler=new  RolloutHandler(this.getUserRole());
	rollouthandler.getFinalDecidedMenu();
}



}