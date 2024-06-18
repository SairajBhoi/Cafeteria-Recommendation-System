package client;

import java.io.IOException;

import org.json.simple.parser.ParseException;

public class Employee extends User {

    private FeedbackHandler feedbackHandler;
    private UserNotificationService userNotificationService;
    private   VoteDAO voteDAO;
    Employee(String userName, String userId){
    	  this.setUserRole("EMPLOYEE");
		    this.setUserName(userName);
	        this.setUserId(userId); 

    }
void ViewFeedbackonFoodItem(){
	feedbackHandler=new FeedbackHandler(this.getUserRole(),this.getUserName(),this.getUserId());
    feedbackHandler.viewFeedbackonFoodItem();

}

void addFeedbackonFooditem(){
  try {
	feedbackHandler=new FeedbackHandler(this.getUserRole(),this.getUserName(),this.getUserId());
	feedbackHandler.addFeedbackOnFoodItem();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}

void viewChefRollout() {
	voteDAO = new VoteDAO(this.getUserRole(),this.getUserId());
	voteDAO.viewChefRollout();
	
}

void viewNotification(){
	userNotificationService = new UserNotificationService(this.getUserRole());
    userNotificationService.viewNotification(this.getUserId());
}

void viewMainMenu() {
	
	System.out.print("Main menu");
}



void votefortomorrowsMenu() {
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



void viewRecommendation() {
	System.out.println(this.getUserRole());
	voteDAO = new VoteDAO(this.getUserRole(),this.getUserId());
	voteDAO.viewRecommendation();
}



}