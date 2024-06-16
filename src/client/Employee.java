package client;

import java.io.IOException;

public class Employee extends User {

    private FeedbackHandler feedbackHandler;
    private UserNotificationService userNotificationService;

    Employee(String userName, String userId){
    	  this.setUserRole("EMPLOYEE");
		    this.setUserName(userName);
	        this.setUserId(userId);  
        feedbackHandler=new FeedbackHandler(this.getUserRole(),this.getUserName());
        userNotificationService = new UserNotificationService(this.getUserRole());

    }
void ViewFeedbackonFoodItem(){
    
    try {
		String itemName=InputHandler.getStringInput("Enter Food Item Name");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    feedbackHandler.viewFeedbackonFoodItem();

}

void addFeedbackonFooditem(){
  try {
	feedbackHandler.addFeedbackOnFoodItem();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}

void viewNotification(){
    userNotificationService.viewNotification(this.getUserId());
}

void viewMainMenu() {
	
	
	System.out.print("Main menu");
}



void votefortomorrowsMenu() {
	System.out.print("votefortomorrowsMenu");
}



void viewRecommendation() {
	
	System.out.print("viewRecommendation");
}



}