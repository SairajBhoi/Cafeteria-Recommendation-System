package client.model;

import java.io.IOException;
import java.text.ParseException;

import client.service.FeedbackHandler;
import client.service.Menu;
import client.service.UserNotificationService;
import client.service.VoteDAO;

public class Employee extends User {

    private FeedbackHandler feedbackHandler;
    private UserNotificationService userNotificationService;
    private   VoteDAO voteDAO;
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
	voteDAO = new VoteDAO(this.getUserRole(),this.getUserId());
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



public void votefortomorrowsMenu() {
	voteDAO = new VoteDAO(this.getUserRole(),this.getUserId());
	voteDAO.viewChefRollout();
	
	try {
		voteDAO.addVote();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}



public void viewRecommendation() {
	System.out.println(this.getUserRole());
	voteDAO = new VoteDAO(this.getUserRole(),this.getUserId());
	voteDAO.viewRecommendation();
}



}